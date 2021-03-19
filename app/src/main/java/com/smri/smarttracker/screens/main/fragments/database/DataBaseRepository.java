package com.smri.smarttracker.screens.main.fragments.database;

import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.List;

public class DataBaseRepository implements DataBaseContract.Repository {

    DataBaseContract.Presenter mPresenter;
    @Override
    public void attachPresenter(DataBaseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadChemicalsDB() {
        ArrayList<Chemical> listChems = new ArrayList<Chemical>();
        for (int i = 0; i<10; i++) {
            listChems.add(new Chemical("Item " + (i + 1), "This is description of item " + (i+1)));
        }
        sentToPres(listChems);
    }

    void sentToPres(ArrayList<Chemical> list){
        mPresenter.onDataLoaded(list);
    }
}
