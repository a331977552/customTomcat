package org.tomcat.startup;

import org.tomcat.connector.HttpConnector;

public class Bootstrap {

	public static void main(String[] args) {
			HttpConnector connector=new HttpConnector();
			connector.start();


	}
}
