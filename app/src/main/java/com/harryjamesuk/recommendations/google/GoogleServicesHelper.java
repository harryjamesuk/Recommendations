package com.harryjamesuk.recommendations.google;

import android.app.Activity;
import android.content.Intent;
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
        if (isGooglePlayServicesAvailable()) {
            apiClient.connect();
        } else {
            // Tell the listener that Google Play Services is not available.
            listener.onDisconnected();
        }
    }

    public void disconnect() {
        if (isGooglePlayServicesAvailable()) {
            apiClient.disconnect();
        }
        else {
            // Tell the listener that Google Play Services is not available.
            listener.onDisconnected();
        }
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
        // Tell the listener that Google Play Services is available.
        listener.onConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Tell the listener that Google Play Services is not available.
        listener.onDisconnected();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, REQUEST_CODE_RESOLUTION);
            } catch (android.content.IntentSender.SendIntentException e) {
                // Even if one error doesn't fix, another might so let's try connecting again.
                connect();
            }
        } else {
            // Tell the listener that Google Play Services is not available.
            listener.onDisconnected();
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_AVAILABILITY || requestCode == REQUEST_CODE_RESOLUTION) {
            if (resultCode == Activity.RESULT_OK) {
                connect();
            }
            else {
                // Tell the listener that Google Play Services is not available.
                listener.onDisconnected();
            }
        }
    }
}
