package com.ardetrick.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Drew Tenenbaum on 11/21/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RhymeWord {

    private String word;
    private int freq;
    private String score;
    private String flags;
    private String syllables;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getSyllables() {
        return syllables;
    }

    public void setSyllables(String syllables) {
        this.syllables = syllables;
    }

    public boolean isHasBeenGuessed() {
        return hasBeenGuessed;
    }

    public void setHasBeenGuessed(boolean hasBeenGuessed) {
        this.hasBeenGuessed = hasBeenGuessed;
    }

    @Override
    public String toString() {
        return "RhymeWord{" +
                "word='" + word + '\'' +
                ", freq=" + freq +
                ", score='" + score + '\'' +
                ", flags='" + flags + '\'' +
                ", syllables='" + syllables + '\'' +
                ", hasBeenGuessed=" + hasBeenGuessed +
                '}';
    }

    public static List<RhymeWord> listFromHashMap(ArrayList<LinkedHashMap> mapList) {
        List<RhymeWord> newList = new ArrayList<>();
        for(LinkedHashMap map : mapList){
            RhymeWord rw = new RhymeWord();
            rw.setWord((String) map.get("word"));
            rw.setFreq((int) map.get("freq"));
            rw.setScore((String) map.get("score"));
            rw.setFlags((String) map.get("flags"));
            rw.setSyllables((String) map.get("syllables"));
            rw.setHasBeenGuessed((boolean) map.get("hasBeenGuessed"));
            newList.add(rw);
        }
        return newList;
    }
}
