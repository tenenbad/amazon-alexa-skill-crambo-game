package com.ardetrick.alexa.intent;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.ardetrick.alexa.model.RhymeWordLite;
import com.ardetrick.alexa.service.DefinitionService;
import com.ardetrick.alexa.service.RhymeService;
import com.ardetrick.alexa.service.RhymeServiceBetter;
import com.ardetrick.alexa.util.CramboUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AmazonNoIntentAction extends RespondToGuessIntentAction {

    @Override
    public SpeechletResponse perform(final Intent intent,final Session session) {
        if(!CramboUtils.isGameInProgress(session)){
            return getGameNotstartedtResponse();
        }else{
            return getNextGuessResponse("", session);
        }
    }

}
