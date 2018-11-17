package com.example.mdadil2019.mvpsample;

import com.example.mdadil2019.mvpsample.http.apimodel.Result;

import io.reactivex.Observable;

public interface MainActivityRepository {

    Observable<Result> getResultsFromMemory();

    Observable<Result> getResultsFromNetwork();

    Observable<String> getCountriesFromMemory();

    Observable<String> getCountriesFromNetwork();

    Observable<String> getCountryData();

    Observable<Result> getResultData();
}
