package com.something.myapplication.activity.splashactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.something.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {                               //Go to next page i.e, start the main activity.
                try {
                    Intent intent = new Intent(getApplicationContext(), com.something.myapplication.activity.homeactivity.MainActivity.class);
                    startActivity(intent);
                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);  // Give a 3 seconds delay.
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }         //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.

}