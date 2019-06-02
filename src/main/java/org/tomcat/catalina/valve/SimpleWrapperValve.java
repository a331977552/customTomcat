package org.tomcat.catalina.valve;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tomcat.catalina.Contained;
import org.tomcat.catalina.Container;
import org.tomcat.catalina.Valve;
import org.tomcat.catalina.ValveContext;
import org.tomcat.catalina.wrapper.SimpleWrapper;


public class SimpleWrapperValve implements Valve, Contained {

  protected Container container;

  public void invoke(HttpServletRequest request, HttpServletResponse response, ValveContext valveContext)
    throws IOException, ServletException {

    SimpleWrapper wrapper = (SimpleWrapper) getContainer();

    Servlet servlet = null;



    // Allocate a servlet instance to process this request
    try {
      servlet = wrapper.allocate();
      if (request!=null && response!=null) {
        servlet.service(request, response);
      }
      else {
        servlet.service(request, response);
      }
    }
    catch (ServletException e) {
      e.printStackTrace();
    }
  }

  public String getInfo() {
    return "this is a simple servlet level valve";
  }

  public Container getContainer() {
    return container;
  }

  public void setContainer(Container container) {
    this.container = container;
  }
}