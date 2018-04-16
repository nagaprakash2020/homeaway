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
    private ResultsDetailFragmentListener mListener;
    private FragmentResultDetailBinding fragmentResultDetailBinding;

    public interface ResultsDetailFragmentListener{
        void onFavoriteAdded(events events);
        void onFavoriteRemoved(events events);
    }

    public ResultDetailFragment() {
        // Required empty public constructor
    }

    public static ResultDetailFragment newInstance(events event) {
        ResultDetailFragment fragment = new ResultDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(events.class.getName(), event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(events.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentResultDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_result_detail,container,false);
        fragmentResultDetailBinding.setEvent(event);
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if(!fragmentResultDetailBinding.favorite.isSelected()){
            fragmentResultDetailBinding.favorite.setSelected(true);
            mListener.onFavoriteAdded(event);
        }else {
            fragmentResultDetailBinding.favorite.setSelected(false);
            mListener.onFavoriteRemoved(event);
        }

    }
}
