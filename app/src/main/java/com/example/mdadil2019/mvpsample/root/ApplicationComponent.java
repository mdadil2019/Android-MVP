package com.example.mdadil2019.mvpsample.root;

import com.example.mdadil2019.mvpsample.MainActivity;
import com.example.mdadil2019.mvpsample.MainActivityModule;
import com.example.mdadil2019.mvpsample.http.ApiModuleForInfo;
import com.example.mdadil2019.mvpsample.http.ApiModuleForName;

import dagger.Component;

@Component(modules = {ApplicationModule.class, ApiModuleForInfo.class, ApiModuleForName.class, MainActivityModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
}
