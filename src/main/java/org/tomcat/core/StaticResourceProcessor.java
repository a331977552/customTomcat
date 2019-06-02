package org.tomcat.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.tomcat.connector.Constants;
import org.tomcat.connector.http.HttpRequest;
import org.tomcat.connector.http.HttpResponse;

public class StaticResourceProcessor {

	private FileInputStream fis;
	private static final int BUFFER_SIZE=2048;
	public void process(HttpRequest request, HttpResponse response) {
		sendStaticResource(request,response);
	}
	public void sendStaticResource(HttpRequest request, HttpResponse response) {
		ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream(BUFFER_SIZE);
		byte[] buf=new byte[BUFFER_SIZE];
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String uri = request.getRequestURI();
		if (uri.equals("/")) {
			uri = "index.html";
		}
		File file = new File(Constants.WEB_ROOT, uri);
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch ;
				while ((ch=fis.read(buf, 0, BUFFER_SIZE)) != -1) {
					arrayOutputStream.write(buf,0,ch);
				}
				byte[] byteArray = arrayOutputStream.toByteArray();
				outputStream.write(("HTTP/1.1 200 ok\r\nContent-Type: text/html\r\nContent-Length:"+byteArray.length+" \r\n\r\n").getBytes());
				outputStream.write(byteArray);
				fis.close();
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				outputStream.write(errorMessage.getBytes());

			}
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
}
