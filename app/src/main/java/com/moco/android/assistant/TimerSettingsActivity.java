package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Map;

public class TimerSettingsActivity extends Activity {
    Assistant assistant = null;
    Map<String, String> settings;
    EditText etHours;
    EditText etMinutes;
    EditText etSeconds;
    RadioGroup rgTimer;
    String timerNumber = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_settings);
        //Views
        etHours = (EditText) findViewById(R.id.etHours);
        etMinutes = (EditText) findViewById(R.id.etMinutes);
        etSeconds = (EditText) findViewById(R.id.etSeconds);
        rgTimer = (RadioGroup) findViewById(R.id.rgTimer);
        //set default values
        etHours.setText("0");
        etMinutes.setText("0");
        etSeconds.setText("0");
        //edit text filter
        etHours.setFilters(new InputFilter[]{ new EditTextNumberFilter("0", "10")});
        etMinutes.setFilters(new InputFilter[]{ new EditTextNumberFilter("0", "59")});
        etSeconds.setFilters(new InputFilter[]{ new EditTextNumberFilter("0", "59")});
        //get data from Intent
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");
        settings = assistant.getTimer();

        // check the first radiobutton
        rgTimer.check(R.id.rbtnTimerOne);
        //radiobuttons listener
        rgTimer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbtnTimerOne:
                        //use one timer
                        timerNumber = "1";
                        break;
                    case R.id.rbtnTimerEach:
                        //use timer for each player
                        timerNumber = "each";
                        break;
                }
            }
        });
    }

    //called when user taps on continue button. forwards to player number settings
    protected void toNextSettings(View view){

        //write editText values into settings map
        //hours
        if(!etHours.getText().toString().equals("")){
            settings.put("hours", etHours.getText().toString());
        }else{
            Toast.makeText(this, "please enter hours number", Toast.LENGTH_LONG).show();
            return;
        }
        //minutes
        if(!etMinutes.getText().toString().equals("")){
            settings.put("minutes", etMinutes.getText().toString());
        }else{
            Toast.makeText(this, "please enter minutes number", Toast.LENGTH_LONG).show();
            return;
        }
        //seconds
        if(!etSeconds.getText().toString().equals("")){
            settings.put("seconds", etSeconds.getText().toString());
        }else{
            Toast.makeText(this, "please enter seconds number", Toast.LENGTH_LONG).show();
            return;
        }

        //timer number
//        settings.put("timerNumber", timerNumber);

        assistant.setTimer(settings);

        //intent
        Intent intent = new Intent(this, PlayerNumberActivity.class);
        intent.putExtra("assistant",assistant);
        startActivity(intent);
    }
}
