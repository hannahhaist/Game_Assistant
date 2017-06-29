package com.moco.android.assistant;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    TableAssistant tableAssistant;
    Assistant assistant;
    Boolean diceGame;
    Boolean tableGame;
    Boolean timerGame;
    ArrayList<String> players;
    int roundNumber;
    int currentPlayer;
    ArrayList<Integer> currentDiceThrow;
    boolean imageAdded = false;

    Button timerButton;
    //progressbar for timer
    ProgressBar progressBar;
    CountDownTimer pbTimer;
    Long millisLeft = null;
    boolean reset;

    public InGameActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        assistant = (Assistant) i.getSerializableExtra("assistant");
        //get Chosen gamefeature
        setGameFeatures(assistant);
        if(tableGame){setContentView(R.layout.activity_table_in_game);
        tableAssistant.drawTable(this);}
        else{
            if(diceGame){
                setContentView(R.layout.activity_dice_game);
                if(!imageAdded) {
                    da.addImageViews(this);
                    imageAdded = true;
                }
            } else {
                setContentView(R.layout.activity_timer_in_game);
                progressBar = (ProgressBar) findViewById(R.id.cpbTimer);
                progressBar.setProgress(Math.round(ta.getMillis()));
            }
        }


        disableUnusedButtons();
        roundNumber = 1;
        currentPlayer = 0;
        setPlayerAndRound();



    }

    /*
    change views
     */
    public void changeToDice(View view){
        setContentView(R.layout.activity_dice_game);
        disableUnusedButtons();
        setPlayerAndRound();
        da.createImageViews(this);
        da.addImageViews(this);
        if(currentDiceThrow!=null){da.setNewDiceImage(currentDiceThrow);}


    }

    public void changeToTimer(View view){
        setContentView(R.layout.activity_timer_in_game);
        disableUnusedButtons();
        setPlayerAndRound();

    }

    public void changeToTable(View view){
        setContentView(R.layout.activity_table_in_game);
        disableUnusedButtons();
        setPlayerAndRound();
        tableAssistant.drawTable(this);

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

    private void setPlayerAndRound(){

        playerName = (TextView) findViewById(R.id.tvPlayerName);
        currentRound = (TextView) findViewById(R.id.tvRoundNumber);
        playerName.setText(players.get(currentPlayer));
        currentRound.setText("Round "+ roundNumber);
        System.out.println("TEST");
    }

    /*
    DICE FUNCTIONS
     */

    public void diceRoll(View view){
            currentDiceThrow = da.diceThrow();
            da.setNewDiceImage(currentDiceThrow);
            findViewById(R.id.saveToTable).setVisibility(View.VISIBLE);

        }
    public void saveToTable(View view){
        tableAssistant.saveScore(currentDiceThrow,currentPlayer,roundNumber);
        Toast.makeText(this, "Score is saved to Table", Toast.LENGTH_LONG).show();
        findViewById(R.id.saveToTable).setVisibility(View.INVISIBLE);
    }


    /*
    TIMER FUNCTIONS
    Start the Timer
     */
    public void startTimer(View view){
        /*
        Initiate Timer
         */
        timerButton = (Button) findViewById(R.id.btStartTimer);
        //Define Handler that is attached to the UI thread
        //thread.start();
        if(ta.paused) {
            timerButton.setText("Stop Timer");

            gameHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    timerMessage = "" + msg.obj;
                    tvTimer.setText(timerMessage);
                }
            };
            ta.setGameHandler(gameHandler);
            new Thread(ta).start();
            if(millisLeft != null){
                startProgressBar(millisLeft);
//                Log.d("timer",""+millisLeft);
            }else {
                startProgressBar(ta.getMillis());
            }
        }
        else{
            timerButton.setText("Start Timer");
            ta.pause();
            pbTimer.cancel();
        }
    }

    private void startProgressBar(long millis){

        if(reset == true){
            millis = ta.getMillis();
            reset = false;
        }
        if(millis == ta.getMillis()) {
            progressBar.setMax(Math.round(millis / 50));
        }

        progressBar.setVisibility(View.VISIBLE);
        pbTimer = new CountDownTimer(millis, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                //update progress every 100 milliseconds
                progressBar.setProgress(Math.round(millisUntilFinished / 50));
            }
            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    public void resetTimer(View view){
        ta.resetTimer();
        tvTimer.setText(ta.getTimer());
        timerButton.setText("Start Timer");
        //progressbar
        pbTimer.cancel();
        progressBar.setProgress(Math.round(ta.getMillis()));
        progressBar.setVisibility(View.VISIBLE);
        reset = true;
    }

    private void setGameFeatures(Assistant assistant){
       /*
       Get the Playernames
        */
        Map<String, String> mPlayers = assistant.getPlayerNames();
        Iterator it = mPlayers.entrySet().iterator();
        players = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            players.add(pair.getValue().toString());
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
        /*
        find the chosen gamefeatures
         */
        it = assistant.getFeatures().entrySet().iterator();
        diceGame=false;
        tableGame=false;
        timerGame=false;

        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            switch (pair.getKey().toString()){
                case "table":
                    tableGame=true;
                    tableAssistant = new TableAssistant(assistant.getTable(), players);
                    break;
                case "timer":
                    timerGame=true;
                    ta = new TimerAssistant(assistant.getTimer());
                    break;
                case "dice":
                    diceGame=true;
                    da = new DiceAssistant(assistant.getDice());
                    da.createImageViews(this);
                    break;
            }
        }
    }

    /**
     * Table Functions
     */
    public void addRow(View view){
        tableAssistant.addRow(this);
        ScrollView sv = (ScrollView) findViewById(R.id.svScoreTable);
        sv.fullScroll(HorizontalScrollView.FOCUS_DOWN);
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
