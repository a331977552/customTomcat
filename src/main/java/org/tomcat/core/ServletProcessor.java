package org.tomcat.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;

import org.tomcat.connector.Constants;
import org.tomcat.connector.http.HttpRequest;
import org.tomcat.connector.http.HttpRequestFacade;
import org.tomcat.connector.http.HttpResponse;
import org.tomcat.connector.http.HttpResponseFacade;

public class ServletProcessor {

	public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
		String servletName = httpRequest.getRequestURI().substring(httpRequest.getRequestURI().lastIndexOf("/") + 1,
				httpRequest.getRequestURI().length());
		URLClassLoader loader = null;
		try {
			// create a URLClassLoader
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Constants.WEB_ROOT+File.separator+"servlet");
			// the forming of repository is taken from the
			// createClassLoader method in
			// org.apache.catalina.startup.ClassLoaderFactory
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			// the code for forming the URL is taken from
			// the addRepository method in
			// org.apache.catalina.loader.StandardClassLoader.
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class myClass = null;
		try {
			myClass = loader.loadClass("org.myhandler."+servletName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) myClass.newInstance();
			servlet.service(new HttpRequestFacade(httpRequest), new HttpResponseFacade(httpResponse));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
