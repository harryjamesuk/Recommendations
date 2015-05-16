package com.harryjamesuk.recommendations.api;

import com.harryjamesuk.recommendations.model.ActiveListings;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Harry on 15/05/2015.
 */
public class Etsy {

    private static RequestInterceptor getInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key", ProtectedClass.ETSY_API_KEY);
            }
        };
    }

    private static Api getApi() {
        return new RestAdapter.Builder()
                .setEndpoint("https://openapi.etsy.com/v2")
                .setRequestInterceptor(getInterceptor())
                .build()
                .create(Api.class);
    }

    public static void getActiveListings(Callback<ActiveListings> callback) {
        getApi().activeListings("Images,Shop", callback);
    }
}
