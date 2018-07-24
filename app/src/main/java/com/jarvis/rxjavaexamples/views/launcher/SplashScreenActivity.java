package com.jarvis.rxjavaexamples.views.launcher;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jarvis.rxjavaexamples.R;

import butterknife.ButterKnife;

/**
 * Created by Sachin
 */


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        initializeView();
    }

    private void initializeView() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        goToNextScreen();
                    }
                }, 2500);
    }

    private void goToNextScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}