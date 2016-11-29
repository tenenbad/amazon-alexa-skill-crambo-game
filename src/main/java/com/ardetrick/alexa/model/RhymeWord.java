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
    private int score;
    private String numSyllables;
    private boolean hasBeenGuessed;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNumSyllables() {
        return numSyllables;
    }

    public void setNumSyllables(String numSyllables) {
        this.numSyllables = numSyllables;
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
                ", score=" + score +
                ", numSyllables='" + numSyllables + '\'' +
                ", hasBeenGuessed=" + hasBeenGuessed +
                '}';
    }

    public static List<RhymeWord> listFromHashMap(ArrayList<LinkedHashMap> mapList) {
        List<RhymeWord> newList = new ArrayList<>();
        for(LinkedHashMap map : mapList){
            RhymeWord rw = new RhymeWord();
            rw.setWord((String) map.get("word"));
            rw.setScore((int) map.get("score"));
            rw.setHasBeenGuessed((boolean) map.get("hasBeenGuessed"));
            newList.add(rw);
        }
        return newList;
    }
}
