package org.tomcat.catalina;

public class LifecycleException extends RuntimeException {

    public LifecycleException() {
    }

    public LifecycleException(String message) {
        super(message);
    }
}
