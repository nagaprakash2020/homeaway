package com.ndanda.homeaway.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ndanda.homeaway.AppExecutors;
import com.ndanda.homeaway.HomeAwayApplication;
import com.ndanda.homeaway.api.ApiService;
import com.ndanda.homeaway.repository.FavouriteDao;
import com.ndanda.homeaway.repository.HomeAwayDatabase;
import com.ndanda.homeaway.repository.HomeAwayRepository;
import com.ndanda.homeaway.utils.LiveDataCallAdapterFactory;
import com.ndanda.homeaway.viewmodel.HomeAwayViewModelFactory;
import com.ndanda.homeaway.viewmodel.ResultsViewModel;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ndanda on 4/8/2018.
 */

@Module
public class ApplicationModule {

    private final HomeAwayApplication application;

    public ApplicationModule(HomeAwayApplication homeAwayApplication){
        application = homeAwayApplication;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(HomeAwayRepository repository){
        return new HomeAwayViewModelFactory(repository);
    }

    @Provides
    @Singleton
    AppExecutors providesAppExecutors(){
        return new AppExecutors();
    }


    @Provides
    @Named("dataBase")
    String providesDatabasePath(){
        return "HomeAway.db";
    }

    @Singleton
    @Provides
    HomeAwayDatabase provideHomeAwayDatabase(Application application, @Named("dataBase") String databaseName){
        return Room.databaseBuilder(
                application,
                HomeAwayDatabase.class,
                databaseName)
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    FavouriteDao provideFavoriteDao(HomeAwayDatabase homeAwayDatabase) {
        return homeAwayDatabase.favouriteDao();
    }

    @Provides
    @Singleton
    HomeAwayRepository provideHomeAloneRepository(AppExecutors appExecutors, ApiService apiService,FavouriteDao favouriteDao){
        return new HomeAwayRepository(appExecutors,apiService,favouriteDao);
    }

    @Provides
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return httpClient;
    }

    @Provides
    @Named("baseUrl")
    String provideBaseUrl(){
        return "https://api.seatgeek.com/2/";
    }

    @Provides
    @Singleton
    Retrofit provideRetroFit(OkHttpClient.Builder okHttpClientBuilder, @Named("baseUrl") String baseUrl){


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClientBuilder.build())
                .build();

    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
