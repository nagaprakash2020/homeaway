package com.ndanda.homeaway.api;

import android.arch.lifecycle.LiveData;

import com.ndanda.homeaway.data.SeatGeekEvent;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ndanda on 4/10/2018.
 */

public interface ApiService {

    @GET("events")
    LiveData<ApiResponse<SeatGeekEvent>> getEvents(
            @Query("q") String searchString,
            @Query("client_id") String clientId);
}
