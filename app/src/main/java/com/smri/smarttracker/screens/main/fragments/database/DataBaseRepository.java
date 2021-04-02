package com.smri.smarttracker.screens.main.fragments.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataBaseRepository implements DataBaseContract.Repository {

    DataBaseContract.Presenter mPresenter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences mSP;
    public static final String APP_PREFERENCES_LABORATORY = "laboratory";

    public DataBaseRepository(SharedPreferences sp){
        mSP = sp;
    }

    @Override
    public void attachPresenter(DataBaseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadChemicalsDB() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DocumentReference userDoc = db.collection("users").document("testId"); //TODO!!!!
        final String[] laboratory = new String[1];
        final SharedPreferences.Editor editor = mSP.edit();
        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult() != null){
                        laboratory[0] = task.getResult().getString("laboratory");
                        editor.putString(APP_PREFERENCES_LABORATORY,laboratory[0]);
                        editor.apply();
                        loadListFromLab(laboratory[0]);
                    }
                }
            }
        });
    }

    void loadListFromLab(String laboratory){
        final CollectionReference collRef = db.collection("databases").document(laboratory).collection("chemicals");
        collRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult() != null){
                        QuerySnapshot allChems = task.getResult();
                        ArrayList<Chemical> listChems = new ArrayList<>();
                        for(DocumentSnapshot doc : allChems.getDocuments()){
                            String id = doc.getId();
                            String name = doc.getString("name");
                            String desc = doc.getString("description");
                            listChems.add(new Chemical(id,name,desc));
                        }
                        mPresenter.onDataLoaded(listChems);
                    }
                }
            }
        });
    }
}
