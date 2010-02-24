package org.apache.camel.component.stomp;

import org.apache.camel.CamelException;
import org.apache.camel.RuntimeCamelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */

public class StompConfiguration implements Cloneable {

    private static final transient Log LOG = LogFactory.getLog(StompConfiguration.class);

    private String hostname;
    private Integer port = 61613; //default
    private String username;
    private String password;
    private String destination;
    private long attempts = 30;

    public StompConfiguration() {
    }

    public StompConfiguration(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Accepts:
     * stomp://user:pass@host:port/destination
     * stomp:destination
     * <p/>
     * Latter assumes bean is configured for rest of information.
     *
     * @param configURI
     * @throws URISyntaxException
     */
    public void configure(String configURI) throws URISyntaxException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Configuring '" + configURI + "'.");
        }

        if (configURI.startsWith("stomp://")) {

            if (configURI.substring(8).contains("/")) {
                URI uri = new URI(configURI);
                if (uri.getHost() != null) {
                    hostname = uri.getHost();
                }

                if (uri.getPort() > -1) {
                    port = uri.getPort();
                }
                destination = uri.getPath();

                String userInfo = uri.getUserInfo();
                if (userInfo != null) {
                    int idxOfPass = userInfo.indexOf(":");
                    if (idxOfPass > -1) {
                        username = userInfo.substring(0, idxOfPass);
                        password = userInfo.substring(idxOfPass + 1);
                    } else {
                        username = userInfo;
                        password = null;
                    }
                }
            } else {
                destination = configURI.substring(8);
            }
        } else if (configURI.startsWith("stomp:")) {
            destination = configURI.substring(6);
        } else {
            throw new RuntimeCamelException("Invalid stomp uri '" + configURI + "': it must start with 'stomp://' or 'stomp:'.");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("destination: " + destination);
            LOG.debug("hostname: " + hostname);
            LOG.debug("port: " + port);
            LOG.debug("username: " + username);
            LOG.debug("password: " + password);
        }
    }

    public StompConfiguration copy() {
        StompConfiguration copy = new StompConfiguration();
        copy.setDestination(destination);
        copy.setHostname(hostname);
        copy.setUsername(username);
        copy.setPassword(password);
        copy.setPort(port);
        return copy;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getMaxAttempts() {
        return attempts;
    }

    public void setMaxAttempts(long attempts) {
        this.attempts = attempts;
    }


}
