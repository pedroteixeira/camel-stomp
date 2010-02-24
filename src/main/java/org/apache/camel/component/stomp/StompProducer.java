package org.apache.camel.component.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class StompProducer extends DefaultProducer {

    private static final transient Log LOG = LogFactory.getLog(StompProducer.class);

    private Client client;
    private StompConfiguration config;

    public StompProducer(StompEndpoint endpoint, Client client) {
        super(endpoint);
        this.client = client;
        this.config = endpoint.getConfig();
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        final String msg = exchange.getIn().getBody(String.class);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending to " + config.getDestination() + " msg: " + msg + " | " + exchange.getIn().getHeaders());
        }

        client.send(config.getDestination(), msg, exchange.getIn().getHeaders());
    }


}
