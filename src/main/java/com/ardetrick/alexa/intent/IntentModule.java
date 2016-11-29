package com.ardetrick.alexa.intent;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class IntentModule extends AbstractModule {

    /*
     * Configures all IntentAction implementations. Binding them to a Map so that they
     * can be looked up by their Amazon Intent name.
     */
    @Override
    protected void configure() {
        bind(IntentHandlerService.class).to(IntentHandlerServiceImpl.class);

        MapBinder<String, IntentAction> mapBinder =
                MapBinder.newMapBinder(binder(), String.class, IntentAction.class);

        // Amazon intents
        mapBinder.addBinding("AMAZON.HelpIntent").to(AmazonHelpIntentAction.class);
        mapBinder.addBinding("AMAZON.StartOverIntent").to(AmazonHelpIntentAction.class);
        mapBinder.addBinding("AMAZON.StopIntent").to(AmazonStopIntentAction.class);
        mapBinder.addBinding("AMAZON.CancelIntent").to(AmazonStopIntentAction.class);
        mapBinder.addBinding("AMAZON.YesIntent").to(AmazonYesIntentAction.class);
        mapBinder.addBinding("AMAZON.NoIntent").to(AmazonNoIntentAction.class);

        // custom intents
        mapBinder.addBinding("StartCramboIntent").to(StartCramboIntentAction.class);
        mapBinder.addBinding("RespondToGuessIntent").to(RespondToGuessIntentAction.class);
        mapBinder.addBinding("ConfirmWinningGuessIntent").to(ConfirmWinningGuessIntentAction.class);
    }
}
