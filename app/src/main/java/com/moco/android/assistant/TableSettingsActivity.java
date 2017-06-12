package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TableSettingsActivity extends Activity {

    Assistant assistant = null;
    RadioGroup useNames;
    EditText colsNumber;
    EditText rowsNumber;
    CheckBox lblCols;
    CheckBox lblRows;
    Map<String, String> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_settings);
        //views
        useNames = (RadioGroup)findViewById(R.id.rgColumns);
        colsNumber = (EditText)findViewById(R.id.etColumnsNumber);
        rowsNumber = (EditText)findViewById(R.id.etRowsNumber);;
        lblCols = (CheckBox)findViewById(R.id.cbColumnsLabel);
        lblRows = (CheckBox)findViewById(R.id.cbRowsLabel);
        //get data from Intent
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");
        settings = assistant.getTable();
        //disable "number of plaers editText"
        colsNumber.setEnabled(false);
        colsNumber.setInputType(InputType.TYPE_NULL);
        colsNumber.setFocusable(false);

        //radiobuttons listener
        useNames.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbtnColumnsPlayer:
                        // do operations specific to this selection
                        break;
                    case R.id.rbtnColumnsNumber:
                        //enable "number of plaers editText"
                        colsNumber.setEnabled(true);
                        colsNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                        colsNumber.setFocusable(true);
                        break;
                }
            }
        });
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
                    //TODO:to player settings..
                }
                //get settings from views and write into assistant
//                String type = sprType.getSelectedItem().toString();
//                String number = sprNumber.getSelectedItem().toString();
                Map<String, String> settings = assistant.getTable();
//                settings.put("type", type);
//                settings.put("number", number);
                assistant.setTable(settings);
                //intent
                intent.putExtra("assistant",assistant);
                startActivity(intent);
            }
        }
    }
}
