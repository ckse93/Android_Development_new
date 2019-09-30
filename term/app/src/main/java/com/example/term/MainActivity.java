package com.example.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
    EditText editText;
    String mainText;

    // constants and variable for weather
    LocationManager lm;
    Location location;
    String City;
    Integer REQUEST_CODE = 3333;
    String URL = "https://community-open-weather-map.p.rapidapi.com/weather";
    String HOST = "community-open-weather-map.p.rapidapi.com";
    String KEY = "bc0e697eaemsh75204ba3d5d4ed8p1a2842jsn1ff30169521d";
    WeatherData weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("term", "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize variables
        mainTextView = findViewById(R.id.mainTextView);
        mainTextView.setMovementMethod(new ScrollingMovementMethod());

        // setting up edittext and "return" key listener
        mainText = "";
        editText = findViewById(R.id.editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int key, KeyEvent keyEvent) {
                // if the event is a key-down on the return button
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && key == KeyEvent.KEYCODE_ENTER) {
                    String command = editText.getText().toString();
                    command(command);
                    editText.setText("");
                    mainTextView.scrollTo(0,0);
                }
                return false;
            }
        });
        weather = new WeatherData();

        // getCurrentLocation();
    }
    // edittext command and parser------------------------------------------------------------------
    private void command(String str){
        if (str.contains("getWeather()")) {
            String[] lst = str.split("\\)");
            if (lst.length == 1) {  // when user provides no city info
                mainText = mainText + "\n" + str + "\n";
                getCurrentLocation();
            } else {
                mainText = mainText + "\n" + str + "\n";
                Log.d("term", "lst[1] : " + lst[1]);
                getWeather(0.0,0.0, lst[1]);
            }

        } else {
            mainText = mainText + "\n" + str + "\n" + "INVALID COMMAND. " + "\n";
            mainTextView.setText(mainText);
        }



    }
    // edittext command and parser------------------------------------------------------------------


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
            getWeather(lon,lat,"");
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
    private void getWeather(Double lon, Double lat, String str) {
        Log.d("tem", "getWeather() called");
        //  setting up the client and the required headers
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-rapidapi-host", HOST);
        client.addHeader("x-rapidapi-key", KEY);
        // setting up optinal parameters
        RequestParams params = new RequestParams();
        params.put("lon", lon);
        params.put("lat", lat);
        if (!str.equals("")){  // if user provides city name
            params.put("q", str);
        }

        client.get(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Clima","Success! JSON data : " + response.toString());
                weather.setterFromJSON(response);  // now we got the response, lets set the variables.
                mainText = mainText + "Weather Data : \n " +
                        "\tCity : " + weather.getmCity() + ", " + weather.getmCountry() + "\n" +
                        "\tCondition : " + weather.getmCondition() + "\n" +
                        "\tTempC : " + weather.getTempC();
                mainTextView.setText(mainText);
                mainTextView.scrollTo(0,0);
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
