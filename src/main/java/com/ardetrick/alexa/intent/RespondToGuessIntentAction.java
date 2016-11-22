package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.ardetrick.alexa.model.RhymeWord;
import com.ardetrick.alexa.service.DefinitionService;
import com.ardetrick.alexa.service.RhymeService;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class RespondToGuessIntentAction implements IntentAction {

    private static final String SLOT_WORD = "EnglishWord";

    private RhymeService rhymeService;
    private DefinitionService definitionService;

    protected RespondToGuessIntentAction() {

    }

    @Inject
    protected RespondToGuessIntentAction(RhymeService rhymeService, DefinitionService definitionService) {
        this.rhymeService = rhymeService;
        this.definitionService = definitionService;
    }

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        boolean gameStarted = false;
        try {
            gameStarted = session.getAttribute("gameStarted").toString().equals("true");
        }catch(NullPointerException npe){
            gameStarted = false;
        }
        if(!gameStarted){
            return getBadInputResponse();
        }else{
            return Optional.ofNullable(intent.getSlot(SLOT_WORD))
                    .map(Slot::getValue)
                    .map(word -> getNextGuessResponse(word, session))
                    .orElse(getBadInputResponse());
        }
    }

    /*
     * Returns a SpeechletResponse which reprompts the user to try again.
     */
    private SpeechletResponse getBadInputResponse() {

        final String responseText = "A game hasn't started yet. You need to tell me what your word rhymes with.";

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }


    /*
     * Returns a SpeechletResponse which says hello.
     */
    private SpeechletResponse getNextGuessResponse(final String word, Session session) {
        //Get the rhymes. For now, just choose a random one and tell us
        ArrayList<LinkedHashMap> map = (ArrayList<LinkedHashMap>) session.getAttribute("rhymeWords");
        List<RhymeWord> rhymesArray = RhymeWord.listFromHashMap(map);

        List<RhymeWord> rhymes = rhymesArray.stream().
                filter(rhymeWord -> !rhymeWord.isHasBeenGuessed()).
                collect(Collectors.toList());
        String responseText = "";

        if(rhymes.size() > 0) {
            //get next guess
            int randomWordIndex = getNextWordIndex(rhymes);

            RhymeWord randomWord = rhymes.get(randomWordIndex);
            randomWord.setHasBeenGuessed(true);
            rhymes.set(randomWordIndex, randomWord);

            String expectedWord = (String)session.getAttribute("lastWordGuessed");
            if(!word.equalsIgnoreCase(expectedWord)){
                responseText = "I was actually thinking it might be " + expectedWord + ".";
            }

            String definitionRaw = definitionService.getDefinition(randomWord.getWord());
            if (definitionRaw == null) {
                responseText += " I can't think of a good clue this time, but I think your word might be " + randomWord.getWord() + "?";
            } else {
                String definition = definitionService.removeTrailingSpacesAndPunctuation(definitionRaw);
                responseText += " Is it: " + definition + "?";
            }

            session.setAttribute("lastWordGuessed", randomWord.getWord());
        }else{
            responseText = "Something went wrong.";
        }

        session.setAttribute("rhymeWords", rhymes);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

    private int getNextWordIndex(List<RhymeWord> rhymes) {
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

}
