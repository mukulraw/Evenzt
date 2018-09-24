package com.evenzt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        overridePendingTransition(0 , R.anim.fade);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent(getBaseContext() , LoginActivity.class);

                //i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(i);

                finish();

            }
        } , 1500);


    }

    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(0 , R.anim.fade);


    }
}
