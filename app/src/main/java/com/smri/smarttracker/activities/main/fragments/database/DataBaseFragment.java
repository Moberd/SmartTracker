package com.smri.smarttracker.activities.main.fragments.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smri.smarttracker.R;
import com.smri.smarttracker.activities.editor.ChemEditor;
import com.smri.smarttracker.activities.main.fragments.database.adapter.ChemicalsAdapter;
import com.smri.smarttracker.utils.Chemical;
import com.smri.smarttracker.utils.FabFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class DataBaseFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChemicalsAdapter adapter;
    public FabFragmentListener listener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentActivity database = getActivity();
        final View view = inflater.inflate(R.layout.fragment_database, container, false);
        recyclerView = view.findViewById(R.id.database_chem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        List<Chemical> listChems = new ArrayList<Chemical>();
        for (int i = 0; i<10; i++) {
            listChems.add(new Chemical("Item " + (i + 1), "This is description of item " + (i+1)));
        }

        adapter = new ChemicalsAdapter(listChems, database);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void addNewChemical(){
        Toast.makeText(getContext(), "DATABASE CLICKED", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ChemEditor.class);
        getContext().startActivity(intent);
    }
}
