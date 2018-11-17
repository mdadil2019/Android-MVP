package com.example.mdadil2019.mvpsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.mdadil2019.mvpsample.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View{

    @BindView(R.id.root_listView)
    ViewGroup viewGroup;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();

    @Inject MainActivityMVP.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dependency injection
        ((App)getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupAdapter();

    }

    private void setupAdapter() {
        listAdapter = new ListAdapter(resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        if (resultList.isEmpty()){
            listAdapter.notifyItemInserted(0);
        }else{
            listAdapter.notifyItemInserted(resultList.size()-1);
        }
    }

    @Override
    public void showSnackbar(String s) {

    }
}
