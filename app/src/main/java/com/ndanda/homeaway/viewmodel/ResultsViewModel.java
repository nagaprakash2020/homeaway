package com.ndanda.homeaway.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.ndanda.homeaway.api.ApiResponse;
import com.ndanda.homeaway.data.SeatGeekEvent;
import com.ndanda.homeaway.data.events;
import com.ndanda.homeaway.repository.HomeAwayRepository;

import java.util.List;
import java.util.Locale;

public class ResultsViewModel extends ViewModel {

    private HomeAwayRepository repository;
    private LiveData<List<events>> favoriteEvents;
    private LiveData<ApiResponse<SeatGeekEvent>> searchResultEvents;
    private MediatorLiveData<List<events>> searchResultsWithFavorites = new MediatorLiveData<>();
    private MutableLiveData<String> searchString = new MutableLiveData<>();
    private MutableLiveData<events> selectedEvent = new MutableLiveData<>();

    public ResultsViewModel(HomeAwayRepository repository) {
        this.repository = repository;

        // Get favorite events
        favoriteEvents = repository.getFavoriteEvents();

        /**
         *  @searchResultEvents will be updated when there is a change in @searchString
         */
        searchResultEvents = Transformations.switchMap(searchString, search -> repository.getResults(searchString.getValue()));


        /**
         *  @searchResultsWithFavorites will be updated when there is a change in @searchResultEvents
         */
        searchResultsWithFavorites.addSource(searchResultEvents, search -> {
            if (searchResultEvents != null && searchResultEvents.getValue() != null && searchResultEvents.getValue().body != null) {
                if (favoriteEvents == null || favoriteEvents.getValue() == null) {
                    searchResultsWithFavorites.setValue(searchResultEvents.getValue().body.getEvents());
                } else {

                    for (int i = 0; i < searchResultEvents.getValue().body.getEvents().size(); i++) {
                        if (favoriteEvents.getValue().contains(searchResultEvents.getValue().body.getEvents().get(i))) {
                            searchResultEvents.getValue().body.getEvents().get(i).setFavorite(true);
                        }
                    }

                    searchResultsWithFavorites.setValue(searchResultEvents.getValue().body.getEvents());
                }
            }
        });


        /**
         *  @searchResultsWithFavorites will be updated when there is a change in @favoriteEvents
         */
        searchResultsWithFavorites.addSource(favoriteEvents, search -> {
            if (searchResultEvents != null && searchResultEvents.getValue() != null && searchResultEvents.getValue().body != null) {
                if (favoriteEvents == null || favoriteEvents.getValue() == null) {
                    searchResultsWithFavorites.setValue(searchResultEvents.getValue().body.getEvents());
                } else {

                    for (int i = 0; i < searchResultEvents.getValue().body.getEvents().size(); i++) {
                        if (favoriteEvents.getValue().contains(searchResultEvents.getValue().body.getEvents().get(i))) {
                            searchResultEvents.getValue().body.getEvents().get(i).setFavorite(true);
                        }
                    }

                    searchResultsWithFavorites.setValue(searchResultEvents.getValue().body.getEvents());
                }
            }
        });

    }

    public void setSearchString(@NonNull String query) {
        String input = query.toLowerCase(Locale.getDefault()).trim();
        searchString.setValue(input);
    }

    public MutableLiveData<String> getSearchString() {
        return searchString;
    }

    public MutableLiveData<events> getSelectedEvent() {
        return selectedEvent;
    }

    public LiveData<List<events>> getSearchResultsWithFavorites() {
        return searchResultsWithFavorites;
    }

    public void addEventToFavorite() {
        if (getSelectedEvent() != null && getSelectedEvent().getValue() != null) {
            events event = getSelectedEvent().getValue();
            event.setFavorite(true);
            getSelectedEvent().setValue(event);
            repository.saveFavoriteEvent(getSelectedEvent().getValue());
        }
    }

    public void removeEventFromFavorite() {
        if (getSelectedEvent() != null && getSelectedEvent().getValue() != null) {
            events event = getSelectedEvent().getValue();
            event.setFavorite(false);
            getSelectedEvent().setValue(event);
            repository.removeEventFromFavorite(getSelectedEvent().getValue());
        }
    }

}
