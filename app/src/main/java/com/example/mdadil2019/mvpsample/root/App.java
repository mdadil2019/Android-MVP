package com.example.mdadil2019.mvpsample.root;

import android.app.Application;

import com.example.mdadil2019.mvpsample.MainActivityModule;
import com.example.mdadil2019.mvpsample.http.ApiModuleForInfo;
import com.example.mdadil2019.mvpsample.http.ApiModuleForName;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .mainActivityModule(new MainActivityModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .apiModuleForName(new ApiModuleForName())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
