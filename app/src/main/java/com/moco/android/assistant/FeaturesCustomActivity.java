package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class FeaturesCustomActivity extends Activity {
    private HashMap<String, String[]> features = new LinkedHashMap<String, String[]>();
    String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features_custom);

        LinearLayout featuresLayout = (LinearLayout)findViewById(R.id.features_view);
        if(featuresLayout != null) {
            Toast toast = Toast.makeText(this,"nichtnull",Toast.LENGTH_LONG);
        toast.show();
            Log.d("featuresactivity", "nichtnull");
        }else{
            Log.d("featuresactivity", "null");
        }
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

    //called when user taps on continue button. forwards to dice settings
    protected void toNextSettings(View view){
        Log.d("featuresactivity", "huhu");

        CustomAssistant assistant = new CustomAssistant(features);
        Intent intent =  new Intent(this, DiceSettingsActivity.class);
        String next = features.keySet().iterator().next();
//        Toast toast = Toast.makeText(this,next,Toast.LENGTH_LONG);
//        toast.show();
        if(next.equals("dice")){
            intent = new Intent(this, DiceSettingsActivity.class);
        }else if(next.equals("table")){
            intent = new Intent(this, TableSettingsActivity.class);
        }else if(next.equals("timer")){
            intent = new Intent(this, TimerSettingsActivity.class);
        }else{
            Toast.makeText(this, "please choose some features..", Toast.LENGTH_LONG).show();
        }
        intent.putExtra("assistant",assistant);
        startActivity(intent);
    }
}