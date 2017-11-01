package com.twilio.phonetree.servlet.ivr;

import com.twilio.twiml.Gather;
import com.twilio.twiml.Play;
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
                .play(new Play.Builder("https://raw.githubusercontent.com/TwilioDevEd/ivr-phone-tree-servlets/master/et-phone.mp3")
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
