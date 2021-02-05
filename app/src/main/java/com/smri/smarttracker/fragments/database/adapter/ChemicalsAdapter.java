package com.smri.smarttracker.fragments.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smri.smarttracker.R;
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chemical_cardview, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final Chemical itemList = listItems.get(position);
            String _name = itemList.getName();
            holder.name.setText(itemList.getName());
            holder.description.setText(itemList.getDescription());
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView description;
            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                description = (TextView) itemView.findViewById(R.id.description);
            }
        }
}
