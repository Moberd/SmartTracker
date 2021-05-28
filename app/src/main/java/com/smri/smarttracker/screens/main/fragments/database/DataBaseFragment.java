package com.smri.smarttracker.screens.main.fragments.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.editor.ChemEditorActivity;
import com.smri.smarttracker.screens.main.fragments.database.adapter.ChemicalsAdapter;
import com.smri.smarttracker.screens.main.scanner.CaptureActivityAnyOrientation;
import com.smri.smarttracker.utils.Chemical;
import com.smri.smarttracker.utils.FabFragmentListener;

import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class DataBaseFragment extends Fragment implements DataBaseContract.View {

    private RecyclerView recyclerView;
    private ChemicalsAdapter adapter;
    private ProgressBar progressBar;
    public FabFragmentListener listener;
    DataBaseContract.Presenter mPresenter;
    AlertDialog dialogAlert;
    String scanContent;
    String scanFormat;



    SharedPreferences mSP;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSP = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mPresenter = new DataBasePresenter(mSP);
        setHasOptionsMenu(true);
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

        mPresenter.attachView(this);
        mPresenter.loadData();
        dialogAlert = createDialog(view.getContext());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    public void addNewChemical(String new_id){
        Intent intent = new Intent(getContext(), ChemEditorActivity.class);
        intent.putExtra("CHEM_ID",new_id);
        getContext().startActivity(intent);
    }

    @Override
    public void updateList(ArrayList<Chemical> items) {
        adapter.updateItems(items);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    AlertDialog createDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("NEW ITEM");
        builder.setMessage("How do you want to add item?");
        builder.setCancelable(true);
        builder.setNegativeButton("SCAN CODE", (dialog, which) -> callScanner());
        builder.setPositiveButton("ADD NEW CHEM", (dialog, which) -> addNewChemical("NEWRECORD"));
        return builder.create();
    }

    public void addItem(){
        dialogAlert.show();
    }

    public void callScanner(){
        IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(DataBaseFragment.this);
        scanIntegrator.setPrompt("Scan");
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }
            Toast.makeText(getActivity(), scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
            addNewChemical(scanContent);
        } else {
            Toast.makeText(getActivity(), "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }


}
