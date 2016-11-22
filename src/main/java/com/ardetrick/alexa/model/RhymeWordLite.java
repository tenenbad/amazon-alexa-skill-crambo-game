package com.ardetrick.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Drew Tenenbaum on 11/21/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RhymeWordLite {

    private String word;
    private int freq;
    private boolean hasBeenGuessed;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public boolean isHasBeenGuessed() {
        return hasBeenGuessed;
    }

    public void setHasBeenGuessed(boolean hasBeenGuessed) {
        this.hasBeenGuessed = hasBeenGuessed;
    }

    @Override
    public String toString() {
        return "RhymeWordLite{" +
                "word='" + word + '\'' +
                ", freq=" + freq +
                ", hasBeenGuessed=" + hasBeenGuessed +
                '}';
    }

    public static List<RhymeWordLite> listFromHashMap(ArrayList<LinkedHashMap> mapList) {
        List<RhymeWordLite> newList = new ArrayList<>();
        for(LinkedHashMap map : mapList){
            RhymeWordLite rw = new RhymeWordLite();
            rw.setWord((String) map.get("word"));
            rw.setFreq((int) map.get("freq"));
            rw.setHasBeenGuessed((boolean) map.get("hasBeenGuessed"));
            newList.add(rw);
        }
        return newList;
    }

    public static List<RhymeWordLite> listFromRhymeWords(List<RhymeWord> notLiteList) {
        List<RhymeWordLite> newList = new ArrayList<>();
        for(RhymeWord notLiteWord : notLiteList){
            RhymeWordLite rw = new RhymeWordLite();
            rw.setWord(notLiteWord.getWord());
            rw.setFreq(notLiteWord.getFreq());
            rw.setHasBeenGuessed(notLiteWord.isHasBeenGuessed());
            newList.add(rw);
        }
        return newList;
    }
}
