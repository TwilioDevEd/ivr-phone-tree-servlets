package com.twilio.phonetree.servlet.commuter;

import com.twilio.phonetree.servlet.common.Redirect;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConnectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws IOException {

        String selectedOption = servletRequest.getParameter("Digits");
        Map<String, String> optionPhones = new HashMap<>();
        optionPhones.put("2", "+12024173378");
        optionPhones.put("3", "+12027336386");
        optionPhones.put("4", "+12027336637");

        VoiceResponse twiMLResponse = optionPhones.containsKey(selectedOption)
                ? dial(optionPhones.get(selectedOption))
                : Redirect.toMainMenu();

        servletResponse.setContentType("text/xml");
        try {
            servletResponse.getWriter().write(twiMLResponse.toXml());
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }

    private VoiceResponse dial(String phoneNumber) {
        Number number = new Number.Builder(phoneNumber).build();
        return new VoiceResponse.Builder()
                .dial(new Dial.Builder().number(number).build())
                .build();
    }
}

