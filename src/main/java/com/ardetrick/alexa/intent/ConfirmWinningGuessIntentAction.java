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

public class ConfirmWinningGuessIntentAction implements IntentAction {

    private static final String SLOT_WORD = "EnglishWord";

    private RhymeService rhymeService;
    private DefinitionService definitionService;

    protected ConfirmWinningGuessIntentAction() {

    }

    @Inject
    protected ConfirmWinningGuessIntentAction(RhymeService rhymeService, DefinitionService definitionService) {
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
                    .map(word -> getThanksForPlayingResponse(word, session))
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
    private SpeechletResponse getThanksForPlayingResponse(final String word, Session session) {

        final String responseText = "Hooray! I win! I guessed your word! The word you were thinking of was: " + word + "!";

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech);
    }



}
