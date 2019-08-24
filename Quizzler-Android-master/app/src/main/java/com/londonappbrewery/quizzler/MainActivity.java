package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:
    Button trueButton;
    Button falseButton;
    TextView textview;
    int mIndex;
    int score;

//     TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        textview = findViewById(R.id.question_text_view);


        updateUI();
        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "True Button Clicked");
                Toast.makeText(getApplicationContext(), "True pressed", Toast.LENGTH_SHORT).show();
                try {
                    checkAnswer(true);
                    updateUI();
                }catch (Exception e) {
                    Log.i("","end of the question. performing reset");
                    reset();
                }
            }
        };
        trueButton.setOnClickListener(myListener);

        // true button uses predefined myListener, and false button uses closure syntax.

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "False Button Clicked");
                Toast.makeText(getApplicationContext(), "false pressed", Toast.LENGTH_SHORT).show();
                try {
                    checkAnswer(false);
                    updateUI();
                }catch (Exception e) {
                    Log.i("","end of the question. performing reset");
                    reset();
                }
            }
        });

        TrueFalse exampleQuestion = new TrueFalse(R.string.question_1, true);
    }

    private void reset() {
        score=0;
        mIndex=0;
        updateUI();
    }

    private boolean checkAnswer(boolean input){
        if (input == mQuestionBank[mIndex].ismAnswer()){
            score++;
            mIndex++;
            return true;
        }
        else {
            mIndex++;
            return false;
        }
    }

    private void updateUI() {
        textview.setText(mQuestionBank[mIndex].getmQuestionID());
    }
}
