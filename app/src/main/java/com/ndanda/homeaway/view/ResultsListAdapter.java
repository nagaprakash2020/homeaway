package com.ndanda.homeaway.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.ndanda.homeaway.R;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.databinding.ResultsRowBinding;

import java.util.List;

public class ResultsListAdapter extends ArrayAdapter<events> {

    private List<events> eventsList;
    private ResultsListAdapterListener listener;

    public interface ResultsListAdapterListener{
        void onTextChanged(String text);
    }


    public ResultsListAdapter(@NonNull Context context, int resource,ResultsListAdapterListener listener) {
        super(context, resource);
        this.listener = listener;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint != null)
                    listener.onTextChanged(constraint.toString());
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ResultsViewHolder viewHolder;


        if(convertView == null){
            ResultsRowBinding resultsRowBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.results_row,
                            parent, false);
            viewHolder = new ResultsViewHolder(resultsRowBinding);
            convertView = resultsRowBinding.getRoot();
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ResultsViewHolder) convertView.getTag();
        }

        events event = getItem(position);

        viewHolder.setEvent(event);

        return convertView;
    }

    public void setEventsList(List<events> eventsList){
        this.eventsList = eventsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventsList == null || eventsList.isEmpty() ? 0 : eventsList.size();
    }

    @Nullable
    @Override
    public events getItem(int position) {
        return eventsList.get(position);
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{

        ResultsRowBinding resultsRowBinding;

        public ResultsViewHolder(ResultsRowBinding resultsRowBinding) {
            super(resultsRowBinding.getRoot());
            this.resultsRowBinding = resultsRowBinding;
        }

        public void setEvent(events event){
            resultsRowBinding.setEvent(event);
        }
    }
}
