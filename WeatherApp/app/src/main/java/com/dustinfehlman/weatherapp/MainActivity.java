package com.dustinfehlman.weatherapp;

        import android.content.Context;
        import android.location.Location;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
        import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
        import com.google.android.gms.location.LocationServices;
        import com.koushikdutta.ion.Ion;




        import java.io.IOException;
        import java.util.ArrayList;

        import static resources.weatherService.getWeather;



public class MainActivity extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "basic-location-sample";

    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected double mLatitudeText;
    protected double mLongitudeText;
    private grabWeatherAPI _grabWeatherAPI;
    private static Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();


        buildGoogleApiClient();



    }
    public static Context getAppContext() {
        return MainActivity.context;
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = mLastLocation.getLatitude();
            mLongitudeText = mLastLocation.getLongitude();
        }
        _grabWeatherAPI = new grabWeatherAPI();
        _grabWeatherAPI.execute();


    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public class grabWeatherAPI extends AsyncTask<ArrayList<String>, ArrayList<String>, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {

            ArrayList<String> weather = null;

            try {
                weather = getWeather(mLatitudeText, mLongitudeText, MainActivity.getAppContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            //Displays city temp in fahrenheit
            TextView cityTemp = (TextView) findViewById(R.id.temp);
            cityTemp.setText(result.get(0));
            //Displays weather + city name
            TextView cityName = (TextView) findViewById(R.id.cityName);
            cityName.setText(result.get(4) + " in " + result.get(1));
            //Displays last time weather data was refreshed
            TextView lastUpdated = (TextView) findViewById(R.id.lastUpdated);
            lastUpdated.setText(result.get(2));

            String url = result.get(3);
            ImageView iv = (ImageView) findViewById(R.id.weatherPic);
            Ion.with(iv).load(url);










        }
    }

}