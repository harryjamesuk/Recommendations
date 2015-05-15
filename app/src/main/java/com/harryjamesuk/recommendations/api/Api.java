package com.harryjamesuk.recommendations.api;

import com.harryjamesuk.recommendations.model.ActiveListings;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Harry on 15/05/2015.
 */
public interface Api {

    @GET("/listings/active")
    void activeListings(@Query("includes") String includes,
                        Callback<ActiveListings> callback);
}
