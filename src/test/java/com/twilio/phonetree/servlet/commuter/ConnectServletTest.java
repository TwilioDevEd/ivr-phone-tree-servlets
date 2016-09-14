package com.twilio.phonetree.servlet.commuter;

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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ConnectServletTest {

    @Mock private HttpServletRequest request;

    @Mock private HttpServletResponse response;

    private PrintWriter printWriter;

    private StringWriter stringWriter;

    private XpathEngine eng = XMLUnit.newXpathEngine();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    @Parameters({"2", "3", "4"})
    public void whenSelectedOptionIsMappedToAPhoneNumberThenResponseContainsDial(String digits)
            throws IOException, SAXException, XpathException {

        when(request.getParameter("Digits")).thenReturn(digits);

        ConnectServlet servlet = new ConnectServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());


        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Dial", doc), is(notNullValue()));
    }

    @Test
    @Parameters({"1", "5"})
    public void whenSelectedOptionIsNotMappedToAPhoneNumberThenRedirectsToWelcome(String digits)
            throws IOException, SAXException, XpathException {

        when(request.getParameter("Digits")).thenReturn(digits);

        ConnectServlet servlet = new ConnectServlet();
        servlet.doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        verifyThatContentTypeIsXml(response);
        assertThat(eng.evaluate("/Response/Dial", doc), is(equalTo("")));
        assertThat(eng.evaluate("/Response/Redirect/text()", doc), is(equalTo("/irv/welcome")));
    }
}

