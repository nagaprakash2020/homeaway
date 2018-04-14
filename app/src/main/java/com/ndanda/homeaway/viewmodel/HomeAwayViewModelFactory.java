package com.ndanda.homeaway.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.ndanda.homeaway.repository.HomeAwayRepository;

import javax.inject.Singleton;

@Singleton
public class HomeAwayViewModelFactory implements ViewModelProvider.Factory {

    private final HomeAwayRepository repository;

    public HomeAwayViewModelFactory(HomeAwayRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ResultsViewModel.class))
            return (T) new ResultsViewModel(repository);
        else
            throw new IllegalArgumentException("ViewModel Not Found");

    }
}
