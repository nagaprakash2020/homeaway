package com.ndanda.homeaway.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.ndanda.homeaway.data.events;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> implements Filterable{

    List<events> eventsList;



    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null){
                    return filterResults;
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            }
        };
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ResultsRowBinding resultsBinding = DataBindingUtil.

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder{

        public ResultsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
