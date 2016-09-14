package com.twilio.phonetree;


import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;

public final class ContentTypeVerifier {

    private ContentTypeVerifier() {
        // To prevent instantiation.
    }

    public static void verifyThatContentTypeIsXml(HttpServletResponse response) {
        verify(response).setContentType("text/xml");
    }
}
