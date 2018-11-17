package com.example.mdadil2019.mvpsample;

import com.example.mdadil2019.mvpsample.http.MoreInfoApiService;
import com.example.mdadil2019.mvpsample.http.MovieApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public MainActivityMVP.Presenter provideMainActivityPresenter(MainActivityMVP.Model mainAcitityModel){
        return new MainActivityPresenter(mainAcitityModel);
    }

    @Provides
    public MainActivityMVP.Model provideMainActivityModel(MainActivityRepository repository){
        return new MovieModel(repository);
    }

    @Provides
    public MainActivityRepository provideRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService){
        return new MainRepository(movieApiService,moreInfoApiService);
    }
}
