package com.ndanda.homeaway.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndanda.homeaway.R;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.databinding.FragmentResultDetailBinding;

public class ResultDetailFragment extends Fragment implements View.OnClickListener{

    private events event;
    private String searchString;
    private ResultsDetailFragmentListener mListener;
    private FragmentResultDetailBinding fragmentResultDetailBinding;

    private static final String SEARCH_STRING = "SearchString";

    public interface ResultsDetailFragmentListener{
        void onFavoriteAdded(events events);
        void onFavoriteRemoved(events events);
    }

    public ResultDetailFragment() {
        // Required empty public constructor
    }

    public static ResultDetailFragment newInstance(events event,String searchString) {
        ResultDetailFragment fragment = new ResultDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(events.class.getName(), event);
        args.putString(SEARCH_STRING,searchString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(events.class.getName());
            searchString = getArguments().getString(SEARCH_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentResultDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_result_detail,container,false);

        fragmentResultDetailBinding.setEvent(event);
        fragmentResultDetailBinding.setSearchString(searchString);
        fragmentResultDetailBinding.favorite.setSelected(event.getFavorite());
        fragmentResultDetailBinding.favorite.setOnClickListener(this);
        return fragmentResultDetailBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ResultsDetailFragmentListener) {
            mListener = (ResultsDetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ResultsDetailFragmentListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if(!fragmentResultDetailBinding.favorite.isSelected()){
            fragmentResultDetailBinding.favorite.setSelected(true);
            event.setFavorite(true);
            mListener.onFavoriteAdded(event);
        }else {
            fragmentResultDetailBinding.favorite.setSelected(false);
            event.setFavorite(false);
            mListener.onFavoriteRemoved(event);
        }

    }
}
