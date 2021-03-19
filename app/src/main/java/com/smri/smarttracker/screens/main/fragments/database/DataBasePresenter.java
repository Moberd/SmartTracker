package com.smri.smarttracker.screens.main.fragments.database;

import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;

public class DataBasePresenter implements DataBaseContract.Presenter {

    private boolean viewIsAttached = false;
    DataBaseContract.View mView = null;
    private final DataBaseRepository mRepository = new DataBaseRepository();

    @Override
    public void attachView(DataBaseContract.View view) {
        viewIsAttached = true;
        mView = view;
        mRepository.attachPresenter(this);
    }

    @Override
    public void detachView() {
        viewIsAttached = false;
        mView = null;
    }

    @Override
    public void onDataLoaded(ArrayList<Chemical> items) {
        if(viewIsAttached){
            mView.updateList(items);
        }
    }

    @Override
    public void loadData() {
        mRepository.loadChemicalsDB();
    }
}
