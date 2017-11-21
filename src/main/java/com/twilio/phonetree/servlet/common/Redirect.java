package com.twilio.phonetree.servlet.common;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;

public final class Redirect {

    private Redirect() {
        // To prevent instantiation.
    }

    public static VoiceResponse toMainMenu() {

        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder("Returning to the main menu")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .redirect(new com.twilio.twiml.voice.Redirect.Builder("/irv/welcome").build())
                .build();

        return response;
    }
}

