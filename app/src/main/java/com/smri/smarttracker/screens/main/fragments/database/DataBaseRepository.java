package com.smri.smarttracker.screens.main.fragments.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

public class DataBaseRepository implements DataBaseContract.Repository {

    DataBaseContract.Presenter mPresenter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences mSP;
    public static final String APP_PREFERENCES_LABORATORY = "laboratory";
    public static final String APP_PREFERENCES_USER = "user";
    public static final String APP_PREFERENCES_LOCATION = "laboratory_number";

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
        final DocumentReference userDoc = db.collection("users").document(userId);
        final SharedPreferences.Editor editor = mSP.edit();
        userDoc.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(task.getResult() != null){
                    String laboratory = task.getResult().getString("laboratory").toLowerCase();
                    String name = task.getResult().getString("name");
                    String loc = task.getResult().getString("laboratory_number");
                    editor.putString(APP_PREFERENCES_LABORATORY,laboratory);
                    editor.putString(APP_PREFERENCES_USER,name);
                    editor.putString(APP_PREFERENCES_LOCATION,loc);
                    editor.apply();
                    loadListFromLab(laboratory);
                }
            }else{
                Log.e(TAG, "onFailure: ", task.getException());
            }
        });
    }

    void loadListFromLab(String laboratory){
        final CollectionReference collRef = db.collection("databases").document(laboratory).collection("chemicals");
        collRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(task.getResult() != null){
                    QuerySnapshot allChems = task.getResult();
                    ArrayList<Chemical> listChems = new ArrayList<>();
                    for(DocumentSnapshot doc : allChems.getDocuments()){
                        Chemical item = doc.toObject(Chemical.class);
                        listChems.add(item);
                    }
                    mPresenter.onDataLoaded(listChems);
                }
            }
        });
    }
}
