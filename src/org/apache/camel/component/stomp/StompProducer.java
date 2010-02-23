package net.intelie.holmes.core.camel.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * 
 */
public class StompProducer extends DefaultProducer {

    public StompProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        final org.apache.camel.Message in = exchange.getIn();

    }


}
