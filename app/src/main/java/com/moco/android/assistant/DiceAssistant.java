

package com.moco.android.assistant;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Hannah on 12.06.2017.
 */

public class DiceAssistant {
    int type;
    int number;
    Random random;
    TextView[] imageViews;
    Map<Integer, String> dicePictures;

    ArrayList<Integer> inputArray;

    public DiceAssistant(Map<String, String> diceSettings){
        this.type= Integer.parseInt(diceSettings.get("type"));
        this.number= Integer.parseInt(diceSettings.get("number"));
        this.random = new Random();
        this.imageViews = new TextView[number];
        dicePictures = new TreeMap<>();
        setDiceNames();
    }

    public ArrayList<Integer> diceThrow() {
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        for(int i = 0; i < number; i++){
            randomList.add(getRandomNumber());
        }
        return randomList;
    }


    protected int getRandomNumber(){
        return random.nextInt(type)+1;
    }



protected void createImageViews(InGameActivity inGameActivity) {
  //  LinearLayout ll = (LinearLayout) inGameActivity.findViewById(R.id.rlDiceRoll);
    for (int i = 0; i < number; i++) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(300,300);

        TextView ivDice = new TextView(inGameActivity);
        lparams.setMargins(50,50,50,50);
        ivDice.setLayoutParams(lparams);
        String picture = dicePictures.get(getRandomNumber());
        Context context = ivDice.getContext();
        int id = context.getResources().getIdentifier(picture, "drawable", context.getPackageName());
        ivDice.setGravity(Gravity.CENTER);
        if(type!=6) {
            ivDice.setTextSize(30);
            ivDice.setTextColor(Color.WHITE);
            ivDice.setText("" + getRandomNumber());
        }
        ivDice.setBackgroundResource(id);

       /* ivDice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));*/
        imageViews[i] = ivDice;
  //      ll.addView(ivDice);

    }
}

protected void addImageViews(InGameActivity inGameActivity){
    LinearLayout ll = (LinearLayout) inGameActivity.findViewById(R.id.rlDiceRoll);
    for (int i = 0; i < number; i++) {
        ll.addView(imageViews[i]);
    }
}

    protected void setNewDiceImage(ArrayList<Integer> random){

        for(int i=0;i<number;i++){
            TextView ivDice = imageViews[i];
            if(type!=6) {

                ivDice.setText("" + random.get(i));

            }
            else{
                String picture = dicePictures.get(random.get(i));
                Context context = ivDice.getContext();
                int id = context.getResources().getIdentifier(picture, "drawable", context.getPackageName());
                ivDice.setBackgroundResource(id);
            }
        }
    }

    /*
    set the dice names in a hashmap
     */
    protected void setDiceNames(){
        switch (type){
            case 4:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d4");
                    System.out.println(dicePictures.get(i));
                }
                break;
            case 6:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d6_"+i);
                    System.out.println(dicePictures.get(i));
                }
                break;
            case 8:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d8");
                    System.out.println(dicePictures.get(i));
                }
                break;
            case 10:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d10");
                    System.out.println(dicePictures.get(i));
                }
                break;
            case 12:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d12");
                    System.out.println(dicePictures.get(i));
                }
                break;
            case 20:
                for(int i = 1 ; i<=type;i++){
                    dicePictures.put(i, "d20");
                    System.out.println(dicePictures.get(i));
                }
                break;
        }

    }
}
