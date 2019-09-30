package com.example.term;

import android.util.Log;

import org.json.JSONObject;

public class WeatherData {
    private String mCity;
    private Double mTempRaw;
    private String mCondition;
    private String mCountry;

    public void setterFromJSON (JSONObject json){
        try {
            mCity = json.getString("name");
            mTempRaw = json.getJSONObject("main").getDouble("temp");
            mCondition = conditionInterpreter(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
            mCountry = json.getJSONObject("sys").getString("country");
        }
        catch (Exception e) {
            Log.d("term", "JSON parsing error : " + e.toString());
        }
    }

    private String conditionInterpreter(int condition) {
        if (condition >= 0 && condition < 300) {
            return "Storm";
        } else if (condition >= 300 && condition < 500) {
            return "Light Rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "Jon SNOH";
        } else if (condition >= 701 && condition <= 771) {
            return "Fog";
        } else if (condition >= 772 && condition < 800) {
            return "Storm but more";
        } else if (condition == 800) {
            return "Sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "Cloudy";
        } else if (condition >= 900 && condition <= 902) {
            return "Storm";
        } else if (condition == 903) {
            return "SHOH";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "Strpm";
        } else return "nuuuuuulllll";
    }

    public int getTempC() {
        return (int)Math.round(mTempRaw - 273.15);
    }

    public int getTempF() {
        return (int)Math.round(((mTempRaw-273.15)*1.8)+32);
    }

    public String getmCity() {
        return mCity;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmCondition() {
        return mCondition;
    }
}
