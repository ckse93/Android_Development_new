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


## How to go to differnt activity 
1. set up an Intent, gice it a parameter for self and the activity you want to jump to 
2. and call `startActivity(mIntent)` this will launch another activity and your main activity will be at onPause()
3. from ChangeCity.java, get the string from EditText, this is what will be passed back to MainActivity.java.
4. Now that you got the relavant data, make another Intent lets say, cityIntent, of course give it a parameter of self and MainActivity.
5. put that String from editText as `cityIntent.putExtra("city", string)`. now you stored that string inside an intent as a key-value pair
6. now you stored that data, dismiss ChangeCity.class by calling finish(). this will dismiss this view and garbage collector will reclaim the memory here
7. your MainActivity.class was sleeping, in the meanwhile, and now that ChangeCity.class is dismissed, if will go bac kto surface. Which means it will call onResume(). Override this and get the string value that was stored inside the intent
```java
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
```

and of course, you nedd `@override onResume()` to go back from landscape mode or from sleep
  ```@Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "onResume() called");
        Log.d("Clima", "Getting weather data..");
        getWeatherForCurrentLocation();
    }
4. 
