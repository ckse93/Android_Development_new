package com.example.dicee;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
// declare variables you will use in the code=======================================================

    Button rollButton;
    ImageView leftDieView;
    ImageView rightDieView;
    Random rand = new Random();

    int RandomNumberBetween(int a, int b) {
        int l = rand.nextInt(a) + b;
        return l;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rollButton = findViewById(R.id.button2);
        leftDieView = findViewById(R.id.image_die_left);
        rightDieView = findViewById(R.id.image_die_right);
        final int[] dicearray= {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4,
                          R.drawable.dice5, R.drawable.dice6};

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer randint = RandomNumberBetween(5,0);
                Log.i("Dicee", "button pressed with randint : " + randint);
                leftDieView.setImageResource(dicearray[randint]);
                randint=RandomNumberBetween(5,0);
                rightDieView.setImageResource(dicearray[randint]);
            }
        });
    }



}
