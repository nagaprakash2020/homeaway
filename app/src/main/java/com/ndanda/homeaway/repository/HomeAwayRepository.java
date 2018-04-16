package com.ndanda.homeaway.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ndanda.homeaway.AppExecutors;
import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.api.ApiService;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class HomeAwayRepository {

    private final AppExecutors appExecutors;
    private final ApiService apiService;
    private FavouriteDao favouriteDao;

    @Inject
    public HomeAwayRepository(AppExecutors appExecutors, ApiService apiService,FavouriteDao favouriteDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.favouriteDao = favouriteDao;
    }

    public LiveData<Resource<SeatGeekEvent>> getSeatGeekEvent(String searchString) {

        return new NetworkBoundResource<SeatGeekEvent,SeatGeekEvent>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull SeatGeekEvent item) {
                // Don't save any results.
            }

            @Override
            protected boolean shouldFetch(@Nullable SeatGeekEvent data) {
                // Always fetch results from webservices
                return true;
            }

            @NonNull
            @Override
            protected LiveData<SeatGeekEvent> loadFromDb() {
                return new MediatorLiveData<>();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SeatGeekEvent>> createCall() {
                return null;
            }
        }.asLiveData();
    }

    public LiveData<ApiResponse<SeatGeekEvent>> getResults(String searchString){
        String clientID = "MTEyMTMxNzd8MTUyMzY1MzEyOS45NA";
        return apiService.getEvents(searchString,clientID);
    }

    public void saveFavoriteEvent(events event){
        try{
            Thread t = new Thread(() -> favouriteDao.insertFavoriteEvent(event));
            t.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeEventFromFavorite(events event){
        try{
            Thread t = new Thread(() -> favouriteDao.removeEventFromFavorite(event));
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<events>> getFavoriteEvents(){
        return favouriteDao.getFavoriteEvents();
    }
}
