package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;
import com.ardetrick.alexa.model.RhymeWord;
import com.ardetrick.alexa.service.HelloWorldService;
import com.ardetrick.alexa.service.RhymeService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GetRhymesIntentAction implements IntentAction {

    private static final String SLOT_WORD = "word";

    private RhymeService rhymeService;

    protected GetRhymesIntentAction() {

    }

    @Inject
    protected GetRhymesIntentAction(RhymeService rhymeService) {
        this.rhymeService = rhymeService;
    }

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        return Optional.ofNullable(intent.getSlot(SLOT_WORD))
                .map(Slot::getValue)
                .map(name -> getRhymesResponse(name))
                .orElse(getRepromptResposne());
    }

    /*
     * Returns a SpeechletResponse which reprompts the user to try again.
     */
    private SpeechletResponse getRepromptResposne() {

        final String responseText = "I'm sorry, I'm not sure what you mean. Please try again'";

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(plainTextOutputSpeech);

        return SpeechletResponse.newAskResponse(plainTextOutputSpeech, reprompt);
    }

    /*
     * Returns a SpeechletResponse which says hello.
     */
    private SpeechletResponse getRhymesResponse(final String word) {
        //Get the rhymes. For now, just choose a random one and tell us
        List<RhymeWord> rhymes = rhymeService.getWordsThatPerfectRhymeWith(word);
        String responseText;

        if(rhymes.size() > 0) {
            Random r = new Random();
            RhymeWord randomWord = rhymes.get(r.nextInt(rhymes.size()));

            responseText = randomWord.getWord() + " is a word that rhymes with " + word;
        }else{
            responseText = word + " is either not a real word, or doesn't rhyme with anything.";
        }

        PlainTextOutputSpeech plainTextOutputSpeech = new PlainTextOutputSpeech();
        plainTextOutputSpeech.setText(responseText);

        return SpeechletResponse.newTellResponse(plainTextOutputSpeech);
    }

}
