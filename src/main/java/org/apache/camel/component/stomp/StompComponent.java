package org.apache.camel.component.stomp;

import net.ser1.stomp.Client;
import org.apache.camel.CamelContext;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultComponent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class StompComponent extends DefaultComponent {


    private StompConfiguration config;

    public StompComponent() {
        this.config = new StompConfiguration();
    }

    public StompComponent(StompConfiguration configuration) {
        this.config = configuration;
    }

    public StompComponent(CamelContext context) {
        super(context);
        this.config = new StompConfiguration();
    }

    @Override
    protected StompEndpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        StompConfiguration config = this.config.copy();
        config.configure(uri);

        StompEndpoint endpoint = new StompEndpoint(uri, this, config);
        setProperties(config, parameters);
        return endpoint;
    }


    public void setConfig(StompConfiguration config) {
        this.config = config;
    }


}
