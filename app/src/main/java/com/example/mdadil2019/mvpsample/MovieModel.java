package com.example.mdadil2019.mvpsample;

import com.example.mdadil2019.mvpsample.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class MovieModel implements MainActivityMVP.Model {

    private MainActivityRepository repository;

    public MovieModel(MainActivityRepository repository){
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(), new BiFunction<Result, String, ViewModel>() {
            @Override
            public ViewModel apply(Result result, String s) throws Exception {
                return new ViewModel(result.title,s);
            }
        });
    }
}
