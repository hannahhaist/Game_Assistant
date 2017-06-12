package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import java.util.LinkedHashMap;
import java.util.Map;

public class FeaturesCustomActivity extends Activity {
    private Map<String, Map<String, String>> features = new LinkedHashMap<String, Map<String, String>>();
    Map<String, String> arr = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features_custom);
    }

    protected void onFeatureClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkDice:
                if (checked){
                    features.put("dice", arr);
                }else{
                    features.remove("dice");
                }
                break;
            case R.id.checkScore:
                if (checked){
                    features.put("table", arr);
                }else{
                    features.remove("table");
                }
                break;
            case R.id.checkTimer:
                if (checked){
                    features.put("timer", arr);
                }else{
                    features.remove("timer");
                }
                break;
        }
    }

    //called when user taps on continue button. creates an instance of CustomAssistant, forwards to next settins
    protected void toNextSettings(View view){
        if(features.size() != 0){
            Assistant assistant = new CustomAssistant(features);

            Intent intent = new Intent();
            String next = features.keySet().iterator().next();

            if(next.equals("dice")){
                intent = new Intent(this, DiceSettingsActivity.class);
            }else if(next.equals("table")){
                intent = new Intent(this, TableSettingsActivity.class);
            }else if(next.equals("timer")) {
                intent = new Intent(this, TimerSettingsActivity.class);
            }
            intent.putExtra("assistant",assistant);
            startActivity(intent);
        }else{
            Toast.makeText(this, "please choose some features..", Toast.LENGTH_LONG).show();
        }
    }
}