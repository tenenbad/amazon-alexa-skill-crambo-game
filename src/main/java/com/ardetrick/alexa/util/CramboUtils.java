package com.ardetrick.alexa.util;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.ardetrick.alexa.model.RhymeWord;

import java.util.List;
import java.util.Random;

/**
 * Created by Drew Tenenbaum on 11/22/2016.
 */
public class CramboUtils {


    public static boolean isGameInProgress(Session session) {
        try {
            return session.getAttribute("gameStarted").toString().equals("true");
        }catch(NullPointerException npe){
            return false;
        }
    }

    public static SpeechletResponse getSimpleReprompt(String responseText){
        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

    public static int getNextWordIndex(List<RhymeWord> rhymes) {
        Random r = new Random();
        double percentage = Math.random();
        if(percentage <= 0.15){
            //most frequently used word
            return 0;
        }else if (percentage <= 60){
            //word in the top 25% of frequency of use
            return r.nextInt((int)Math.floor(rhymes.size()/4));
        }else if (percentage <= 85){
            //word in the top half of frequency of use
            return r.nextInt((int)Math.floor(rhymes.size()/2));
        }else{
            //words in the bottom 33%
            return (int)Math.floor(rhymes.size()/3) * 2 + (r.nextInt((int)Math.floor(rhymes.size()/3)));
        }

    }

    public static SpeechletResponse getSimpleTell(String responseText) {
        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);
        return SpeechletResponse.newTellResponse(plainTextOutputSpeech);

    }
}
