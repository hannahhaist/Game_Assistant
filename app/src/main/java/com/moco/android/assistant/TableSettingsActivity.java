package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TableSettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_settings);
    }

    //called when user taps on continue button. forwards to timer settings
    protected void toTableSettings(View view){
        Intent intent = new Intent(this, TimerSettingsActivity.class);
        startActivity(intent);
    }
}
