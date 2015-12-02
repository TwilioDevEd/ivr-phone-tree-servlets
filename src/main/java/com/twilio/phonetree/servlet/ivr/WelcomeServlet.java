package com.twilio.phonetree.servlet.ivr;

import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Play;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws IOException {

        Gather gather = new Gather();
        gather.setAction("/menu/show");
        gather.setNumDigits(1);

        Play play = new Play("http://howtodocs.s3.amazonaws.com/et-phone.mp3");
        play.setLoop(3);

        try {
            gather.append(play);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        TwiMLResponse twiMLResponse = new TwiMLResponse();
        try {
            twiMLResponse.append(gather);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        servletResponse.setContentType("text/xml");
        servletResponse.getWriter().write(twiMLResponse.toXML());
    }
}
