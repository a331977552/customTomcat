package org.tomcat.catalina.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tomcat.catalina.Container;
import org.tomcat.catalina.Mapper;
import org.tomcat.catalina.Wrapper;
import org.tomcat.catalina.context.SimpleContext;

public class SimpleContextMapper implements Mapper {

  Log log= LogFactory.getLog(SimpleContextMapper.class);
  /**
   * The Container with which this Mapper is associated.
   */
  private SimpleContext context = null;

  public Container getContainer() {
    return (context);
  }

  public void setContainer(Container container) {
    if (!(container instanceof SimpleContext))
      throw new IllegalArgumentException
        ("Illegal type of container");
    context = (SimpleContext) container;
  }

  public String getProtocol() {
    return null;
  }

  public void setProtocol(String protocol) {
  }


  /**
   * Return the child Container that should be used to process this Request,
   * based upon its characteristics.  If no such child Container can be
   * identified, return <code>null</code> instead.
   *
   * @param request Request being processed
   * @param update Update the Request to reflect the mapping selection?
   *
   * @exception IllegalArgumentException if the relative portion of the
   *  path cannot be URL decoded
   */
  public Container map(HttpServletRequest request, boolean update) {
    log.debug("map----");
    // Identify the context-relative URI to be mapped
    String contextPath =
            request.getContextPath();
    String requestURI = request.getRequestURI();
    String relativeURI = requestURI.substring(contextPath.length());

    // Apply the standard request URI mapping rules from the specification
    Wrapper wrapper = null;
    String servletPath = relativeURI;
    String pathInfo = null;
    String name = context.findServletMapping(relativeURI);
    if (name != null)
      wrapper = (Wrapper) context.findChild(name);
    return (wrapper);
  }
}