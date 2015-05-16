package com.harryjamesuk.recommendations.google;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Harry on 16/05/2015.
 */
public class GoogleServicesHelper implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_RESOLUTION = -100;
    private static final int REQUEST_CODE_AVAILABILITY = -101;

    public interface GoogleServicesListener {
        public void onConnected();
        public void onDisconnected();
    }

    private Activity activity;
    private GoogleServicesListener listener;
    private GoogleApiClient apiClient;

    public GoogleServicesHelper(Activity activity, GoogleServicesListener listener) {
        this.activity = activity;
        this.listener = listener;

        this.apiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void connect() {
        apiClient.connect();
    }

    public void disconnect() {
        apiClient.disconnect();
    }

    private boolean isGooglePlayServicesAvailable() {
        int availability = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        switch (availability) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_INVALID:
                GooglePlayServicesUtil.getErrorDialog(availability, activity, REQUEST_CODE_AVAILABILITY)
                        .show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
