package org.tomcat.catalina.loader;

import org.tomcat.catalina.*;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class WebappClassloader extends URLClassLoader implements Reloader, Lifecycle {


    public WebappClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public WebappClassloader(URL[] urls) {
        super(urls);
    }

    public WebappClassloader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public WebappClassloader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public WebappClassloader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }

    @Override
    public void addRepository(String repository) {

    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }
}
