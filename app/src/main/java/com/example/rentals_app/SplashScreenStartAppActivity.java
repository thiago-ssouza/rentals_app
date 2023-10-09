package com.example.rentals_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenStartAppActivity extends AppCompatActivity {

    // Duration of the splash
    public static final int SPLASH_DUR = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_start_app);

        //Create a handler that will handle all the going to the other screen.
        // I need to say from where and to where I am going
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenStartAppActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DUR);
    }
}