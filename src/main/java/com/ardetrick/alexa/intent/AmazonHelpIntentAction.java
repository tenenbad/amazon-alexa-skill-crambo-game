package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * Provide help about how to use the skill.
 */
public class AmazonHelpIntentAction implements IntentAction {

    protected AmazonHelpIntentAction() {

    }

    @Override
    public SpeechletResponse perform(Intent intent, Session session) {
        String speechText = "Crambo is a guessing game where I guess your word, and you guess my guess. " +
                "Start a game by thinking of a word, then telling me what your word rhymes with. " +
                "For example if your word is 'cute', you can say: " +
                "'My word rhymes with boot.' " +
                "Then I'll try to guess your word by giving a definition of the word I am guessing. " +
                "For example I might guess like this: " +
                "Is it: the sweet and fleshy product of a tree or other plant that contains seed and can be eaten as food? " +
                "You would tell me I'm wrong by saying: " +
                "No. It is not fruit. " +
                "If I give the definition of your word, you would tell me I won by saying: " +
                "Yes it was 'cute.' " +
                "If I don't guess your word. You win! " +
                "Now, let's play...What does your word rhyme with?";

        SimpleCard card = new SimpleCard();
        card.setTitle("Help");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
