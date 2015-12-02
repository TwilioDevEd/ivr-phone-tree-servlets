package com.twilio.phonetree.servlet.menu;

import com.twilio.phonetree.TwilioServletTest;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class ShowServletTest extends TwilioServletTest {

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
    public void whenSelectedOptionIs1ThenResponseContainsSayAndHangup() throws IOException, JDOMException {

        when(request.getParameter("digits")).thenReturn("1");

        ShowServlet servlet = new ShowServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Say").getValue(), is(CoreMatchers.<String>notNullValue()));
        assertThat(getElement(document, "Hangup").getValue(), is(CoreMatchers.<String>notNullValue()));
    }

    @Test
    public void whenSelectedOptionIs2ThenResponseContainsGatherAndSay() throws IOException, JDOMException {

        when(request.getParameter("digits")).thenReturn("2");

        ShowServlet servlet = new ShowServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Gather/Say").getValue(), is(CoreMatchers.<String>notNullValue()));
        assertThat(getAttributeValue(document, "Gather", "action"), is(equalTo("/")));
    }
}