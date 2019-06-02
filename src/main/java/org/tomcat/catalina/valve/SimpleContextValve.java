package org.tomcat.catalina.valve;

import org.tomcat.catalina.*;
import org.tomcat.connector.http.HttpResponse;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SimpleContextValve implements Valve, Contained {

  protected Container container;

  public void invoke(HttpServletRequest request, HttpServletResponse response, ValveContext valveContext)
    throws IOException, ServletException {

/*
    // Validate the request and response object types
    if (!(request.getRequest() instanceof HttpServletRequest) ||
      !(response.getResponse() instanceof HttpServletResponse)) {
      return;     // NOTE - Not much else we can do generically
    }
*/

    // Disallow any direct access to resources under WEB-INF or META-INF
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI();
    String relativeURI =
      requestURI.substring(contextPath.length()).toUpperCase();

    Context context = (Context) getContainer();
    // Select the Wrapper to be used for this Request
    Wrapper wrapper = null;
    try {
      wrapper = (Wrapper) context.map(request, true);
    }
    catch (IllegalArgumentException e) {
      badRequest(requestURI,response);
      return;
    }
    if (wrapper == null) {
      notFound(requestURI, response);
      return;
    }
    if(response instanceof HttpResponse){
      ((HttpResponse)response).setContext(context);

    }
    // Ask this Wrapper to process this Request
    wrapper.invoke(request, response);
  }

  public String getInfo() {
    return null;
  }

  public Container getContainer() {
    return container;
  }

  public void setContainer(Container container) {
    this.container = container;
  }

  private void badRequest(String requestURI, HttpServletResponse response) {
    try {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
    }
    catch (IllegalStateException e) {
      ;
    }
    catch (IOException e) {
      ;
    }
  }

  private void notFound(String requestURI, HttpServletResponse response) {
    try {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
    }
    catch (IllegalStateException e) {
      ;
    }
    catch (IOException e) {
      ;
    }
  }

}