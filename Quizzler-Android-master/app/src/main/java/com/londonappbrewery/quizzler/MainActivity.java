package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:
    Button trueButton;
    Button falseButton;
    TextView textview;
    TextView scoreview;
    ProgressBar progressBar;
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
        scoreview = findViewById(R.id.score);
        progressBar = findViewById(R.id.progress_bar);

        if (savedInstanceState != null){  //  if there is any saved data, then load saved instance data.
            // since updateUI() uses textview variable, you need to launch this after you define those variables.
            score = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            Log.i("score : " + score,"mIndex : " + mIndex);
            updateUI();
        } else {
            score = 0;
            mIndex = 0;
        }
        updateUI();

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "True Button Clicked");
                // Toast.makeText(getApplicationContext(), "True pressed", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(getApplicationContext(), "false pressed", Toast.LENGTH_SHORT).show();
                try {
                    checkAnswer(false);
                    updateUI();
                }catch (Exception e) {
                    Log.i("","end of the question. performing reset");
                    reset();
                }
            }
        });
    }

    private void reset() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this); // this refers to MainActivity
        alert.setTitle("Game Over");
        alert.setCancelable(true); // alert will be cancelled if user taps outside of the alert window
        alert.setMessage("Your Score : " + score + " Out of " + mQuestionBank.length);
        alert.setPositiveButton("Exit application", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();  // yeah you need to show it to the user.
        score=0;
        mIndex=0;
        updateUI();
        progressBar.setProgress(100/mQuestionBank.length);
    }

    private boolean checkAnswer(boolean input){
        if (input == mQuestionBank[mIndex].ismAnswer()){
            score++;
            mIndex++;
            Toast.makeText(getApplicationContext(), "KORREKT", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            mIndex++;
            Toast.makeText(getApplicationContext(), "YINKORREKT", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void updateUI() {
        textview.setText(mQuestionBank[mIndex].getmQuestionID());
        scoreview.setText("Score " + score + "/" + mQuestionBank.length);
        progressBar.incrementProgressBy(100/mQuestionBank.length);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {  // this is where you save your state before putting your app in background or screen rotate.
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", score);
        outState.putInt("IndexKey", mIndex);
    }
}
