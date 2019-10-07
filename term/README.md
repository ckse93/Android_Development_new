# Command based weather and bitcoin info getter 

## how to use
this is a terminal-looking api based application. In English, user types in command, and the app will get the info from servers.
Currently supported api calls 
**getWeather()**
```
getWeather()
    -> will get weather based on your current location
getWeather() seoul
    -> will get weather in Seoul, Korea
 ```
 
 **clear**
 ```
 clear
 -> will clear out the terminal 
 ```
 
 to be added : 
 **bitCoin()** : will get current bicoin price. with no parameter, will return bitcoin value in USD. paramters will include USD, CAD, EU, JPY, KRW and so on

## build note 
**1. initial setup**
- set up the permission and import the library for you to use. You need interet,location permission and json library and async library.
  - permission is done in `AndroidManifest.xml` and importing library is done in `build.gradle Module:app`. remember to sync 
  
**2. getting location**
- you neew `LocationManager` and `Location` variable. 
  - init the `LocationManager` in `getCurrentLocation()` and init `Location` as well. Permission check dialogue will let you check permission.
    - inside permission check, feed it with `ActivityCompat.requestPermissions (MainActivity.this, new String manifest's location permission, Request code)`. After this, you should @Override `onRequestPermissionResult()`. If you get the permission, call back the `getCurrentLocation()` so your function call doesnt end up dead. 
    
**3. getting weather info**
- make WeatherData class and set it up as you want. You should make a setter with JSON as a input param. 
- use `AsyncHttpClient` as the API handler. this is fast and light. only 90kbs of overhead. YEET
- feed your client variable with header (if you need it, RapidAPI requires you to addHeader, but OpenWeatherMap doesnt. chekc with your API provider)
  - RapidAPI needs `addHeader()` to add headers to client. OpenWeatherMap accepts Params.put(key, value). test out some stuff. play with it

## tips that i used 
- scrollable textview 
  - add "gravity = "bottom"" perperty in the textview xml attribute
  - `setMovementMethod(new ScrollingMovementMethod())`
- hitting 'return' key to trigger something
  - set the onKeyListener for the editText like : 
    - `editText.setOnKeyListener(new View.OnKeyListener)`
    - this will override onKey method. One of the parameter, `int i` is the keycode, just change i info keycode or smth 
    - and within the onKey scope, do whatever you want to do if (key == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
## todo
- "clear" command implementation.
  - easily done with just setting `mainText = ""`. 
- housekeeping 
  - maybe it can be more modulized.
- using more than 1 viewController? 
  - not sure. this is a terminal looking app. there isn't really a need for second view controller
- alias 
  - need storage or setting permission so i can load up configurations on `onResume()`
