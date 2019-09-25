package com.example.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    // view variables
    TextView mainTextView;

    // constants and variable for weather
    LocationManager lm;
    Location location;
    String City;
    Integer REQUEST_CODE = 3333;
    String URL = "https://community-open-weather-map.p.rapidapi.com/weather";
    String APP_ID = "bc0e697eaemsh75204ba3d5d4ed8p1a2842jsn1ff30169521d";
    WeatherData weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("term", "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize variables
        mainTextView = findViewById(R.id.mainTextView);
        weather = new WeatherData();

        getCurrentLocation();
    }


    // getting current location---------------------------------------------------------------------
    private void getCurrentLocation() {
        Log.d("term", "getCurrentLocation() called");
        // 위치 관리자 객체 참조하기.
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("term", "No permission yet, requesting permission now...");
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        try {
            location = (Location) lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            String provider = location.getProvider();
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            Double alt = location.getAltitude();
            Log.d("term", "PROVIDER : " + provider + " LATITUDE : " + lat.toString() + " LONGITUDE : "+ lon.toString() + " ALTITUDE : " + alt.toString());
            getWeather(lon,lat);
        }
        catch (Exception e ) {
            Log.d("term", "caught"+e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // check if request callback contains out request code and shit
        if (requestCode == REQUEST_CODE){  // this request code has to match up.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){  // location access granted
                Log.d("term", "onRequestPermissionsResult() : PERMISSION GRANTED!");
                // after this is done, you call back to getCurrentLocation() to resume.
                getCurrentLocation();
            }
            else {
                Log.d("term", "Permission denied");
            }
        }
    }
// getting current location---------------------------------------------------------------------
// params : latitude, longitute. job : networking and get the weather data from server
    private void getWeather(Double lon, Double lat) {
        Log.d("tem", "getWeather() called");
        RequestParams params = new RequestParams();
        params.put("lon", lon);
        params.put("lat", lat);
        params.put("q", "Seoul");
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");
        client.addHeader("x-rapidapi-key", "bc0e697eaemsh75204ba3d5d4ed8p1a2842jsn1ff30169521d");
        client.get("https://community-open-weather-map.p.rapidapi.com/weather", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Clima","Success! JSON data : " + response.toString());
                weather.setterFromJSON(response);  // now we got the response, lets set the variables.
                String str = "Weather Data : \n " +
                        "\tCity : " + weather.getmCity() + "\n" +
                        "\tCondition : " + weather.getmCondition() + "\n" +
                        "\tTempC : " + weather.getTempC();
                mainTextView.setText(str);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "FAIL to get internet response " + e.toString());
                Log.d("Clima","Status Code " + statusCode);
            }
        });
    }

    private void updateUI(){

    }

}
