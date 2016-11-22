package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.ardetrick.alexa.service.DefinitionService;
import com.ardetrick.alexa.service.RhymeService;
import com.ardetrick.alexa.util.CramboUtils;

import javax.inject.Inject;
import java.util.Optional;

public class QuitGameIntentAction implements IntentAction {

    private static final String SLOT_WORD = "EnglishWord";

    protected QuitGameIntentAction() {}

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        return getGoodbyeResponse();
    }

    /*
     * Returns a SpeechletResponse which reprompts the user to try again.
     */
    private SpeechletResponse getGoodbyeResponse() {
        final String responseText = "Goodbye.";
        return CramboUtils.getSimpleReprompt(responseText);
    }


}
