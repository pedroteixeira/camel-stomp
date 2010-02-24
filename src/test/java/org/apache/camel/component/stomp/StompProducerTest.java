package org.apache.camel.component.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class StompProducerTest extends CamelTestSupport {

    @Test
    @Ignore("Manual test")
    public void testClientFailover() throws Exception {
        StompEndpoint stompEndpoint = resolveMandatoryEndpoint("stomp://localhost:7000/queue/testestomp", StompEndpoint.class);
        Client c = stompEndpoint.getClient();
    }

    @Test
    @Ignore("Manual test")
    public void testStompSend() throws Exception {
        MockEndpoint mockEndpoint = resolveMandatoryEndpoint("mock:results", MockEndpoint.class);
        mockEndpoint.expectedMessageCount(1);

        template.sendBody("direct:start", "echo hello world");

        mockEndpoint.assertIsSatisfied();
        List<Exchange> list = mockEndpoint.getReceivedExchanges();
        Exchange exchange = list.get(0);
        assertNotNull("exchange", exchange);

        Message in = exchange.getIn();
        assertNotNull("in", in);

        Map<String, Object> headers = in.getHeaders();

        log.debug("Headers: " + headers);
        String body = in.getBody(String.class);

        log.debug("Body: " + body);
        assertNotNull("Should have a body!", body);

    }

    @Test
    public void testSimpleURI() throws Exception {
        StompEndpoint stompEndpoint = resolveMandatoryEndpoint("stomp:/queue/testestomp", StompEndpoint.class);
        assertEquals("/queue/testestomp", stompEndpoint.getConfig().getDestination());
    }

    @Test
    public void testURIWithParameters() throws Exception {
        StompEndpoint stompEndpoint = resolveMandatoryEndpoint("stomp://localhost:61613/queue/testestomp", StompEndpoint.class);
        assertEquals("/queue/testestomp", stompEndpoint.getConfig().getDestination());
        assertEquals("localhost", stompEndpoint.getConfig().getHostname());
        assertEquals(new Integer(61613), stompEndpoint.getConfig().getPort());
    }


    @Test
    public void shouldUseDefaultPortWhenNotSpecifying() throws Exception {
        StompEndpoint stompEndpointNoPort = resolveMandatoryEndpoint("stomp://localhost/queue/testestomp", StompEndpoint.class);
        assertEquals(new Integer(61613), stompEndpointNoPort.getConfig().getPort());
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .to("stomp://localhost:61613/queue/testestomp")
                        .to("mock:results");

                from("direct:start2")
                        .to("stomp:/queue/testestomp")
                        .to("mock:results2");
            }
        };
    }
}
