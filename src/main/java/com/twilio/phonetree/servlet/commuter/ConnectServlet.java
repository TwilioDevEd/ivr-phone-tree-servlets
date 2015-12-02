package com.twilio.phonetree.servlet.commuter;

import com.twilio.phonetree.servlet.common.Redirect;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

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

        TwiMLResponse twiMLResponse = null;

        try {
            twiMLResponse = optionPhones.containsKey(selectedOption)
                    ? dial(optionPhones.get(selectedOption))
                    : Redirect.toMainMenu();
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        servletResponse.setContentType("text/xml");
        servletResponse.getWriter().write(twiMLResponse.toXML());
    }

    private TwiMLResponse dial(String phoneNumber) throws TwiMLException {

        TwiMLResponse response = new TwiMLResponse();
        response.append(new Dial(phoneNumber));

        return response;
    }
}
