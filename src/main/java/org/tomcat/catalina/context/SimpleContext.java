package org.tomcat.catalina.context;


import java.awt.event.ContainerListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.directory.DirContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tomcat.catalina.*;
import org.tomcat.catalina.deploy.*;
import org.tomcat.catalina.pippleline.SimplePipeline;
import org.tomcat.catalina.valve.SimpleContextValve;
import org.tomcat.util.CharsetMapper;


public class SimpleContext implements Context, Pipeline,Lifecycle {

  private boolean started;
  private Logger logger;
  private Manager manager;
  private Cluster cluster;
  private String name;
  private ClassLoader parentClassLoader;
  private DirContext resources;

  public SimpleContext() {
    pipeline.setBasic(new SimpleContextValve());
  }

  protected HashMap<String,Container> children = new HashMap<>();
  protected Loader loader = null;
  protected SimplePipeline pipeline = new SimplePipeline(this);
  protected Map<String, String> servletMappings = new HashMap<>();
  protected Mapper mapper = null;
  protected HashMap<String,Mapper> mappers = new HashMap();
  private Container parent = null;

  public Object[] getApplicationListeners() {
    return null;
  }

  public void setApplicationListeners(Object listeners[]) {
  }

  public boolean getAvailable() {
    return false;
  }

  public void setAvailable(boolean flag) {
  }

  public CharsetMapper getCharsetMapper() {
    return null;
  }

  public void setCharsetMapper(CharsetMapper mapper) {
  }

  public boolean getConfigured() {
    return false;
  }

  public void setConfigured(boolean configured) {
  }

  public boolean getCookies() {
    return false;
  }

  public void setCookies(boolean cookies) {
  }

  public boolean getCrossContext() {
    return false;
  }

  public void setCrossContext(boolean crossContext) {
  }

  public String getDisplayName() {
    return null;
  }

  public void setDisplayName(String displayName) {
  }

  public boolean getDistributable() {
    return false;
  }

  public void setDistributable(boolean distributable) {
  }

  public String getDocBase() {
    return null;
  }

  public void setDocBase(String docBase) {
  }

  public LoginConfig getLoginConfig() {
    return null;
  }

  public void setLoginConfig(LoginConfig config) {
  }

  public NamingResources getNamingResources() {
    return null;
  }

  public void setNamingResources(NamingResources namingResources) {
  }

  public String getPath() {
    return null;
  }

  public void setPath(String path) {
  }

  public String getPublicId() {
    return null;
  }

  public void setPublicId(String publicId) {
  }

  public boolean getReloadable() {
    return false;
  }

  public void setReloadable(boolean reloadable) {
  }

  public boolean getOverride() {
    return false;
  }

  public void setOverride(boolean override) {
  }

  public boolean getPrivileged() {
    return false;
  }

  public void setPrivileged(boolean privileged) {
  }

  public ServletContext getServletContext() {
    return null;
  }

  public int getSessionTimeout() {
    return 0;
  }

  public void setSessionTimeout(int timeout) {
  }

  public String getWrapperClass() {
    return null;
  }

  public void setWrapperClass(String wrapperClass) {
  }

  public void addApplicationListener(String listener) {
  }

  public void addApplicationParameter(ApplicationParameter parameter) {
  }

  public void addConstraint(SecurityConstraint constraint) {
  }

  public void addEjb(ContextEjb ejb) {
  }

  public void addEnvironment(ContextEnvironment environment) {
  }

  public void addErrorPage(ErrorPage errorPage) {
  }

  public void addFilterDef(FilterDef filterDef) {
  }

  public void addFilterMap(FilterMap filterMap) {
  }

  public void addInstanceListener(String listener) {
  }

  public void addLocalEjb(ContextLocalEjb ejb) {
  }

  public void addMimeMapping(String extension, String mimeType) {
  }

  public void addParameter(String name, String value) {
  }

  public void addResource(ContextResource resource) {
  }

  public void addResourceEnvRef(String name, String type) {
  }

