package com.example.mdadil2019.mvpsample;


import io.reactivex.Observable;

public interface MainActivityMVP {

    interface View{
        void updateData(ViewModel viewModel);

        void showSnackbar(String s);
    }

    interface Presenter{
        void loadData();

        void rxUnsubscribe();

        void setView(MainActivityMVP.View view);

    }

    interface Model{

        Observable<ViewModel> result();


    }
}
