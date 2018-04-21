package com.ndanda.homeaway.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.R;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.databinding.FragmentSearchBinding;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;

import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends Fragment implements ResultsAdapter.ResultsClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private OnSearchFragmentInteractionListener mListener;
    FragmentSearchBinding fragmentSearchBinding;
    ResultsAdapter resultsAdapter;
    ResultsViewModel resultsViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mListener = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeAwayApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
        resultsViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ResultsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        resultsAdapter = new ResultsAdapter(getContext(), this);
        fragmentSearchBinding.searchResultsList.setLayoutManager(mLayoutManager);
        fragmentSearchBinding.searchResultsList.setAdapter(resultsAdapter);
        fragmentSearchBinding.searchBar.addTextChangedListener(new CustomTextChangeListener());

        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void updateSearchResults(List<events> eventsList) {
        resultsAdapter.setEventsList(eventsList);
    }

    @Override
    public void onResultItemClicked(events event) {
        mListener.onSearchItemResultSelected(event);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSearchFragmentInteractionListener {
        void onSearchItemResultSelected(events event);
    }

    private class CustomTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            resultsViewModel.setSearchString(s.toString());

            resultsViewModel.getSearchResultsWithFavorites().observe(getActivity(), new Observer<List<events>>() {
                @Override
                public void onChanged(@Nullable List<events> eventsList) {
                    updateSearchResults(eventsList);
                }
            });
        }
    }
}
