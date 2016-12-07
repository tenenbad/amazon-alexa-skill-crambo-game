package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.ardetrick.alexa.util.CramboUtils;

import java.util.Optional;

public class AmazonYesIntentAction extends ConfirmWinningGuessIntentAction {

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        if(!CramboUtils.isGameInProgress(session)){
            return getGameNotstartedtResponse();
        }else{
            return getThanksForPlayingResponse("", session);
        }
    }

}
