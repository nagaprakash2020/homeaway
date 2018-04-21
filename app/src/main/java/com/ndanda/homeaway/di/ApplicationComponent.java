package com.ndanda.homeaway.di;

import android.app.Application;

import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.view.LandingActivity;
import com.ndanda.homeaway.view.ResultDetailFragment;
import com.ndanda.homeaway.view.ResultsAdapter;
import com.ndanda.homeaway.view.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by ndanda on 4/8/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<HomeAwayApplication>{

    Application exposeApplication();
    void inject(LandingActivity landingActivity);
    void inject(ResultDetailFragment resultDetailFragment);
    void inject(SearchFragment searchFragment);
}
