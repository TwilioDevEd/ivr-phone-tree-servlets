package com.twilio.phonetree.servlet.ivr;

import com.twilio.twiml.Gather;
import com.twilio.twiml.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws IOException {
        VoiceResponse response = new VoiceResponse.Builder()
                .gather(new Gather.Builder()
                        .action("/menu/show")
                        .numDigits(1)
                        .build())
                .say(new Say.Builder(
                        "Thanks for calling the E T Phone Home Service. "
                        + "Please press 1 for directions."
                        + "Press 2 for a list of planets to call.")
                        .loop(3)
                        .build())
                .build();

        servletResponse.setContentType("text/xml");
        try {
            servletResponse.getWriter().write(response.toXml());
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }
}
