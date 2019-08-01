package com.londonappbrewery.xylophonepm;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Helpful Constants
    private final int NR_OF_SIMULTANEOUS_SOUNDS = 7;
    private final float LEFT_VOLUME = 1.0f;
    private final float RIGHT_VOLUME = 1.0f;
    private final int NO_LOOP = 0;
    private final int PRIORITY = 0;
    private final float NORMAL_PLAY_RATE = 1.0f;

    // TODO: Add member variables here
    private SoundPool msoundpool;

    private int mCSoundId;
    private int mDSoundId;
    private int mESoundId;
    private int mFSoundId;
    private int mGSoundId;
    private int mASoundId;
    private int mBSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Create a new SoundPool
        msoundpool = new SoundPool(NR_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 0 );


        // TODO: Load and get the IDs to identify the sounds
        mCSoundId = msoundpool.load(getApplicationContext(),R.raw.note1_c,1);
        mDSoundId = msoundpool.load(getApplicationContext(),R.raw.note2_d,1);
        mESoundId = msoundpool.load(getApplicationContext(),R.raw.note3_e,1);
        mFSoundId = msoundpool.load(getApplicationContext(),R.raw.note4_f,1);
        mGSoundId = msoundpool.load(getApplicationContext(),R.raw.note5_g,1);
        mASoundId = msoundpool.load(getApplicationContext(),R.raw.note6_a,1);
        mBSoundId = msoundpool.load(getApplicationContext(),R.raw.note7_b,1);

    }

    // TODO: Add the play methods triggered by the buttons
    void onClick(View v) {
        Integer id = v.getId();
        //Log.i("id : ", id.toString());
        switch (id) {
            case R.id.c_key : {
                Log.println(Log.INFO,"Key pressed :","C");
                msoundpool.play(mCSoundId,1,1,1,0,1);
                break;
            }
            case R.id.d_key : {
                Log.println(Log.INFO, "Key pressed :", "D");
                msoundpool.play(mCSoundId, 1, 1, 1, 0, 1);
                break;
            }
            case R.id.e_key : {
                Log.println(Log.INFO,"Key pressed :","E");
                msoundpool.play(mESoundId,1,1,1,0,1);
                break;
            }
            case R.id.f_key : {
                Log.println(Log.INFO,"Key pressed :","F");
                msoundpool.play(mFSoundId,1,1,1,0,1);
                break;
            }
            case R.id.g_key : {
                Log.println(Log.INFO,"Key pressed :","G");
                msoundpool.play(mGSoundId,1,1,1,0,1);
                break;
            }
            case R.id.a_key : {
                Log.println(Log.INFO,"Key pressed :","A");
                msoundpool.play(mASoundId,1,1,1,0,1);
                break;
            }
            case R.id.b_key : {
                Log.println(Log.INFO,"Key pr essed :","B");
                msoundpool.play(mBSoundId,1,1,1,0,1);
                break;
            }
            default :
                break;
        }

    }

}
