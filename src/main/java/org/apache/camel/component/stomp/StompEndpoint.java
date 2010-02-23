package org.apache.camel.component.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 *
 */
public class StompEndpoint extends DefaultEndpoint {

    private static final transient Log LOG = LogFactory.getLog(StompProducer.class);

    private StompComponent stompComponent;
    private StompConfiguration config;

    public StompEndpoint(String uri, StompComponent component, StompConfiguration config) {
        super(uri, component);
        this.config = config;
    }


    @Override
    public Producer createProducer() throws Exception {
        return new StompProducer(this, createStompClient());
    }

    private Client createStompClient() {

        try {
            LOG.info("Connecting STOMP Client to: " + config.getHostname() + ":" + config.getPort());
            
            Client client = new Client(config.getHostname(), config.getPort(), config.getUsername(), config.getPassword());
            return client;

        } catch (IOException e) {
            throw new RuntimeCamelException(e);
        } catch (LoginException e) {
            throw new RuntimeCamelException(e);
        }
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public StompConfiguration getConfig() {
        return config;
    }

}
