package com.londonappbrewery.climapm;

import android.util.Log;

import org.json.JSONObject;

public class WeatherDataModel {

    private String mTemoerature;
    private int mCondition;
    private String mCity;
    private String mIconName;

    // TODO: Create a WeatherDataModel from a JSON:
public static WeatherDataModel fromJson(JSONObject json){
    try {
        WeatherDataModel weatherData = new WeatherDataModel();
        weatherData.mCity = json.getString("name");
        weatherData.mCondition = json.getJSONArray("weather").getJSONObject(0).getInt("id");
        weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
        Double tempResult = json.getJSONObject("main").getDouble("temp") - 273.15;  // getting the raw data, and converting to celcius
        weatherData.mTemoerature = String.valueOf((int)Math.round(tempResult));

        return weatherData;
    }
    catch (Exception e) {
        e.printStackTrace();
        Log.d("WeatherDataModel", "json parsing error" + e.toString());
        return null;
    }
}

    // TODO: Uncomment to this to get the weather image name from the condition:
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    // TODO: Create getter methods for temperature, city, and icon name:


    public String getmTemoerature() {
        return mTemoerature;
    }

    public int getmCondition() {
        return mCondition;
    }


    public String getmCity() {
        return mCity;
    }

    public String getmIconName() {
        return mIconName;
    }
}
