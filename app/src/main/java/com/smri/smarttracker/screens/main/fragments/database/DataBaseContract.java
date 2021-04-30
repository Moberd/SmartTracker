package com.smri.smarttracker.screens.main.fragments.database;

import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.List;

public interface DataBaseContract {
    interface View {
        void updateList(ArrayList<Chemical> items);
        void addNewChemical(String new_id);
    }

    interface Presenter {
        void attachView(DataBaseContract.View view);
        void detachView();
        void onDataLoaded(ArrayList<Chemical> items);
        void loadData();
    }

    interface Repository {
        void attachPresenter(DataBaseContract.Presenter presenter);
        void loadChemicalsDB();
    }

}
