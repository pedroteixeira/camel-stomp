package net.intelie.holmes.core.camel.stomp;

import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

/**
 *
 */
public class StompComponent extends DefaultComponent  {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return null;
    }
}
