package com.ndanda.homeaway.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.R;
import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.databinding.ActivityLandingBinding;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;

import java.util.Stack;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity implements SearchFragment.OnSearchFragmentInteractionListener,
ResultDetailFragment.ResultsDetailFragmentListener{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ActivityLandingBinding activityLandingBinding;
    ResultsViewModel resultsViewModel;
    SearchFragment searchFragment;
    private Stack<Fragment> fragmentStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((HomeAwayApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);

        fragmentStack = new Stack<>();
        activityLandingBinding = DataBindingUtil.setContentView(this,R.layout.activity_landing);

        resultsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ResultsViewModel.class);

        getFavoriteEvents();
        showSearchFragment();
    }

    private void getFavoriteEvents(){
        resultsViewModel
    }

    private void showSearchFragment() {
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getName());

        if(searchFragment == null){
            searchFragment = SearchFragment.newInstance();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, searchFragment,SearchFragment.class.getName());
        fragmentStack.push(searchFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSearchStringUpdated(String searchString) {

        if(searchString == null || searchString.isEmpty()){
            searchFragment.updateSearchResults(null);
        }else {
            resultsViewModel.getSeatGeekEvent(searchString).observe(this, new Observer<ApiResponse<SeatGeekEvent>>() {
                @Override
                public void onChanged(@Nullable ApiResponse<SeatGeekEvent> seatGeekEventApiResponse) {
                    if(seatGeekEventApiResponse != null && seatGeekEventApiResponse.body != null){
                        searchFragment.updateSearchResults(seatGeekEventApiResponse.body.getEvents());
                    }
                }
            });
        }
    }

    @Override
    public void onSearchItemResultSelected(events event) {

        ResultDetailFragment detailFragment = ResultDetailFragment.newInstance(event);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, detailFragment,ResultDetailFragment.class.getName());
        fragmentStack.lastElement().onPause();
        fragmentTransaction.hide(fragmentStack.lastElement());
        fragmentStack.push(detailFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (fragmentStack.size() == 2) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentStack.lastElement().onPause();
            fragmentTransaction.remove(fragmentStack.pop());
            fragmentStack.lastElement().onResume();
            fragmentTransaction.show(fragmentStack.lastElement());
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFavoriteAdded(events events) {
        resultsViewModel.addEventToFavorite(events);
    }

    @Override
    public void onFavoriteRemoved(events event) {
        resultsViewModel.removeEventFromFavorite(event);
    }
}