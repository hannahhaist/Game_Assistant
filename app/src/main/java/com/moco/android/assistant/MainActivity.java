package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    Animation bounce;
    TextView tvChoose;
    Button btnCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animateTv();
    }

    //    called when user taps on custom button
    public void toCustomFeatures(View view){
        Intent intent = new Intent(this, FeaturesCustomActivity.class);
        startActivity(intent);
    }

    // called when user taps on the text view
    public void animateOnClick(View view){
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        tvChoose = (TextView)findViewById(R.id.tvChoose);
        tvChoose.startAnimation(bounce);
    }

    //called on start. the text view is animated once
    protected void animateTv(){
        tvChoose = (TextView)findViewById(R.id.tvChoose);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvChoose.startAnimation(bounce);
            }
        }, 2500);


    }
}