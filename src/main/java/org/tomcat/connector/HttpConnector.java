package org.tomcat.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.tomcat.connector.http.HttpProcessor;

public class HttpConnector implements Runnable {

	boolean stopped = false;
	String scheme = "http";
	public String getScheme() {
		return scheme;
	}

	public void start() {
		Thread thread=new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			 serverSocket=new ServerSocket(8080,1,InetAddress.getByName("localhost"));
			while(!stopped) {
				Socket socket = serverSocket.accept();
				HttpProcessor httpProcessor=new HttpProcessor(this);
				httpProcessor.process(socket);
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

}