  public void addResourceLink(ContextResourceLink resourceLink) {
  }

  public void addRoleMapping(String role, String link) {
  }

  public void addSecurityRole(String role) {
  }

  public void addServletMapping(String pattern, String name) {
    synchronized (servletMappings) {
      servletMappings.put(pattern, name);
    }
  }

  public void addTaglib(String uri, String location) {
  }

  public void addWelcomeFile(String name) {
  }

  public void addWrapperLifecycle(String listener) {
  }

  public void addWrapperListener(String listener) {
  }

  public Wrapper createWrapper() {
    return null;
  }

  public String[] findApplicationListeners() {
    return null;
  }

  public ApplicationParameter[] findApplicationParameters() {
    return null;
  }

  public SecurityConstraint[] findConstraints() {
    return null;
  }

  public ContextEjb findEjb(String name) {
    return null;
  }

  public ContextEjb[] findEjbs() {
    return null;
  }

  public ContextEnvironment findEnvironment(String name) {
    return null;
  }

  public ContextEnvironment[] findEnvironments() {
    return null;
  }

  public ErrorPage findErrorPage(int errorCode) {
    return null;
  }

  public ErrorPage findErrorPage(String exceptionType) {
    return null;
  }

  public ErrorPage[] findErrorPages() {
    return null;
  }

  public FilterDef findFilterDef(String filterName) {
    return null;
  }

  public FilterDef[] findFilterDefs() {
    return null;
  }

  public FilterMap[] findFilterMaps() {
    return null;
  }

  public String[] findInstanceListeners() {
    return null;
  }

  public ContextLocalEjb findLocalEjb(String name) {
    return null;
  }

  public ContextLocalEjb[] findLocalEjbs() {
    return null;
  }

  public String findMimeMapping(String extension) {
    return null;
  }

  public String[] findMimeMappings() {
    return null;
  }

  public String findParameter(String name) {
    return null;
  }

  public String[] findParameters() {
    return null;
  }

  public ContextResource findResource(String name) {
    return null;
  }

  public String findResourceEnvRef(String name) {
    return null;
  }

  public String[] findResourceEnvRefs() {
    return null;
  }

  public ContextResourceLink findResourceLink(String name) {
    return null;
  }

  public ContextResourceLink[] findResourceLinks() {
    return null;
  }

  public ContextResource[] findResources() {
    return null;
  }

  public String findRoleMapping(String role) {
    return null;
  }

  public boolean findSecurityRole(String role) {
    return false;
  }

  public String[] findSecurityRoles() {
    return null;
  }

  public String findServletMapping(String pattern) {
    synchronized (servletMappings) {
      return ((String) servletMappings.get(pattern));
    }
  }

  public String[] findServletMappings() {
    return null;
  }

  public String findStatusPage(int status) {
    return null;
  }

  public int[] findStatusPages() {
    return null;
  }

  public String findTaglib(String uri) {
    return null;
  }

  public String[] findTaglibs() {
    return null;
  }

  public boolean findWelcomeFile(String name) {
    return false;
  }

  public String[] findWelcomeFiles() {
    return null;
  }

  public String[] findWrapperLifecycles() {
    return null;
  }

  public String[] findWrapperListeners() {
    return null;
  }

  public void reload() {
  }

  public void removeApplicationListener(String listener) {
  }

  public void removeApplicationParameter(String name) {
  }

  public void removeConstraint(SecurityConstraint constraint) {
  }

  public void removeEjb(String name) {
  }

  public void removeEnvironment(String name) {
  }

  public void removeErrorPage(ErrorPage errorPage) {
  }

  public void removeFilterDef(FilterDef filterDef) {
  }

  public void removeFilterMap(FilterMap filterMap) {
  }

  public void removeInstanceListener(String listener) {
  }

  public void removeLocalEjb(String name) {
  }

  public void removeMimeMapping(String extension) {
  }

  public void removeParameter(String name) {
  }

  public void removeResource(String name) {
  }

  public void removeResourceEnvRef(String name) {
  }

  public void removeResourceLink(String name) {
  }

