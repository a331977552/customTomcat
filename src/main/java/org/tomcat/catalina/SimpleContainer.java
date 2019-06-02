package org.tomcat.catalina;

import org.tomcat.connector.Constants;
import org.tomcat.connector.http.HttpRequestFacade;
import org.tomcat.connector.http.HttpResponseFacade;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class SimpleContainer implements Container {
    private Loader loader;
    private Logger logger;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger=logger;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {

    }

    @Override
    public void setLoader(Loader loader) {
        this.loader=loader;
    }

    @Override
    public Loader getLoader() {
        return loader;
    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String servletName = ( (HttpServletRequest) request).getRequestURI();
        servletName = servletName.substring(servletName.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;

        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.init(null);
            servlet.service(new HttpRequestFacade((HttpServletRequest)request), new HttpResponseFacade( (HttpServletResponse)response));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public Container map(HttpServletRequest request, boolean update) {
        return null;
    }


    @Override
    public void removeChild(Container child) {

    }



    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
