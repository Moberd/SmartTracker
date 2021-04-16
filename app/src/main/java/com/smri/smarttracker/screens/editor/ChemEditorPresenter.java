package com.smri.smarttracker.screens.editor;

import android.content.SharedPreferences;

public class ChemEditorPresenter implements ChemEditorContract.Presenter{

    boolean viewIsAttached = false;
    ChemEditorContract.View mView = null;
    SharedPreferences mSP;
    ChemEditorContract.Repository mRepository;

    public ChemEditorPresenter(SharedPreferences sp){
        mSP = sp;
        mRepository = new ChemEditorRepository(mSP);
    }

    @Override
    public void attachView(ChemEditorContract.View view) {
        mView = view;
        viewIsAttached = true;
        mRepository.attachPresenter(this);
    }

    @Override
    public void detachView() {
        mView = null;
        viewIsAttached = false;
    }

    boolean checkEmptiness(String name,String description){
        boolean result = false;
        if (name.trim().equals("")) {
            mView.showToast("Name is empty",ToastMode.ERROR);
            result = true;
        } else if (description.trim().equals("")) {
            mView.showToast("Description is empty",ToastMode.ERROR);
            result = true;
        }
        return result;
    }

    public void getChanges(String id,String name,String description){
        if(!checkEmptiness(name,description)) {
            mRepository.sentChangesToDB(id, name, description);
            mView.showToast("Successful",ToastMode.SUCCESS);
        } else {
            mView.showToast("Empty fields",ToastMode.ERROR);
        }
    }

    @Override
    public void deleteChemical(String id) {
        if(!(id.equals("NEWRECORD") || id.trim().equals(""))){
            mRepository.deleteFromDB(id);
            mView.showToast("Deleted successful",ToastMode.SUCCESS);
        }
    }

    public void changesComplete(){
        mView.closeActivity();
    }
}
