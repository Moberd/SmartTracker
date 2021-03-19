package com.smri.smarttracker.screens.main.fragments.database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.editor.ChemEditor;
import com.smri.smarttracker.screens.main.fragments.database.adapter.ChemicalsAdapter;
import com.smri.smarttracker.utils.Chemical;
import com.smri.smarttracker.utils.FabFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class DataBaseFragment extends Fragment implements DataBaseContract.View {

    private RecyclerView recyclerView;
    private ChemicalsAdapter adapter;
    private ProgressBar progressBar;
    public FabFragmentListener listener;
    final DataBaseContract.Presenter mPresenter = new DataBasePresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_database, container, false);
        progressBar = view.findViewById(R.id.progressTasks);
        recyclerView = view.findViewById(R.id.database_chem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<Chemical> list = new ArrayList<Chemical>();
        adapter = new ChemicalsAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);

        mPresenter.loadData();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    public void addNewChemical(){
        Toast.makeText(getContext(), "DATABASE CLICKED", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ChemEditor.class);
        getContext().startActivity(intent);
    }

    @Override
    public void updateList(ArrayList<Chemical> items) {
        adapter.updateItems(items);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }
}
