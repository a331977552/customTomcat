package org.tomcat.connector.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpResponseFacade implements HttpServletResponse{

	private HttpServletResponse httpResponse;

	public HttpResponseFacade(HttpServletResponse httpResponse) {
		super();
		this.httpResponse = httpResponse;
	}

	@Override
	public String getCharacterEncoding() {
		return httpResponse.getCharacterEncoding();
	}

	@Override
	public String getContentType() {

		return httpResponse.getContentType();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return httpResponse.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return httpResponse.getWriter();
	}

	@Override
	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub
		httpResponse.setCharacterEncoding(charset);
	}

	@Override
	public void setContentLength(int len) {
		// TODO Auto-generated method stub
		httpResponse.setContentLength(len);
	}

	@Override
	public void setContentType(String type) {
		// TODO Auto-generated method stub
		httpResponse.setContentType(type);
	}

	@Override
	public void setBufferSize(int size) {
		// TODO Auto-generated method stub
		httpResponse.setBufferSize(size);
	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 	httpResponse.getBufferSize();
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		httpResponse.flushBuffer();
	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
		httpResponse.resetBuffer();
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return 	httpResponse.isCommitted();
	}

	@Override
	public void reset() {
		httpResponse.reset();
	}

	@Override
	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
		httpResponse.setLocale(loc);
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return 	httpResponse.getLocale();
	}

	@Override
	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		httpResponse.addCookie(cookie);
	}

	@Override
	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return httpResponse.containsHeader(name);
	}

	@Override
	public String encodeURL(String url) {
		// TODO Auto-generated method stub
		return httpResponse.encodeURL(url);
	}

	@Override
	public String encodeRedirectURL(String url) {
		// TODO Auto-generated method stub
		return httpResponse.encodeRedirectURL(url);
	}

	@Override
	public String encodeUrl(String url) {
		// TODO Auto-generated method stub
		return httpResponse.encodeUrl(url);
	}

	@Override
	public String encodeRedirectUrl(String url) {
		return  httpResponse.encodeRedirectUrl(url);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		// TODO Auto-generated method stub
		httpResponse.sendError(sc,msg);
	}

	@Override
	public void sendError(int sc) throws IOException {
		// TODO Auto-generated method stub
		httpResponse.sendError(sc);

	}

	@Override
	public void sendRedirect(String location) throws IOException {
		// TODO Auto-generated method stub
		httpResponse.sendRedirect(location);

	}

	@Override
	public void setDateHeader(String name, long date) {
		// TODO Auto-generated method stub
		httpResponse.setDateHeader(name,date);

	}

	@Override
	public void addDateHeader(String name, long date) {
		// TODO Auto-generated method stub
		httpResponse.addDateHeader(name,date);

	}

	@Override
	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub
		httpResponse.setHeader(name,value);

	}

	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub
		httpResponse.addHeader(name,value);

	}

	@Override
	public void setIntHeader(String name, int value) {
		// TODO Auto-generated method stub
		httpResponse.setIntHeader(name,value);

	}

	@Override
	public void addIntHeader(String name, int value) {
		// TODO Auto-generated method stub
		httpResponse.addIntHeader(name,value);

	}

	@Override
	public void setStatus(int sc) {
		// TODO Auto-generated method stub
		httpResponse.setStatus(sc);

	}

	@Override
	public void setStatus(int sc, String sm) {
		// TODO Auto-generated method stub
		httpResponse.setStatus(sc,sm);

	}

}
