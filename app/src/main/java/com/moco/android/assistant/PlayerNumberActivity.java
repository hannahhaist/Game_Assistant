package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

    public class PlayerNumberActivity extends Activity {

        Assistant assistant = null;
        Map<String, String> playernumber = new LinkedHashMap<String,String>();
        EditText playerNumber;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.player_number);

            //get data from Intent
            Intent i = getIntent();
            assistant = (Assistant) i.getSerializableExtra("assistant");

            //views
            playerNumber = (EditText) findViewById(R.id.etPlayerNumber);
            playerNumber.setText("1");
        }

        //called when user taps on continue button. forwards to player names settings
        protected void toNextSettings(View view){
            playernumber.put("playernumber" ,playerNumber.getText().toString());
            assistant.setPlayerNumber(playernumber);
            //intent
            Intent intent = new Intent(this, PlayerNamesActivity.class);
            intent.putExtra("assistant",assistant);
            startActivity(intent);
        }
    }

