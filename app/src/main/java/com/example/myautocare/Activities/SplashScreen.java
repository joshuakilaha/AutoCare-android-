package com.example.myautocare.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myautocare.User.LoginActivity;
import com.example.myautocare.R;

public class SplashScreen extends AppCompatActivity {

    ImageView Splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Splash = findViewById(R.id.splash);

       Splash.animate().scaleX(2).scaleY(2).setDuration(5000).start();

        int secondsDelayed = 1;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, secondsDelayed * 3000);
    }
}
