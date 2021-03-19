package com.smri.smarttracker.screens.main.fragments.database.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.editor.ChemEditor;
import com.smri.smarttracker.utils.Chemical;

import java.util.List;

public class ChemicalsAdapter extends RecyclerView.Adapter<ChemicalsAdapter.ViewHolder> {

    private List<Chemical> listItems;
    private Context mContext;

    public ChemicalsAdapter(List<Chemical> listItems, Context mContext) {
        this.listItems = listItems;
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

        final Chemical itemList = listItems.get(position);
        String _name = itemList.getName();
        holder.name.setText(itemList.getName());
        holder.description.setText(itemList.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ChemEditor.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

}
