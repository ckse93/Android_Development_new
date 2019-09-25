# Command based weather and bitcoin info getter 

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
