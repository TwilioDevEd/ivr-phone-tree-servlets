package com.twilio.phonetree.servlet.common;

import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class Redirect {

    public static TwiMLResponse toMainMenu() throws TwiMLException {

        Say say = new Say("Returning to the main menu");
        say.setVoice("alice");
        say.setLanguage("en-GB");

        TwiMLResponse response = new TwiMLResponse();

        response.append(say);
        response.append(new com.twilio.sdk.verbs.Redirect("/ivr/welcome"));

        return response;
    }
}
