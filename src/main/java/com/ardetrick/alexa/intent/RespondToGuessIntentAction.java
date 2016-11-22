package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.ardetrick.alexa.model.RhymeWord;
import com.ardetrick.alexa.service.DefinitionService;
import com.ardetrick.alexa.service.RhymeService;
import com.ardetrick.alexa.util.CramboUtils;

import javax.inject.Inject;
import java.util.*;

public class RespondToGuessIntentAction implements IntentAction {

    private static final String SLOT_WORD = "EnglishWord";

    private RhymeService rhymeService;
    private DefinitionService definitionService;

    protected RespondToGuessIntentAction() {}

    @Inject
    protected RespondToGuessIntentAction(RhymeService rhymeService, DefinitionService definitionService) {
        this.rhymeService = rhymeService;
        this.definitionService = definitionService;
    }

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        if(!CramboUtils.isGameInProgress(session)){
            return getGameNotstartedtResponse();
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
    private SpeechletResponse getGameNotstartedtResponse() {

        final String responseText = "A game hasn't started yet. You need to tell me what your word rhymes with.";
        return CramboUtils.getSimpleReprompt(responseText);
    }

    /*
 * Returns a SpeechletResponse which reprompts the user to try again.
 */
    private SpeechletResponse getBadInputResponse() {

        final String responseText = "I didn't understand your response. Please try again.";
        return CramboUtils.getSimpleReprompt(responseText);
    }


    /*
     * Returns a SpeechletResponse which says hello.
     */
    private SpeechletResponse getNextGuessResponse(final String word, Session session) {
        //Get the rhymes. this time, from the session, so we only need to call the rhymeService once.
        ArrayList<LinkedHashMap> map = (ArrayList<LinkedHashMap>) session.getAttribute("rhymeWords");
        List<RhymeWord> rhymesArray = RhymeWord.listFromHashMap(map);
        List<RhymeWord> rhymes  = RhymeService.filterByNotYetGuessed(rhymesArray);

        String responseText = "";

        if(rhymes.size() > 0) {
            int randomWordIndex = CramboUtils.getNextWordIndex(rhymes);
            RhymeWord nextGuess = rhymes.get(randomWordIndex);
            nextGuess.setHasBeenGuessed(true);
            rhymes.set(randomWordIndex, nextGuess);

            String expectedWord = (String)session.getAttribute("lastWordGuessed");
            if(!word.equalsIgnoreCase(expectedWord)){
                responseText = "I was actually thinking it might be " + expectedWord + ". ";
            }

            responseText = addDefinition(responseText, nextGuess);

            session.setAttribute("lastWordGuessed", nextGuess.getWord());
            session.setAttribute("rhymeWords", rhymes);
            return CramboUtils.getSimpleReprompt(responseText);
        }else{
            responseText = "I cannot guess your word. You have beaten me. Goodbye.";
            return CramboUtils.getSimpleTell(responseText);

        }
    }

    private String addDefinition(String responseText, RhymeWord word) {
        String definitionRaw = definitionService.getDefinition(word.getWord());
        if (definitionRaw == null) {
            responseText += "I can't think of a good clue this time, but I think your word might be " + word.getWord() + "?";
        } else {
            String definition = definitionService.removeTrailingSpacesAndPunctuation(definitionRaw);
            responseText += "Is it: " + definition + "?";
        }
        return responseText;
    }


}
