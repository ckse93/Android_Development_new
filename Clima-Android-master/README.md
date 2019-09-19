# Clima weatehr app
## Development process 
1. include library you want to use 
- you can download exisiting library to put in the lib directory (you might want to create it first within the 'app' directory), and use `implementation fileTree(dir: 'libs', include: ['*.jar'])` in *build.gradle (Module: app)*
- Or you can be a pro and use gradle to download and compile online library for you to use.
  - include the library like this : `implementation 'com.loopj.android:android-async-http:1.4.9'` and hit "Sync Now" button *AND* elephant with blue arrow button too.
