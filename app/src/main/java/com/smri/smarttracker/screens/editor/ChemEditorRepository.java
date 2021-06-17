package com.smri.smarttracker.screens.editor;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChemEditorRepository implements ChemEditorContract.Repository {
    ChemEditorContract.Presenter mPresenter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String APP_PREFERENCES_LABORATORY = "laboratory";
    SharedPreferences mSP;

    public ChemEditorRepository(SharedPreferences sp){
        mSP = sp;
    }
    @Override
    public void attachPresenter(ChemEditorContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void sentChangesToDB(String id,Chemical item) {
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(!id.equals("NEWRECORD")) {
            db.collection("databases").document(laboratory).collection("chemicals").document(id).set(item, SetOptions.merge());
        } else {
            db.collection("databases").document(laboratory).collection("chemicals").add(item);
        }
        String loc = item.getLocation();
        Query capitalCities = db.collection("databases").document(laboratory).collection("locations").whereEqualTo("name", loc);
        capitalCities.get().addOnSuccessListener(queryDocumentSnapshots -> {
           if(queryDocumentSnapshots.isEmpty()){
               addNewLoc(loc);
           }
        });
        mPresenter.changesComplete();
    }

    public void addNewLoc(String location){
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        Map<String,String> map = new HashMap<>();
        map.put("name",location);
        db.collection("databases").document(laboratory).collection("locations").add(map);
    }

    @Override
    public void deleteFromDB(String id) {
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        db.collection("databases").document(laboratory).collection("chemicals").document(id).delete();
        mPresenter.changesComplete();
    }

    @Override
    public void getChemInfo(String id){
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        DocumentReference docRef = db.collection("databases").document(laboratory).collection("chemicals").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                Chemical item = documentSnapshot.toObject(Chemical.class);
                mPresenter.sendInfoToView(item);
            }
        });
    }

    @Override
    public void getAutoCompFromDB() {
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        CollectionReference colRef = db.collection("databases").document(laboratory).collection("locations");
        colRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(task.getResult() != null) {
                    QuerySnapshot allLocations = task.getResult();
                    ArrayList<String> listLocations = new ArrayList<>();
                    for(DocumentSnapshot doc : allLocations.getDocuments()){
                        String name = doc.getString("name");
                        listLocations.add(name);
                    }
                    mPresenter.sendAutoCompData(listLocations);
                }
            }
        });
    }


}
