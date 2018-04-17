package com.ndanda.homeaway.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.R;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.databinding.ActivityLandingBinding;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;

import java.util.List;
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
    public List<events> events;
    private String currentSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((HomeAwayApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);

        fragmentStack = new Stack<>();
        activityLandingBinding = DataBindingUtil.setContentView(this,R.layout.activity_landing);

        resultsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ResultsViewModel.class);

        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getName());
        if(searchFragment == null)
            showSearchFragment();

        fragmentStack.push(searchFragment);
    }

    private void showSearchFragment() {

        if(searchFragment == null){
            searchFragment = SearchFragment.newInstance();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, searchFragment,SearchFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSearchStringUpdated(String searchString) {

        this.currentSearchString = searchString;
        if(searchString == null || searchString.isEmpty()){
            if(events != null)
                events.clear();
            searchFragment.updateSearchResults();
        }else {
            resultsViewModel.getSeatGeekEvent(searchString).observe(this, seatGeekEventApiResponse -> {
                if(seatGeekEventApiResponse != null && seatGeekEventApiResponse.body != null){

                    resultsViewModel.getFavoriteEvents().observe(LandingActivity.this,favoriteEvents -> {
                        events = seatGeekEventApiResponse.body.getEvents();

                        for(int i=0; i< events.size(); i++){
                            if(favoriteEvents.contains(events.get(i))){
                                events.get(i).setFavorite(true);
                            }
                        }

                        searchFragment.updateSearchResults();
                    });
                }
            });
        }
    }

    @Override
    public void onSearchItemResultSelected(events event) {

        ResultDetailFragment detailFragment = ResultDetailFragment.newInstance(event,currentSearchString);

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
    public void onFavoriteAdded(events event) {
        updateCurrentList(event);

        resultsViewModel.addEventToFavorite(event);
    }

    @Override
    public void onFavoriteRemoved(events event) {
        updateCurrentList(event);
        resultsViewModel.removeEventFromFavorite(event);
    }

    private void updateCurrentList(events event) {
        try{
            int index = this.events.indexOf(event);
            if(index >= 0){
                this.events.set(index,event);
            }
        }catch (ClassCastException | NullPointerException e){
            e.printStackTrace();
        }
    }
}