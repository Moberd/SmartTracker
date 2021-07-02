package com.smri.smarttracker.screens.editor;

import android.content.SharedPreferences;

import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;

public class ChemEditorPresenter implements ChemEditorContract.Presenter{

    boolean viewIsAttached = false;
    private ChemEditorContract.View mView = null;
    private SharedPreferences mSP;
    private ChemEditorContract.Repository mRepository;

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

    public void getChanges(String id,Chemical item){
        if(!checkEmptiness(item.getName(),item.getDescription())) {
            mRepository.sentChangesToDB(id, item);
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
    @Override
    public void getChemInfo(String id){
        mRepository.getChemInfo(id);
    }

    public void sendInfoToView(Chemical item){
        mView.writeInfo(item);
    }

    @Override
    public void getAutoCompData() {
        mRepository.getAutoCompFromDB();
    }

    @Override
    public void sendAutoCompData(ArrayList<String> data) {
        mView.attachAutoCompData(data);
    }

    @Override
    public void newChemScanned(String id) {
        mView.scannedNew(id);
    }

    public void changesComplete(){
        mView.closeActivity();
    }
}
