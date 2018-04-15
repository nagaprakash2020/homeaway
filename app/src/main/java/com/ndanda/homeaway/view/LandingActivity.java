package com.ndanda.homeaway.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.R;
import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.databinding.ActivityLandingBinding;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity implements ResultsListAdapter.ResultsListAdapterListener{


    ActivityLandingBinding activityLandingBinding;
    ResultsListAdapter resultsListAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ResultsViewModel resultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((HomeAwayApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);

        activityLandingBinding = DataBindingUtil.setContentView(this,R.layout.activity_landing);

        resultsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ResultsViewModel.class);
        resultsListAdapter = new ResultsListAdapter(this,R.layout.results_row,this);

        activityLandingBinding.searchView.setAdapter(resultsListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTextChanged(String text) {
        resultsViewModel.getSeatGeekEvent(text).observe(this, new Observer<ApiResponse<SeatGeekEvent>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<SeatGeekEvent> seatGeekEventApiResponse) {
                if(seatGeekEventApiResponse != null && seatGeekEventApiResponse.body != null){
                    resultsListAdapter.setEventsList(seatGeekEventApiResponse.body.getEvents());
                }
            }
        });
    }
}
