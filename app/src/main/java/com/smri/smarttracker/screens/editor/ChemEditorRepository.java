package com.smri.smarttracker.screens.editor;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

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
    public void sentChangesToDB(String id,String name, String description) {
        Map<String, Object> data = new HashMap<>();
        data.put("name",name);
        data.put("description", description);
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(!id.equals("NEWRECORD")) {
            db.collection("databases").document(laboratory).collection("chemicals").document(id).set(data, SetOptions.merge());
        } else {
            db.collection("databases").document(laboratory).collection("chemicals").add(data);
        }
        mPresenter.changesComplete();
    }

    @Override
    public void deleteFromDB(String id) {
        String laboratory = mSP.getString(APP_PREFERENCES_LABORATORY,"");
        db.collection("databases").document(laboratory).collection("chemicals").document(id).delete();
        mPresenter.changesComplete();
    }


}
