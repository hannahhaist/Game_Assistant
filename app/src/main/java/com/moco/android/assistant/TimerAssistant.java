package com.moco.android.assistant;

import android.app.Notification;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Hannah on 08.06.2017.
 */

/*
    Timer class
     */
class TimerAssistant implements Runnable{
    boolean paused = true;
    boolean timeLeft = true;
    private int startHour;
    private int startMinute;
    private int startSecond;
    private int hour;
    private int minute;
    private int second;
    Handler gameHandler;

    Message message;

    protected TimerAssistant(Map<String, String> time){

        this.hour = Integer.parseInt(time.get("hours"));
        this.startHour = hour;
        this.minute = Integer.parseInt(time.get("minutes"));
        this.startMinute = minute;
        this.second = Integer.parseInt(time.get("seconds"));
        this.startSecond = second;
    }

    @Override public void run() {
        Looper.prepare();
        paused = false;
        try {
            while (!paused && timeLeft ){
                message = Message.obtain();
                message.obj = getTimer();
                gameHandler.sendMessage(message);

                Thread.sleep(1000);
                countdown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!timeLeft){
            message = Message.obtain();
            message.obj = "Time over";
            gameHandler.sendMessage(message);
        }


    }

    private void countdown(){
        if(second == 0){
            if(minute==0){
                if(hour == 0){
                    timeLeft = false;
                }
                else{
                    hour--;
                    minute = 59;
                    second = 59;
                }
            }
            else{
                minute--;
                second=59;

            }
        }
        else{
            second--;
        }
    }

    protected String getTimer(){
        return hour+":"+minute+":"+second;
    }

    protected void pause(){
        paused = !paused;
    }

    protected void resetTimer(){
        this.hour = startHour;
        this.minute = startMinute;
        this.second = startSecond;
        paused = true;
    }

    protected void setGameHandler(Handler gameHandler){
        this.gameHandler = gameHandler;
    }


}



