package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

public class TableSettingsActivity extends Activity {

    Assistant assistant = null;
    RadioGroup useNames;
    EditText colsNumber;
    EditText rowsNumber;
    CheckBox lblCols;
    CheckBox lblRows;
    Map<String, String> settings;
    String playerNames = "1"; // use player names = 1(default); custom = 0
    String labelCols = "0";
    String labelRows = "0";
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

        //disable "number of players editText"
        colsNumber.setEnabled(false);
        colsNumber.setInputType(InputType.TYPE_NULL);
        colsNumber.setFocusable(false);

        //get data from Intent
            Intent i = getIntent();
            assistant = (Assistant) i.getSerializableExtra("assistant");
            settings = assistant.getTable();

        // check the first radiobutton
        useNames.check(R.id.rbtnColumnsPlayer);
        //radiobuttons listener
        useNames.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbtnColumnsPlayer:
                        //disable "number of players editText"
                        colsNumber.setEnabled(false);
                        colsNumber.setInputType(InputType.TYPE_NULL);
                        colsNumber.setFocusable(false);
                        //use player names as columns labels
                        playerNames = "1";
                         break;
                    case R.id.rbtnColumnsNumber:
                        //enable "number of players editText"
                        colsNumber.setEnabled(true);
                        colsNumber.setFocusableInTouchMode(true);
                        colsNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                        colsNumber.setFocusable(true);
                        //don't use player names as columns labels
                        playerNames = "0";
                        break;
                }
            }
        });
    }

    //checkbox listener(label columns, label rows)
    protected void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cbColumnsLabel:
                if (checked){
                    labelCols = "1";
                }else{
                    labelCols = "0";
                }
                break;
            case R.id.cbRowsLabel:
                if (checked){
                    labelRows = "1";
                }else{
                    labelRows = "0";
                }
                break;
        }
    }

    //called when user taps on continue button. writes settings into the hashmap, forwards to next settins (if there are other features)
    protected void toNextSettings(View view){
        Map<String, Map<String, String>> features = assistant.getFeatures();

        Iterator<Map.Entry<String, Map<String, String>>> it = features.entrySet().iterator();
        String next = "";
        Intent intent = new Intent();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, String>> f = it.next();//(Map.Entry<String, Map<String, String>>) it.next();
            if (f.getKey().equals("table")) {
                if (it.hasNext()) {
                    Map.Entry<String, Map<String, String>> n = it.next();
                    next = n.getKey();
                    if(next.equals("timer")){
                        intent = new Intent(this, TimerSettingsActivity.class);
                    }
                }else{
                    //TODO:to player settings..
                    Log.d("tSettings","no more features");
                }
            }
        }
        //TODO: check cols and rows number (number??)
        //get settings from views and write into assistant
        Map<String, String> settings = assistant.getTable();
        settings.put("labelRows", labelRows);
        Log.d("tSettings","labelrows: "+labelRows);

        settings.put("labelCols", labelCols);
        Log.d("tSettings","labelCols: " + labelCols);

        settings.put("usePlayerNames",playerNames);
        Log.d("tSettings","usePlayerNames: " + playerNames);
        Log.d("tSettings", "colsNumber" + colsNumber.getText().toString());

        if(playerNames.equals("0")){
            if(!colsNumber.getText().toString().equals("")){
                settings.put("columnsNumber", colsNumber.getText().toString());
            }
        }
        if(!rowsNumber.getText().toString().equals("")){
            settings.put("rowsNumber", rowsNumber.getText().toString());
            Toast.makeText(this, "please enter number of rows", Toast.LENGTH_LONG).show();
        }

        //////
        String rowsNumber = settings.get("rowsNumber");
        Log.d("tSettings",rowsNumber);
//TODO: check if cols and rows number is given.
        ////
//        if(settings.containsKey("rowsNumber") && settings.containsKey("columnsNumber")){
//            assistant.setTable(settings);
//            //intent
//            intent.putExtra("assistant",assistant);
//            startActivity(intent);
//        }else{
//            Toast.makeText(this, "please enter number of rows and columns", Toast.LENGTH_LONG).show();
//        }

    }
}
