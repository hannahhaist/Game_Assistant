package com.moco.android.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TimerSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_settings);
    }

    //called when user taps on continue button. forwards to table settings
    protected void toTableSettings(View view){
//        Intent intent = new Intent(this, TableSettingsActivity.class);
//        startActivity(intent);
    }
}
