package com.ardetrick.alexa;

import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;

/**
 * This class could be the handler for an AWS Lambda function powering an Alexa Skills Kit
 * experience. To do this, simply set the handler field in the AWS Lambda console to
 * "com.ardetrick.alexa.niceride.AlexaStarterSpeechletRequestStreamHandler" For this to work, you'll
 * also need to build this project using the {@code lambda-compile} Ant task and upload
 * the resulting zip file to power your function.
 */
public final class AlexaStarterSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
  /*
   * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
   * Alexa Skill and put the relevant Application Ids in this Set.
   * Replace this with your own ID
   */
    private static final Set<String> supportedApplicationIds = ImmutableSet.of("amzn1.ask.skill.79dc51e0-1800-4e29-9398-c6dec8ecf928");

    public AlexaStarterSpeechletRequestStreamHandler() {
        super(Guice.createInjector(new AlexaStarterApplicationModule()).getInstance(AlexaStarterSpeechlet.class),
                supportedApplicationIds);
    }
}