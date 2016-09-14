package com.twilio.phonetree.servlet.ivr;

import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.twilio.phonetree.ContentTypeVerifier.verifyThatContentTypeIsXml;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class WelcomeServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void responseContainsGatherAndPlay() throws Exception {

        XpathEngine eng = XMLUnit.newXpathEngine();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        WelcomeServlet servlet = new WelcomeServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Gather/@action", doc), is(equalTo("/menu/show")));
        assertThat(eng.evaluate("/Response/Play/text()", doc), containsString("et-phone.mp3"));
    }
}

