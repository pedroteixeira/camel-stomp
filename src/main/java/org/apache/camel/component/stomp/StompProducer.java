package org.apache.camel.component.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 *
 */
public class StompProducer extends DefaultProducer {

    private static final transient Log LOG = LogFactory.getLog(StompProducer.class);

    private StompConfiguration config;
    private StompEndpoint endpoint;                     

    public StompProducer(StompEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        this.config = endpoint.getConfig();
    }

    /**
     * Lock.
     * 
     * @param exchange
     * @throws Exception
     */
    @Override
    public synchronized void process(final Exchange exchange) throws Exception {
        final String msg = exchange.getIn().getBody(String.class);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending to " + config.getDestination() + " msg: " + msg + " | " + exchange.getIn().getHeaders());
        }
        
        try {
            endpoint.getClient().send(config.getDestination(), msg, exchange.getIn().getHeaders()); 
        } catch (Exception e) {
            LOG.error("Error sending stomp message: " + msg, e);
        }

    }


    @Override
    protected void doStart() throws Exception {
        super.doStart();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        endpoint.disconnect();
    }
}
