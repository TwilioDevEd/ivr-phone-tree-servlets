package com.twilio.phonetree.servlet.ivr;

import com.twilio.phonetree.TwilioServletTest;
import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class WelcomeServletTest extends TwilioServletTest {

    @Mock HttpServletRequest request;

    @Mock HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void responseContainsGatherAndPlay() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        WelcomeServlet servlet = new WelcomeServlet();
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getAttributeValue(document, "Gather", "action"), is(equalTo("/menu/show")));
        assertThat(getElement(document, "Gather/Play").getValue(), is(CoreMatchers.<String>notNullValue()));
    }
}
