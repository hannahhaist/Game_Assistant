package com.moco.android.assistant;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Assistant implements Serializable{

    private Map<String, Map<String, String>> features = new LinkedHashMap<String, Map<String, String>>();

    public Assistant(Map<String, Map<String, String>> features){
        this.features = features;
    }

    public Map<String, Map<String, String>> getFeatures() {
        return this.features;
    }

    private void setFeatures(Map<String, Map<String, String>> features) {
        this.features = features;
    }

    public void setDice(Map<String, String> diceSettings){
        this.features.put("dice", diceSettings);
    }

    public void setTable(Map<String, String> tableSettings){
        this.features.put("table", tableSettings);
    }

    public void setTimer(Map<String, String> timerSettings){
        this.features.put("table", timerSettings);
    }

    public Map<String, String> getDice(){
        return this.features.get("dice");
    }

    public Map<String, String> getTable(){
        return this.features.get("table");
    }

    public Map<String, String> getTimer(){
        return this.features.get("timer");
    }

}
