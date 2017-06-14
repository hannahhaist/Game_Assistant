package com.moco.android.assistant;


import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;


public class InGameActivity extends Activity {
    public Handler gameHandler;
    Thread thread;
    TextView tvTimer;
    String timerMessage;
    TimerAssistant ta;
    DiceAssistant da;


    public InGameActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_in_game);

        Assistant assistant = getCustomAssistant();

        da = new DiceAssistant(assistant.getDice());
        ta = new TimerAssistant(assistant.getTimer());

        /*
        !!!TEST FOR DICETHROW!!!!
         */

    }

    /*
    change views
     */
    public void changeToDice(View view){
        setContentView(R.layout.activity_dice_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimer.setText(ta.getTimer());
    }

    public void changeToTimer(View view){
        setContentView(R.layout.activity_timer_in_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimer.setText(ta.getTimer());
    }

    public void changeToTable(View view){
        setContentView(R.layout.activity_table_in_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimer.setText(ta.getTimer());
    }


    public void nextPlayer(View view) {
    }

    /*
    DICE FUNCTIONS
     */

    public void diceRoll(View view){
        TextView tv = getDice();
       LinearLayout ll = (LinearLayout) findViewById(R.id.llDiceRoll);
        ll.addView(tv);
       // ArrayList<Integer> testThrow = da.testThrow();
        //System.out.println(da.sumContribution(da.sort(testThrow)));
    }

    protected TextView getDice(){
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        tv.setText(""+da.getRandomNumber());

        return tv;
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
