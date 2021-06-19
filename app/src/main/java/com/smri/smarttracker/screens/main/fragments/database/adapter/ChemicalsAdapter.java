package com.smri.smarttracker.screens.main.fragments.database.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.editor.ChemEditorActivity;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.List;

public class ChemicalsAdapter extends RecyclerView.Adapter<ChemicalsAdapter.ViewHolder> implements Filterable {

    private ArrayList<Chemical> listItems = new ArrayList<>();
    private ArrayList<Chemical> filteredList = new ArrayList<>();
    private final Context mContext;

    public ChemicalsAdapter(ArrayList<Chemical> listItems, Context mContext) {
        this.listItems = listItems;
        this.filteredList = listItems;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chemical_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Chemical itemList = filteredList.get(position);
        holder.name.setText(itemList.getName());
        holder.description.setText(itemList.getDescription());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ChemEditorActivity.class);
            intent.putExtra("CHEM_ID",filteredList.get(position).getId());
            intent.putExtra("name",filteredList.get(position).getName());
            intent.putExtra("description",filteredList.get(position).getDescription());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = listItems;
                } else {
                    charString = charSequence.toString().toLowerCase();
                    ArrayList<Chemical> _filteredList = new ArrayList<>();
                    for (Chemical chem : listItems) {
                        if (chem.getName().toLowerCase().contains(charString) || chem.getDescription().toLowerCase().contains(charString)
                           || ((chem.getLocation() != null) && chem.getLocation().toLowerCase().contains(charString))
                           || ((chem.getCreator() != null) && chem.getCreator().toLowerCase().contains(charString))) {
                            _filteredList.add(chem);
                        }
                    }
                    filteredList = _filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Chemical>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
        }
    }

    public void updateItems(ArrayList<Chemical> items){
        listItems = items;
        filteredList = items;
        notifyDataSetChanged();
    }

}
