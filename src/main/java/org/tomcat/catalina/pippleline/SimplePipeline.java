package org.tomcat.catalina.pippleline;

import org.elasticsearch.common.component.LifecycleListener;
import org.tomcat.catalina.*;
import org.tomcat.catalina.Lifecycle;
import org.tomcat.catalina.LifecycleException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SimplePipeline implements Pipeline, Lifecycle,Contained {

  public SimplePipeline(Container container) {
    setContainer(container);
  }

  // The basic Valve (if any) associated with this Pipeline.
  protected Valve basic = null;
  // The Container with which this Pipeline is associated.
  protected Container container = null;
  // the array of Valves
  protected Valve valves[] = new Valve[0];

  @Override
  public Container getContainer() {
    return container;
  }
  @Override
  public void setContainer(Container container) {
    this.container = container;
  }

  public Valve getBasic() {
    return basic;
  }

  public void setBasic(Valve valve) {
    this.basic = valve;
    ((Contained) valve).setContainer(container);
  }

  public void addValve(Valve valve) {
    if (valve instanceof Contained)
      ((Contained) valve).setContainer(this.container);

    synchronized (valves) {
      Valve results[] = new Valve[valves.length +1];
      System.arraycopy(valves, 0, results, 0, valves.length);
      results[valves.length] = valve;
      valves = results;
    }
  }

  public Valve[] getValves() {
    return valves;
  }

  public void invoke(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    // Invoke the first Valve in this pipeline for this request
   (new StandardPipelineValveContext()).invokeNext(request, response);
  }

  public void removeValve(Valve valve) {
  }

  // implementation of the Lifecycle interface's methods
  public void addLifecycleListener(org.tomcat.catalina.LifecycleListener listener) {
  }

  public org.tomcat.catalina.LifecycleListener[] findLifecycleListeners() {
    return null;
  }

  @Override
  public void removeLifecycleListener(org.tomcat.catalina.LifecycleListener listener) {

  }

  public void removeLifecycleListener(LifecycleListener listener) {
  }

  public  void start() throws LifecycleException {
    System.out.println( toString()+" start");
  }

  public void stop() throws LifecycleException {
    System.out.println("simple pipeline stop");
  }



  // this class is copied from org.apache.catalina.core.StandardPipeline class's
  // StandardPipelineValveContext inner class.
  protected class StandardPipelineValveContext implements ValveContext {
    protected int stage = 0;
    public String getInfo() {
      return null;
    }

    public void invokeNext(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      int subscript = stage;
      stage = stage + 1;
      // Invoke the requested Valve for the current request thread
      if (subscript < valves.length) {
        valves[subscript].invoke(request, response, this);
      }
      else if ((subscript == valves.length) && (basic != null)) {
        basic.invoke(request, response, this);
      }
      else {
        throw new ServletException("No valve");
      }
    }
  } // end of inner class

}