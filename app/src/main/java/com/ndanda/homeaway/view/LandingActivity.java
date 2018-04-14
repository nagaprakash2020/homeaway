package com.ndanda.homeaway.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.R;
import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;
import com.ndanda.homeaway.vo.Status;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class LandingActivity extends AppCompatActivity implements ResultsListAdapter.ResultsListAdapterListener{

    @BindView(R.id.search_view)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.tool_bar_results)
    ListView resultsView;

    ResultsListAdapter resultsListAdapter;

    //TODO delete this
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ResultsViewModel resultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((HomeAwayApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        resultsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ResultsViewModel.class);
        resultsListAdapter = new ResultsListAdapter(this,R.layout.results_row,this);

        autoCompleteTextView.setAdapter(resultsListAdapter);

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
