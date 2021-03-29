package com.smri.smarttracker.screens.editor;


public interface ChemEditorContract {
    interface View{
        void closeActivity();
    }
    interface Presenter{
        void attachView(View view);
        void detachView();
        void getChanges(String id,String name,String description);
        void changesComplete();
    }
    interface Repository{
        void attachPresenter(Presenter presenter);
        void sentChangesToDB(String id,String name,String description);
    }
}
