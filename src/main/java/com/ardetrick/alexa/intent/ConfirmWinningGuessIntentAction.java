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
import com.ardetrick.alexa.util.CramboUtils;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class ConfirmWinningGuessIntentAction implements IntentAction {

    protected static final String SLOT_WORD = "EnglishWord";

    protected ConfirmWinningGuessIntentAction() {}

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        if(!CramboUtils.isGameInProgress(session)){
            return getGameNotstartedtResponse();
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
    protected SpeechletResponse getGameNotstartedtResponse() {

        final String responseText = "A game hasn't started yet. You need to tell me what your word rhymes with.";
        return CramboUtils.getSimpleReprompt(responseText);
    }


    /*
     * Returns a SpeechletResponse which reprompts the user to try again.
     */
    protected SpeechletResponse getBadInputResponse() {

        final String responseText = "A game hasn't started yet. You need to tell me what your word rhymes with.";
        return CramboUtils.getSimpleReprompt(responseText);
    }

    /*
     * Returns a SpeechletResponse which says hello.
     */
    protected SpeechletResponse getThanksForPlayingResponse(final String word, Session session) {

        String lastGuess = (String)session.getAttribute("lastWordGuessed");
        int numTries = (int)session.getAttribute("numGuesses");
        String tryText = numTries == 1 ? "try" : "tries";
        //final String responseText = "Hooray! I win! I guessed your word! The word you were thinking of was: " + lastGuess + "!";
        final String responseText = "Hooray! I win! I guessed your word, and it only took me " + numTries + " " + tryText + "! The word you were thinking of was: " + lastGuess + "!";
        //sfinal String responseText = "Hooray! I win! I guessed your word, and it only took me " + numTries + tryText + "! ";


        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech);
    }

}
