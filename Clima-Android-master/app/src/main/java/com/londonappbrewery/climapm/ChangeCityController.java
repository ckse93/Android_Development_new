package com.londonappbrewery.climapm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeCityController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_city_layout);  // right now, this java doesnt know what layout xml to use. this will specify the xml for it.
        final EditText editText = findViewById(R.id.queryET);
        ImageButton backButton = findViewById(R.id.backButton);

        // set onClickListener for backButton.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // once finish() is called, this view will go away and garbage collector will clean this up.
            }
        });

        // this is where the edittext will send back the information to the other view
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String newCity = editText.getText().toString();
                Intent mCityIntent = new Intent(ChangeCityController.this, WeatherController.class); // this will navigate back to weatgher
                mCityIntent.putExtra("City", newCity);
                startActivity(mCityIntent);  // as soon as this is called, we will go back to main page
                return false;
            }
        });
    }
}
