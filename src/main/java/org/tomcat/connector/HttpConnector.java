package org.tomcat.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.common.component.LifecycleListener;
import org.tomcat.connector.http.Connector;
import org.tomcat.connector.http.HttpProcessor;
import org.tomcat.connector.http.HttpRequest;
import org.tomcat.connector.http.HttpResponse;
import org.tomcat.catalina.Container;
import org.tomcat.catalina.Lifecycle;
import org.tomcat.catalina.LifecycleException;
import org.tomcat.net.ServerSocketFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HttpConnector implements Connector,Runnable, Lifecycle {

	Log log=LogFactory.getLog(HttpConnector.class);
	boolean stopped = false;
	String scheme = "http";
	private Container container;
	private boolean enableLookups;
	private ServerSocketFactory factory;
	private boolean secure;
	private List<LifecycleListener> lifecycleListeners;

	ExecutorService executorService;
	private AtomicInteger currentProcessorCount;
	private int maxProcessCount=5;
	private int minProcessCount=2;
	private Stack<HttpProcessor> processors=new Stack<>();


	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container=container;
	}

	@Override
	public boolean getEnableLookups() {
		return false;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	@Override
	public void setEnableLookups(boolean enableLookups) {
		this.enableLookups=enableLookups;
	}

	@Override
	public ServerSocketFactory getFactory() {
		return factory;
	}

	@Override
	public void setFactory(ServerSocketFactory factory) {
			this.factory=factory;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public int getRedirectPort() {
		return 0;
	}

	@Override
	public void setRedirectPort(int redirectPort) {

	}

	@Override
	public String getScheme() {
		return scheme;
	}

	@Override
	public void setScheme(String scheme) {
	this.scheme=scheme;
	}

	@Override
	public boolean getSecure() {
		return false;
	}

	@Override
	public void setSecure(boolean secure) {
		this.secure=secure;
	}



	@Override
	public ServletRequest createRequest() {
		return new HttpRequest();
	}

	@Override
	public ServletResponse createResponse() {
		return new HttpResponse();
	}



	@Override
	public void start() {
		Thread thread=new Thread(this);
		thread.start();
	}

	public HttpProcessor createHttpProcessor(){
		if(processors.size()>0){
			return processors.pop();
		}else if(currentProcessorCount.get()<maxProcessCount){
			return newProcessor();
		}else if(maxProcessCount<0)
			return newProcessor();
		else
			return null;
	}

	private HttpProcessor newProcessor() {

		int id = currentProcessorCount.incrementAndGet();

		log.debug("newProcessor id: "+id);
		HttpProcessor processor = new HttpProcessor(this,id);
		try {
			processor.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		return processor;
	}

	@Override
	public void stop() throws LifecycleException {

	}

	@Override
	public void addLifecycleListener(org.tomcat.catalina.LifecycleListener listener) {

	}

	@Override
	public org.tomcat.catalina.LifecycleListener[] findLifecycleListeners() {
		return new org.tomcat.catalina.LifecycleListener[0];
	}

	@Override
	public void removeLifecycleListener(org.tomcat.catalina.LifecycleListener listener) {

	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			log.debug("running on connector thread");
			 serverSocket=new ServerSocket(8080,1,InetAddress.getByName("localhost"));
			while(!stopped) {
				Socket socket = serverSocket.accept();
					HttpProcessor httpProcessor = createHttpProcessor();
					if(httpProcessor==null){
						try {
							socket.close();
							log.debug("too many connections.--> ignored.");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else {
						log.debug("new connection");
						httpProcessor.assign(socket);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally{
			if(serverSocket!=null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}


	@Override
	public void initialize() {

		log.debug("initialize");
		currentProcessorCount=new AtomicInteger(0);
		executorService = Executors.newFixedThreadPool(10);
		lifecycleListeners=new ArrayList<>();
		for (int i=0;i<minProcessCount;i++){
			recycle(newProcessor());
		}

	}

	public void recycle(HttpProcessor httpProcessor) {
		processors.push(httpProcessor);
	}
}
