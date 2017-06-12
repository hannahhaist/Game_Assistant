package com.moco.android.assistant;


import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Timer;


public class InGameActivity extends Activity {
    Handler gameHandler;
    Handler timerHandler;
    Thread thread;
    TextView tvTimer;
    String timerMessage;
    TimerAssistant ta;


    public InGameActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_in_game);

        timerHandler = new Handler();
        ta = new TimerAssistant(2,3,60,timerHandler);
    }

    /*
    change views
     */
    public void changeToDice(View view){
        setContentView(R.layout.activity_dice_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
    }

    public void changeToTimer(View view){
        setContentView(R.layout.activity_timer_in_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
    }

    public void changeToTable(View view){
        setContentView(R.layout.activity_table_in_game);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
    }


    public void nextPlayer(View view) {
    }

    /*
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
            new Thread(ta).start();
            gameHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    timerMessage = "" + msg.arg1;
                    tvTimer.setText(timerMessage);
                }
            };
        }
        else{
            timerButton.setText("Start Timer");
            ta.pause();

        }
    }




    /*
    Timer class
     */
    class TimerAssistant implements Runnable{
        boolean paused = true;
        private int hour;
        private int minute;
        private int second;

        Message message;
        Handler timerHandler;

        public TimerAssistant(int hour, int minute, int second, Handler timerHandler){
            assert(hour >=0 && hour < 100 && minute >= 0 && minute < 60 && second >=0 && second < 60);
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.timerHandler = timerHandler;

        }

        @Override public void run() {
            Looper.prepare();
            paused = false;
            try {
            while (!paused && second >=0 ){
            message = Message.obtain();
            message.arg1 =second;
            gameHandler.sendMessage(message);
                System.out.println(second);

                Thread.sleep(1000);
                second--;
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void pause(){
            paused = !paused;
        }
        public String returnTimer(){
            return hour+":"+minute+";"+second;
        }


    }

}
