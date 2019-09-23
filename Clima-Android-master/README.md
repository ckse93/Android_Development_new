# Clima weather app
## Development process 
1. include library you want to use
  - you can download exisiting library to put in the lib directory (you might want to create it first within the 'app' directory), and use `implementation fileTree(dir: 'libs', include: ['*.jar'])` in **build.gradle (Module: app)**
  - Or you can be a pro and use gradle to download and compile online library for you to use.
    - include the library like this : `implementation 'com.loopj.android:android-async-http:1.4.9'` and hit "Sync Now" button *AND* elephant with blue arrow button too.
2. set up the permission 
you need location access, you need permission. You can set tup permission at **AndroidManifest.xml** file.
  - type `<<uses-permission android:name="android.permission.INTERNET"></uses-permission>` for inteternet access 
  - type `<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>` for COARSE location access.
3. setup member variable
  - you need LOCATION_PROVIDER set as LocationManager.NETWORK_PROVIDER so you can get location from cell tower or wifi
  - declare `LocationManager` and `LocationListener ` type variables
4. Write getWeatherForCurrentLocation() method
  - 


and of course, you nedd `@override onResume()` to go back from landscape mode or from sleep
  ```@Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "onResume() called");
        Log.d("Clima", "Getting weather data..");
        getWeatherForCurrentLocation();
    }
4. 
