package com.ndanda.homeaway;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.ndanda.homeaway.di.ApplicationComponent;
import com.ndanda.homeaway.di.ApplicationModule;
import com.ndanda.homeaway.di.DaggerApplicationComponent;

/**
 * Created by ndanda on 4/8/2018.
 */

public class HomeAwayApplication extends Application{

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    public ApplicationComponent getApplicationComponent() {
         return applicationComponent;
    }
}
