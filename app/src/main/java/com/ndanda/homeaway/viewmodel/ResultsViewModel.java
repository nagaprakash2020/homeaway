package com.ndanda.homeaway.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.repository.HomeAwayRepository;

import java.util.List;

public class ResultsViewModel extends ViewModel {

    HomeAwayRepository repository;

    public ResultsViewModel(HomeAwayRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<SeatGeekEvent>> getSeatGeekEvent(String searchString){
//        return repository.getSeatGeekEvent(searchString);
        return repository.getResults(searchString);
    }

    public void addEventToFavorite(events event){
        repository.saveFavoriteEvent(event);
    }

    public void removeEventFromFavorite(events event){
        repository.removeEventFromFavorite(event);
    }

    public LiveData<List<events>> getFavoriteEvents(){
        return repository.getFavoriteEvents();
    }
}
