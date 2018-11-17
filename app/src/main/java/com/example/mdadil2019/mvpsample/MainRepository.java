package com.example.mdadil2019.mvpsample;

import com.example.mdadil2019.mvpsample.http.MoreInfoApiService;
import com.example.mdadil2019.mvpsample.http.MovieApiService;
import com.example.mdadil2019.mvpsample.http.apimodel.OmdbApi;
import com.example.mdadil2019.mvpsample.http.apimodel.Result;
import com.example.mdadil2019.mvpsample.http.apimodel.TopRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainRepository implements MainActivityRepository {

    private MoreInfoApiService moreInfoApiService;
    private MovieApiService movieApiService;
    private List<String> countries;
    private List<Result> results;
    private long timestamp;

    private static final long STALE_MS = 20 * 1000;//data is state after 20 seconds

    //constructor being called by dagger while mapping dependencies
    public MainRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService){
        this.moreInfoApiService = moreInfoApiService;
        this.timestamp = System.currentTimeMillis();
        this.movieApiService = movieApiService;
        countries = new ArrayList<>();
        results = new ArrayList<>();


    }

    /*
    this function helps us find that weather the current adapter is updated or not by detecting
    if last time data fetched is greater then 20 second or not
    */
    public boolean isUpToDate(){
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }
    @Override
    public Observable<Result> getResultsFromMemory() {
        if(isUpToDate()){
            return Observable.fromIterable(results);
        } else{
            timestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getResultsFromNetwork() {
        /*
        MovieApiService is an interface which uses retrofit to request API
        with particular parameter in @GET and @Query
         */

        Observable<TopRated> topRatedObservable = movieApiService.getTopRatedMovies(1)
                .concatWith(movieApiService.getTopRatedMovies(2))
                .concatWith(movieApiService.getTopRatedMovies(3));

        return topRatedObservable.concatMap(new Function<TopRated, ObservableSource<? extends Result>>() {
            @Override
            public ObservableSource<? extends Result> apply(TopRated topRated) throws Exception {
                return Observable.fromIterable(topRated.results);
            }
        }).doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                results.add(result);
            }
        });
    }

    @Override
    public Observable<String> getCountriesFromMemory() {
        if(isUpToDate()){
            return Observable.fromIterable(countries);
        }else{
            timestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountriesFromNetwork() {
        return getResultsFromNetwork().concatMap(new Function<Result, ObservableSource<? extends OmdbApi>>() {
            @Override
            public ObservableSource<? extends OmdbApi> apply(Result result) throws Exception {
                return moreInfoApiService.getCountry(result.title);
            }
        }).concatMap(new Function<OmdbApi, ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> apply(OmdbApi omdbApi) throws Exception {
                return Observable.just(omdbApi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                countries.add(s);
            }
        });
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountriesFromMemory().switchIfEmpty(getCountriesFromNetwork());
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultsFromMemory().switchIfEmpty(getResultsFromNetwork());
    }
}
