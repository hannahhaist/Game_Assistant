package com.moco.android.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerNamesActivity extends Activity {

    Assistant assistant = null;
    Map<String, String> playernames = new LinkedHashMap<String,String>();
    int pNumber;
    EditText[] editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);

        //get data from Intent
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");

        //create text views and edit text fields for player names
        pNumber = Integer.parseInt(assistant.getPlayerNumber().get("playernumber"));
//        Log.d("pnames",""+pNumber);
        //declaration of the textEdits array
        editTexts = new EditText[pNumber];
        //text for text views
        String[] textArray = new String[pNumber];
        for(int p = 1; p <= pNumber; p++){
            textArray[p-1] = "player "+p+" name";
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lPlayerNames);

        int elements = 0;
        for( int c = 0; c < textArray.length; c++ )
        {
            int posText = elements + 1;
            int posInput = elements + 2;

            //params
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(250,30,0,0);
            //text view
            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setText(textArray[c]);
            linearLayout.addView(textView, posText);

            //edit text
            EditText editText = new EditText(this);
            editText.setLayoutParams(params);
            linearLayout.addView(editText, posInput);

            editTexts[c] = editText;
            elements = elements + 2;
        }

    }

    //called when user taps on continue button. forwards to player names settings
    protected void startGame(View view){
        //write player names into the assistant object
        int i = 1;
        for(EditText e : editTexts) {
            if (!e.getText().toString().equals("")) {
                playernames.put(""+i, e.getText().toString());
//                Log.d("pnames",playernames.get(""+i));
            } else {
                Toast.makeText(this, "please enter a name for player "+i, Toast.LENGTH_LONG).show();
                return;
            }
            i++;
        }
        assistant.setPlayerNames(playernames);
        //intent
        Intent intent = new Intent(this, InGameActivity.class);
        intent.putExtra("assistant",assistant);
        startActivity(intent);
    }
}