  public void removeRoleMapping(String role) {
  }

  public void removeSecurityRole(String role) {
  }

  public void removeServletMapping(String pattern) {
  }

  public void removeTaglib(String uri) {
  }

  public void removeWelcomeFile(String name) {
  }

  public void removeWrapperLifecycle(String listener) {
  }

  public void removeWrapperListener(String listener) {
  }


  //methods of the Container interface
  public String getInfo() {
    return null;
  }

  public Loader getLoader() {
    if (loader != null)
      return (loader);
    if (parent != null)
      return (parent.getLoader());
    return (null);
  }

  public void setLoader(Loader loader) {
    this.loader = loader;
  }

  public Logger getLogger() {
    return logger;
  }

  public void setLogger(Logger logger) {
    this.logger=logger;
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager=manager;
  }

  public Cluster getCluster() {
    return cluster;
  }

  public void setCluster(Cluster cluster) {
    this.cluster=cluster;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name=name;
  }

  public Container getParent() {
    return parent;
  }

  public void setParent(Container container) {
  this.parent=container;
  }

  public ClassLoader getParentClassLoader() {
    return parentClassLoader;
  }

  public void setParentClassLoader(ClassLoader parent) {
    this.parentClassLoader=parent;
  }


  public DirContext getResources() {
    return resources;
  }

  public void setResources(DirContext resources) {
    this.resources=resources;
  }

  public void addChild(Container child) {
    child.setParent(this);
    children.put(child.getName(), child);
  }

  public void addContainerListener(ContainerListener listener) {

  }

  public void addMapper(Mapper mapper) {
    // this method is adopted from addMapper in ContainerBase
    // the first mapper added becomes the default mapper
    mapper.setContainer(this);      // May throw IAE
    this.mapper = mapper;
    synchronized(mappers) {
      if (mappers.get(mapper.getProtocol()) != null)
        throw new IllegalArgumentException("addMapper:  Protocol '" +
          mapper.getProtocol() + "' is not unique");
      mapper.setContainer((Container) this);      // May throw IAE
      mappers.put(mapper.getProtocol(), mapper);
      if (mappers.size() == 1)
        this.mapper = mapper;
      else
        this.mapper = null;
    }
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }

  public Container findChild(String name) {
    if (name == null)
      return (null);
    synchronized (children) {       // Required by post-start changes
      return ((Container) children.get(name));
    }
  }

  public Container[] findChildren() {
    synchronized (children) {
      Container results[] = new Container[children.size()];
      return ((Container[]) children.values().toArray(results));
    }
  }

