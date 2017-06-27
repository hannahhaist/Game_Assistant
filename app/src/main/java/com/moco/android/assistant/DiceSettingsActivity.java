package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DiceSettingsActivity extends Activity {

    Assistant assistant = null;
    //spinner arrays
    private List<String> types = new ArrayList<>(Arrays.asList("4", "6", "8", "10", "12", "20"));
    private List<String> number =  new ArrayList<>(Arrays.asList("1", "2", "3"));
    Spinner sprType;
    Spinner sprNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_settings);
        //initialize spinner
        sprType = (Spinner)findViewById(R.id.sprDiceType);
        sprNumber = (Spinner)findViewById(R.id.sprDiceNumber);
        fillSpinner(types, sprType);
        fillSpinner(number, sprNumber);
        //get data from Intent
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");
    }

    private void fillSpinner(List<String> spinnerArray, Spinner spr){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        spr.setAdapter(adapter);
    }

    //called when user taps on continue button. writes dice settings into the hashmap, forwards to next settins (if there are other features)
    protected void toNextSettings(View view){
        Map<String, Map<String, String>> features = assistant.getFeatures();


        Iterator<Map.Entry<String, Map<String, String>>> it = features.entrySet().iterator();
        String next = "";
        Intent intent = new Intent();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, String>> f = (Map.Entry<String, Map<String, String>>) it.next();
            if (f.getKey().equals("dice")) {
                if (it.hasNext()) {
                    Map.Entry<String, Map<String, String>> n = it.next();
                    next = n.getKey();
//                    Log.i("dicesettingsact",next);
                    if(next.equals("table")){
                        intent = new Intent(this, TableSettingsActivity.class);
                    }else if(next.equals("timer")) {
                        intent = new Intent(this, TimerSettingsActivity.class);
                    }
                }else{
                    intent = new Intent(this, PlayerNumberActivity.class);
                }
            }
        }
        //get settings from spinners and write into assistant
        String type = sprType.getSelectedItem().toString();
        String number = sprNumber.getSelectedItem().toString();
        Map<String, String> settings = assistant.getDice();
        settings.put("type", type);
        settings.put("number", number);
        assistant.setDice(settings);
        //intent
        intent.putExtra("assistant",assistant);
        startActivity(intent);
    }
}
