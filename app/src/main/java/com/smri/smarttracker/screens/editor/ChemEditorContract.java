package com.smri.smarttracker.screens.editor;


import com.smri.smarttracker.utils.Chemical;

public interface ChemEditorContract {
    interface View{
        void closeActivity();
        void showToast(String message,ToastMode mode);
        void writeInfo(Chemical item);
    }
    interface Presenter{
        void attachView(View view);
        void detachView();
        void getChanges(String id,Chemical item);
        void deleteChemical(String id);
        void changesComplete();
        void getChemInfo(String id);
        void sendInfoToView(Chemical item);
    }
    interface Repository{
        void attachPresenter(Presenter presenter);
        void sentChangesToDB(String id,Chemical item);
        void deleteFromDB(String id);
        void getChemInfo(String id);
    }
}
