package com.twilio.phonetree.servlet.menu;


import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Say;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws IOException {

        String selectedOption = servletRequest.getParameter("Digits");

        VoiceResponse response;
        switch (selectedOption) {
            case "1":
                response = getReturnInstructions();
                break;
            case "2":
                response = getPlanets();
                break;
            default:
                response = com.twilio.phonetree.servlet.common.Redirect.toMainMenu();
        }

        servletResponse.setContentType("text/xml");
        try {
            servletResponse.getWriter().write(response.toXml());
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }

    private VoiceResponse getReturnInstructions() {

        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder(
                        "To get to your extraction point, get on your bike and go down "
                        + "the street. Then Left down an alley. Avoid the police cars. Turn left "
                        + "into an unfinished housing development. Fly over the roadblock. Go "
                        + "passed the moon. Soon after you will see your mother ship.")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .say(new Say.Builder(
                        "Thank you for calling the ET Phone Home Service - the "
                        + "adventurous alien's first choice in intergalactic travel")
                        .build())
                .hangup(new Hangup.Builder().build())
                .build();

        return response;
    }

    private VoiceResponse getPlanets() {

        VoiceResponse response = new VoiceResponse.Builder()
                .gather(new Gather.Builder()
                        .action("/commuter/connect")
                        .numDigits(1)
                        .build())
                .say(new Say.Builder(
                        "To call the planet Broh doe As O G, press 2. To call the planet "
                        + "DuhGo bah, press 3. To call an oober asteroid to your location"
                        + ", press 4. To go back to the main menu, press the star key ")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .loop(3)
                        .build()
                ).build();

        return response;
    }
}

