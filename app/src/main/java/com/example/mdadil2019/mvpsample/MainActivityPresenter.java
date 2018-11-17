package com.example.mdadil2019.mvpsample;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private MainActivityMVP.View view;
    private Subscription  subscription = null;
    private MainActivityMVP.Model model;
    private Disposable disposable = null;

    public MainActivityPresenter(MainActivityMVP.Model model){
        this.model = model;
    }

    @Override
    public void loadData() {
        //loading data in reactive form using rxjava
         model.result().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ViewModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ViewModel viewModel) {
                if(view!=null){
                    view.updateData(viewModel);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(view!=null){
                    view.showSnackbar("Error retrieving data");
                }
            }

            @Override
            public void onComplete() {
                if(view!=null){
                    view.showSnackbar("Data retrieval completed");
                }
            }
        });

    }

    @Override
    public void rxUnsubscribe() {
        //let's look more into rxjava to discover how to unsubscribe from Subscription
        disposable.dispose();
    }

    @Override
    public void setView(MainActivityMVP.View view) {
        this.view = view;

    }
}
