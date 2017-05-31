package com.moco.android.assistant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Assistant implements Serializable{

    private HashMap<String, String[]> features = new LinkedHashMap<String, String[]>();

    public Assistant(){}

    public Assistant(HashMap<String, String[]> features){
        this.features = features;
    }
}
