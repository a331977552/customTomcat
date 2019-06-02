package org.tomcat.catalina.valve;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tomcat.catalina.Contained;
import org.tomcat.catalina.Container;
import org.tomcat.catalina.Valve;
import org.tomcat.catalina.ValveContext;


public class ClientIPLoggerValve implements Valve, Contained {

  protected Container container;

  public void invoke(HttpServletRequest request, HttpServletResponse response, ValveContext valveContext)
    throws IOException, ServletException {

    // Pass this request on to the next valve in our pipeline
    valveContext.invokeNext(request, response);
    System.out.println("Client IP Logger Valve");
    System.out.println(request.getRemoteAddr());
    System.out.println("------------------------------------");
  }

  public String getInfo() {
    return "ClientIPLoggerValve";
  }

  public Container getContainer() {
    return container;
  }

  public void setContainer(Container container) {
    this.container = container;
  }

}