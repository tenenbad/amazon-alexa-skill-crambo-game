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
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StartCramboIntentAction implements IntentAction {

    private static final String SLOT_WORD = "EnglishWord";

    private RhymeService rhymeService;
    private DefinitionService definitionService;

    protected StartCramboIntentAction() {

    }

    @Inject
    protected StartCramboIntentAction(RhymeService rhymeService, DefinitionService definitionService) {
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

        if(gameStarted){
            return getGameInProgressResponse();
        }else{
            return Optional.ofNullable(intent.getSlot(SLOT_WORD))
                    .map(Slot::getValue)
                    .map(word -> getDefinitionGuessResponse(word, session))
                    .orElse(getBadInputResponse());
        }
    }

    /*
     * Returns a SpeechletResponse which reprompts the user to try again.
     */
    private SpeechletResponse getBadInputResponse() {

        final String responseText = "You need to tell me what your word rhymes with.";

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

    /*
 * Returns a SpeechletResponse which tells the user a game is in progress
 */
    private SpeechletResponse getGameInProgressResponse() {

        final String responseText = "A game is already in progress. Say Quit to end the game";

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

    /*
     * Returns a SpeechletResponse which says hello.
     */
    private SpeechletResponse getDefinitionGuessResponse(final String word, Session session) {
        //Get the rhymes. For now, just choose a random one and tell us
        List<RhymeWord> rhymes = rhymeService.getWordsThatPerfectRhymeWith(word);
        String responseText;

        if(rhymes.size() > 0) {
            Random r = new Random();
            int randPosition = r.nextInt(rhymes.size());
            RhymeWord randomWord = rhymes.get(randPosition);
            randomWord.setHasBeenGuessed(true);
            rhymes.set(randPosition, randomWord);

            //responseText = randomWord.getWord() + " is a word that rhymes with " + word;
            String definition = definitionService.removeTrailingSpacesAndPunctuation(definitionService.getDefinition(randomWord.getWord()));
            responseText = "Is it: " + definition + "?";

            session.setAttribute("baseWord", word);
            session.setAttribute("lastWordGuessed", randomWord.getWord());
            session.setAttribute("gameStarted", "true");
        }else{
            responseText = word + " is either not a real word, or doesn't rhyme with anything.";
        }

        session.setAttribute("rhymeWords", rhymes);

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

}
