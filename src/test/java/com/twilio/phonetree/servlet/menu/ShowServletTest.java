package com.twilio.phonetree.servlet.menu;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.twilio.phonetree.ContentTypeVerifier.verifyThatContentTypeIsXml;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ShowServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    private StringWriter stringWriter;

    private PrintWriter printWriter;

    private XpathEngine eng = XMLUnit.newXpathEngine();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void whenSelectedOptionIs1ThenResponseContainsSayAndHangup()
            throws IOException, SAXException, XpathException {

        when(request.getParameter("Digits")).thenReturn("1");

        ShowServlet servlet = new ShowServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Say/text()", doc),
                containsString("your extraction point"));
        assertThat(eng.evaluate("/Response/Hangup", doc), is(notNullValue()));
    }

    @Test
    public void whenSelectedOptionIs2ThenResponseContainsGatherAndSay()
            throws IOException, XpathException, SAXException {

        when(request.getParameter("Digits")).thenReturn("2");

        ShowServlet servlet = new ShowServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Say/text()", doc),
                containsString("call the planet Broh"));
        assertThat(eng.evaluate("/Response/Gather/@action", doc),
                is(equalTo("/commuter/connect")));
    }

    @Test
    @Parameters({"3", "4"})
    public void whenSelectedOptionIsNot1or2ThenResponseRedirectsToWelcome(String digits)
            throws IOException, SAXException, XpathException {

        when(request.getParameter("Digits")).thenReturn(digits);

        ShowServlet servlet = new ShowServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Dial", doc), is(equalTo("")));
        assertThat(eng.evaluate("/Response/Redirect", doc), is(equalTo("/irv/welcome")));
    }
}

