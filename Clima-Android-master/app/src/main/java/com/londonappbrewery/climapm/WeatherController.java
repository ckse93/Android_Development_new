package com.londonappbrewery.climapm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "e72ca729af228beabd5d20e3b7749713";  // App ID to use OpenWeather data
    final long MIN_TIME = 5000;  // Time between location updates (5000 milliseconds or 5 seconds)
    final float MIN_DISTANCE = 1000;  // Distance between location updates (1000m or 1km)
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;  // will request location from cell tower or wifi


    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;
    LocationManager mLocationManager;
    LocationListener mLocationListener;  // component that will be notified when location is changed.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);

        // set an OnClickListener to the changeCityButton
        // navigation between view are done by Intent feature.
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(WeatherController.this, ChangeCityController.class);  // first param is context, second is where this would send user to
                startActivity(mIntent);  // now this function will take you to a different view
            }
        });
        Log.d("Clima", "calling getWeatherForCurrentLocation()");
        getWeatherForCurrentLocation();
    }

    @Override
    protected void onResume() {
        // remember, this gets called when you resume from home or switching apps
        super.onResume();
        Log.d("Clima", "onResume() called");
        Log.d("Clima", "Getting weather data..");

        Intent mIntent = getIntent();
        String city = mIntent.getStringExtra("City");  // THIS IS WHERE YOU GET THE STRING BACK FROM OTHER SCREEN.

        if (city != null) {
            getWeatherForNewCity(city);
        } else {
            try{
                Log.d("Clima", "getting data for current location");
                getWeatherForCurrentLocation();
            }
            catch (Exception e) {
                Log.d("Clima", "ERROR getting weather data: " + e.toString());
            }
        }
    }


    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        letsDoSomeNetworking(params);
    }


    private void getWeatherForCurrentLocation() {
        Log.d("Clima", "getWeatherForCurrentLocation() called");
        mLocationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);  // assigning mLocationManager to be able to get location.
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Clima", "onLocationChanged() called");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());
                Log.d("Clima", "Longitude = " + longitude + " Latitude = " + latitude);
                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                letsDoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Clima", "onProviderDisabled called");
            }
        };

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // this is the popup message asking for the permission.
            // 1. request permission
            Log.d("Clima", "requesting permission");
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

            // 2. act on user's response (y/n)
        }

        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);  // now this will notify mLocationListener of location data
    }

    @Override  // this chckes if the user gave us the permission to use the location data
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // check if request callback contains out request code and shit
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){  // location access granted
                Log.d("Clima", "onRequestPermissionsResult() : PERMISSION GRANTED!");
                getWeatherForCurrentLocation();  // now we got the permission, we cal get the weather data
            }
            else {
                Log.d("Clima", "Permission denied");
            }
        }

    }


    private void letsDoSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Clima","Success! JSON data : " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);  // use this convention instead of new WeatherDataModel() whatnot.
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "FAIL to get internet response " + e.toString());
                Log.d("Clima","Status Code " + statusCode);
                Toast.makeText(WeatherController.this, "request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateUI(WeatherDataModel weather){
        mCityLabel.setText(weather.getmCity());
        mTemperatureLabel.setText(weather.getmTemoerature());
        int resourceID = getResources().getIdentifier(weather.getmIconName(),"drawable", getPackageName()); // this will look for resource from "drawable folder"
        mWeatherImage.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {  // this is where you free up resource.
        super.onPause();
        if(mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
