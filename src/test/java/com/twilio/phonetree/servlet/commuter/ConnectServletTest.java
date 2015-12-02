package com.twilio.phonetree.servlet.commuter;

import com.twilio.phonetree.TwilioServletTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ConnectServletTest extends TwilioServletTest {

    @Mock HttpServletRequest request;

    @Mock HttpServletResponse response;

    ByteArrayOutputStream output;

    PrintWriter printWriter;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        output = new ByteArrayOutputStream();
        printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    @Parameters({"2", "3", "4"})
    public void whenSelectedOptionIs_2_3_4_ThenResponseContainsDial(String digits)
            throws IOException, JDOMException {

        when(request.getParameter("Digits")).thenReturn(digits);

        ConnectServlet servlet = new ConnectServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Dial").getValue(), is(CoreMatchers.<String>notNullValue()));
    }

    @Test
    @Parameters({"1", "5"})
    public void whenSelectedOptionIsNot_2_3_4_ThenResponseRedirectsToWelcome(String digits)
            throws IOException, JDOMException {

        when(request.getParameter("Digits")).thenReturn(digits);

        ConnectServlet servlet = new ConnectServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Dial"), is(CoreMatchers.<Element>nullValue()));
        assertThat(getElement(document, "Redirect").getValue(), is("/ivr/welcome"));
    }
}