package com.ardetrick.alexa.util;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.ardetrick.alexa.model.RhymeWord;
import com.ardetrick.alexa.model.RhymeWordLite;
import com.ardetrick.alexa.service.DefinitionService;

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

    public static int getNextWordIndex(List<?> rhymes) {
        Random r = new Random();
        double percentage = Math.random();

        try {
            if (percentage <= 0.40) {
                //most frequently used word
                return 0;
            } else if (percentage <= 0.68) {
                //word in the top 25% of frequency of use
                return r.nextInt((int) Math.floor(rhymes.size() / 4));
            } else if (percentage <= 0.95) {
                //word in the top half of frequency of use
                return r.nextInt((int) Math.floor(rhymes.size() / 2));
            } else {
                //words in the bottom 33%
                return (int) Math.floor(rhymes.size() / 3) * 2 + (r.nextInt((int) Math.floor(rhymes.size() / 3)));
            }
        }catch(IllegalArgumentException e){
            //if we try to pass random a 0, just return zero instead.
            return 0;
        }

    }

    public static SpeechletResponse getSimpleTell(String responseText) {
        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);
        return SpeechletResponse.newTellResponse(plainTextOutputSpeech);

    }

    public static String addDefinition(DefinitionService definitionService, String responseText, RhymeWordLite word, Session session) {
        String definitionRaw = definitionService.getDefinition(word.getWord());
        if (definitionRaw == null) {
            responseText += "I can't think of a good clue this time, but I think your word might be " + word.getWord() + "?";
            session.setAttribute("w", "true");
        } else {
            String definition = definitionService.removeTrailingSpacesAndPunctuation(definitionRaw);
            responseText += "Is it: " + definition + "?";
            session.setAttribute("w", "false");
        }
        return responseText;
    }


}
