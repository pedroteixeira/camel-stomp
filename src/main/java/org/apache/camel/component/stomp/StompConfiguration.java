package org.apache.camel.component.stomp;

import org.apache.camel.CamelException;
import org.apache.camel.RuntimeCamelException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */

public class StompConfiguration implements Cloneable {
    private String hostname;
    private Integer port = 61613; //default
    private String username;
    private String password;
    private String destination;

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

        if (configURI.startsWith("stomp://")) {
            URI uri = new URI(configURI);
            hostname = uri.getHost();
            if(uri.getPort() > -1) {
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
        } else if (configURI.startsWith("stomp:")) {
            destination = configURI.substring(6);
        } else {
            throw new RuntimeCamelException("Invalid stomp uri '" + configURI + "': it must start with 'stomp://' or 'stomp:'.");
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
}