  @Override
  public void invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    pipeline.invoke(request, response);
  }


  public ContainerListener[] findContainerListeners() {
    return null;
  }

  public Mapper findMapper(String protocol) {
    // the default mapper will always be returned, if any,
    // regardless the value of protocol
    if (mapper != null)
      return (mapper);
    else
      synchronized (mappers) {
        return ((Mapper) mappers.get(protocol));
      }
  }

  public Mapper[] findMappers() {
    return (Mapper[]) mappers.values().toArray();
  }



  public Container map(HttpServletRequest request, boolean update) {
    //this method is taken from the map method in org.apache.cataline.core.ContainerBase
    //the findMapper method always returns the default mapper, if any, regardless the
    //request's protocol
    Mapper mapper = findMapper(request.getProtocol());
    if (mapper == null)
      return (null);

    // Use this Mapper to perform this mapping
    return (mapper.map(request, update));
  }

  public void removeChild(Container child) {
    children.remove(child.getName());
  }

  public void removeContainerListener(ContainerListener listener) {

  }

  public void removeMapper(Mapper mapper) {
    mappers.remove(mapper.getProtocol());
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }

  // method implementations of Pipeline
  public Valve getBasic() {
    return pipeline.getBasic();
  }

  public void setBasic(Valve valve) {
    pipeline.setBasic(valve);
  }

  public synchronized void addValve(Valve valve) {
    pipeline.addValve(valve);
  }

  public Valve[] getValves() {
    return pipeline.getValves();
  }

  public void removeValve(Valve valve) {
    pipeline.removeValve(valve);
  }

  @Override
  public void start() throws LifecycleException {
    if(started)
    {
      throw new LifecycleException("SimpleContext has been started");
    }
    started=true;
    lifecycleSupport.fireLifecycleEvent(Lifecycle.BEFORE_START_EVENT,null);

    if ((pipeline != null) && (pipeline instanceof Lifecycle))
      ((Lifecycle) pipeline).start();
    if ((loader != null) && (loader instanceof Lifecycle))
      ((Lifecycle) loader).start();
    if ((logger != null) && (logger instanceof Lifecycle))
      ((Lifecycle) logger).start();
    if ((manager != null) && (manager instanceof Lifecycle))
      ((Lifecycle) manager).start();
    if ((cluster != null) && (cluster instanceof Lifecycle))
      ((Lifecycle) cluster).start();
  /*  if ((realm != null) && (realm instanceof Lifecycle))
      ((Lifecycle) realm).start();*/
    if ((resources != null) && (resources instanceof Lifecycle))
      ((Lifecycle) resources).start();
    Collection<Container> values = children.values();
    for (Container value: values) {
      if (value instanceof Lifecycle)
        ((Lifecycle)value).start();
    }
    lifecycleSupport.fireLifecycleEvent(Lifecycle.START_EVENT,null);
    lifecycleSupport.fireLifecycleEvent(Lifecycle.AFTER_START_EVENT,null);
  }

  LifecycleSupport lifecycleSupport=new LifecycleSupport(this);

  @Override
  public void stop() throws LifecycleException {
    lifecycleSupport.fireLifecycleEvent(Lifecycle.BEFORE_STOP_EVENT,null);

    // Validate and update our current component state
    if (!started)
      throw new LifecycleException("containerBase.notStarted");
    started = false;

    // Stop the Valves in our pipeline (including the basic), if any
    if (pipeline instanceof Lifecycle) {
      ((Lifecycle) pipeline).stop();
    }

    // Stop our child containers, if any
    Container children[] = findChildren();
    for (int i = 0; i < children.length; i++) {
      if (children[i] instanceof Lifecycle)
        ((Lifecycle) children[i]).stop();
    }

    // Stop our Mappers, if any
    Mapper mappers[] = findMappers();
    for (int i = 0; i < mappers.length; i++) {
      if (mappers[(mappers.length-1)-i] instanceof Lifecycle)
        ((Lifecycle) mappers[(mappers.length-1)-i]).stop();
    }

    // Stop our subordinate components, if any
    if ((resources != null) && (resources instanceof Lifecycle)) {
      ((Lifecycle) resources).stop();
    }
/*    if ((realm != null) && (realm instanceof Lifecycle)) {
      ((Lifecycle) realm).stop();
    }*/
    if ((cluster != null) && (cluster instanceof Lifecycle)) {
      ((Lifecycle) cluster).stop();
    }
    if ((manager != null) && (manager instanceof Lifecycle)) {
      ((Lifecycle) manager).stop();
    }
    if ((logger != null) && (logger instanceof Lifecycle)) {
      ((Lifecycle) logger).stop();
    }
    if ((loader != null) && (loader instanceof Lifecycle)) {
      ((Lifecycle) loader).stop();
    }

    // Notify our interested LifecycleListeners
  lifecycleSupport.fireLifecycleEvent(Lifecycle.START_EVENT,null);
    lifecycleSupport.fireLifecycleEvent(Lifecycle.AFTER_STOP_EVENT,null);
  }


  @Override
  public void addLifecycleListener(LifecycleListener listener) {
    lifecycleSupport.addLifecycleListener(listener);
  }

  @Override
  public LifecycleListener[] findLifecycleListeners() {
    return lifecycleSupport.findLifecycleListeners();
  }

  @Override
  public void removeLifecycleListener(LifecycleListener listener) {
    lifecycleSupport.removeLifecycleListener(listener);
  }
}