package com.moco.android.assistant;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


public class InGameActivity extends Activity {
    public Handler gameHandler;
    Thread thread;
    TextView tvTimer;
    TextView playerName;
    TextView currentRound;
    String timerMessage;
    TimerAssistant ta;
    DiceAssistant da;
    Assistant assistant;
    Boolean diceGame =false;
    Boolean tableGame =false;
    Boolean timerGame = false;
    ArrayList<String> players;
    int roundNumber;
    int currentPlayer;

    public InGameActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");
        //get Chosen gamefeature
        setGameFeatures(assistant);
        if(tableGame){setContentView(R.layout.activity_table_in_game);}
        else{
            if(diceGame){setContentView(R.layout.activity_dice_game);}
        else
            setContentView(R.layout.activity_timer_in_game);}

        playerName = (TextView) findViewById(R.id.tvPlayerName);
        currentRound = (TextView) findViewById(R.id.tvRoundNumber);
        roundNumber = 1;
        currentPlayer = 0;
        playerName.setText(players.get(0));
        currentRound.setText("Round "+ roundNumber);

        da.createImageViews(this);

        disableUnusedButtons();
        /*
        !!!TEST FOR DICETHROW!!!!
         */

    }

    /*
    change views
     */
    public void changeToDice(View view){
        setContentView(R.layout.activity_dice_game);
        disableUnusedButtons();

    }

    public void changeToTimer(View view){
        setContentView(R.layout.activity_timer_in_game);
        disableUnusedButtons();

    }

    public void changeToTable(View view){
        setContentView(R.layout.activity_table_in_game);
        disableUnusedButtons();

    }


    public void nextPlayer(View view) {
        if(currentPlayer == players.size()-1){
            roundNumber++;
            currentRound.setText("Round " + roundNumber);
        }
        currentPlayer++;
        currentPlayer = currentPlayer% players.size();
        System.out.println(currentPlayer);
        System.out.println(players.size());
        playerName.setText(players.get(currentPlayer));

    }

    /*
    DICE FUNCTIONS
     */

    public void diceRoll(View view){
            da.setNewDiceImage();

        }


    /*
    TIMER FUNCTIONS
    Start the Timer
     */
    public void startTimer(View view){
        /*
        Initiate Timer
         */
        Button timerButton = (Button) findViewById(R.id.btStartTimer);

        //Define Handler that is attached to the UI thread
        //thread.start();
        if(ta.paused) {
            timerButton.setText("Stop Timer");

            gameHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String timerMessage = "" + msg.obj;
                    tvTimer.setText(timerMessage);
                }
            };
            ta.setGameHandler(gameHandler);
            new Thread(ta).start();
        }
        else{
            timerButton.setText("Start Timer");
            ta.pause();

        }
    }

    public void resetTimer(View view){
       ta.resetTimer();
        tvTimer.setText(ta.getTimer());
    }

    private void setGameFeatures(Assistant assistant){
        if(assistant.getDice() != null){
            diceGame = true;
            da = new DiceAssistant(assistant.getDice());
            System.out.println("DiceGame");
        }
        if(assistant.getTimer()!=null){
            timerGame = true;
            ta = new TimerAssistant(assistant.getTimer());
            System.out.println("TimerGame");
        }
        if(assistant.getTable() != null){
            tableGame = true;
            System.out.println("TableGame");
        }

        Map<String, String> mPlayers = assistant.getPlayerNames();
        Iterator it = mPlayers.entrySet().iterator();
        players = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            players.add(pair.getValue().toString());
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    private void disableUnusedButtons(){
        if(!timerGame){
            findViewById(R.id.btTimer).setVisibility(View.INVISIBLE);
        }
        else{
            tvTimer = (TextView) findViewById(R.id.tvTimer);
            tvTimer.setText(ta.getTimer());
        }
        if(!diceGame) {
            findViewById(R.id.btDice).setVisibility(View.INVISIBLE);
        }
        if(!tableGame){
            findViewById(R.id.btTable).setVisibility(View.INVISIBLE);
        }

    }






    /*Testfunktion
    Gibt einen Custom assistant zum testen zurück
    */
    public  Assistant getCustomAssistant(){
        Map<String, Map<String,String>> features = new LinkedHashMap<>();
        Map<String, String> arr = new LinkedHashMap<String, String>();
        features.put("dice", arr);
        features.put("table", arr);
        features.put("timer", arr);
        Assistant assistant = new CustomAssistant(features);
        Map<String, String> settings = assistant.getDice();
        settings.put("type", "6");
        settings.put("number", "3");
        assistant.setDice(settings);
        settings = assistant.getTable();
        settings = assistant.getTimer();
        settings.put("hour", "2");
        settings.put("minute", "1");
        settings.put("second","5");



        return assistant;




    }

}
