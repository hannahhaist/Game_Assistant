package com.moco.android.assistant;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Hannah on 12.06.2017.
 */

public class DiceAssistant {
    int type;
    int number;
    Random random;

    ArrayList<Integer> inputArray;

    public DiceAssistant(Map<String, String> diceSettings){
        this.type= Integer.parseInt(diceSettings.get("type"));
        this.number= Integer.parseInt(diceSettings.get("number"));
        this.random = new Random();
    }

    public ArrayList<Integer> testThrow(){
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        for(int i = 0; i < 1000; i++){
            randomList.add(getRandomNumber());
        }
        return randomList;
    }

    protected int getRandomNumber(){
        return random.nextInt(type)+1;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> aryList){
        inputArray = aryList;
        sortGivenArray();
        return inputArray;
    }



    /**
     * Gets a sorted list of Integers and returns an arraylist of arayList, that contains how often
     * the input list containts each number
     * @param aryList
     * @return
     */
    public ArrayList<ArrayList<Integer>> sumContribution(ArrayList<Integer> sumList){
        assert(sumList != null && sumList.size()>0);
        ArrayList<ArrayList<Integer>> contributionList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> list;
        int currentNumber = sumList.get(0);
        int countNumber=0;
        for(int i = 0 ; i<sumList.size();i++){
            if(currentNumber < sumList.get(i)){
                list = new ArrayList<Integer>();
                list.add(currentNumber);
                list.add(countNumber);
                contributionList.add(list);

                currentNumber = sumList.get(i);
                countNumber = 0;
            }
            countNumber++;

        }
        list = new ArrayList<Integer>();
        list.add(currentNumber);
        list.add(countNumber);
        contributionList.add(list);
        return contributionList;
    }


    public void sortGivenArray(){
        divide(0, this.inputArray.size()-1);
    }

    public void divide(int startIndex,int endIndex){

        //Divide till you breakdown your list to single element
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);

            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);
        }
    }

    public void merger(int startIndex,int midIndex,int endIndex){

        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<Integer> mergedSortedArray = new ArrayList<Integer>();

        int leftIndex = startIndex;
        int rightIndex = midIndex+1;

        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(inputArray.get(leftIndex)<=inputArray.get(rightIndex)){
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }

        //Either of below while loop will execute
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }

        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }


}
