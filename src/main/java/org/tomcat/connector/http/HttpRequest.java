package org.tomcat.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.tomcat.util.ParameterMap;
import org.tomcat.util.RequestUtil;

public class HttpRequest implements HttpServletRequest {


	protected HashMap<String,String> headers = new HashMap<>();
	protected ArrayList<Cookie> cookies = new ArrayList<>();
	protected ParameterMap<String,String> parameters = new ParameterMap<>();
	private String uri;
	private String method;
	private String encoding;
	private boolean parsed;

	public String getMethod() {
		return method;
	}



	public String getProtocol() {
		return protocol;
	}

	private String path;
	private String protocol;

	private InputStream inputStream;
	private String sessionId;
	private boolean requestedSessionCookie;
	private boolean requestedSessionCookieURL;
	private int contentLenght;
	private String contentType;
	//TODO
	private String queryString;
	public HttpRequest(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	public void parse() {
		StringBuilder builder = new StringBuilder();
		try {
			char[] buf = new char[1024];
			int len;

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("utf8"));
			len = inputStreamReader.read(buf);

			for (int i = 0; i < len; i++) {
				builder.append(buf[i]);
			}
			System.out.println(builder);
			int indexOf = builder.indexOf("\r\n");

			uri = builder.substring(0, indexOf);

			String[] split = uri.split(" ");
			method = split[0];
			path = split[1];
			protocol = split[2];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUri() {
		return uri;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		if (encoding == null)
			encoding = "ISO-8859-1";
		return encoding;
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return contentLenght;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return inputStream.read();
			}
		};
	}

	@Override
	public String getParameter(String name) {
		if(!parsed)
		{
			parseParameters();
		}
		return parameters.get(name);
	}

	private void parseParameters() {
		try {

			RequestUtil.parseParameters(parameters,getQueryString(),getCharacterEncoding());

			String contentType = getContentType();
			if (contentType == null)
				contentType = "";
			int semicolon = contentType.indexOf(';');
			if (semicolon >= 0) {
				contentType = contentType.substring (0, semicolon).trim();
			} else {
				contentType = contentType.trim();
			}
			if ("POST".equals(getMethod()) && (getContentLength() > 0)
					&& "application/x-www-form-urlencoded".equals(contentType)) {
				try {
					int max = getContentLength();
					int len = 0;
					byte[] buf = new byte[getContentLength()];
					ServletInputStream is = getInputStream();
					while (len < max) {
						int next = is.read(buf, len, max - len);
						if (next < 0 ) {
							break;
						}
						len += next;
					}
					is.close();
					if (len < max) {
						throw new RuntimeException("Content length mismatch");
					}
					RequestUtil.parseParameters(parameters, buf, encoding);
					parameters.setLocked(true);
					parsed=true;
				} catch (UnsupportedEncodingException ue){
					ue.printStackTrace();
				} catch (IOException e) {
					throw new RuntimeException("Content read fail");
				}
			}


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Enumeration getParameterNames() {
		if(!parsed)
		{
			parseParameters();
		}
		return Collections.enumeration(parameters.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		if(!parsed)
		{
			parseParameters();
		}
		return null;
	}

	@Override
	public Map getParameterMap() {
		// TODO Auto-generated method stub
		if(!parsed)
		{
			parseParameters();
		}
		return parameters;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return new BufferedReader(new  InputStreamReader(inputStream));
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return (Cookie[]) cookies.toArray();
	}

	@Override
	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return Instant.parse(headers.get(name)).toEpochMilli();
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return headers.get(name);
	}

	@Override
	public Enumeration getHeaders(String name) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return Collections.enumeration(headers.keySet());
	}

	@Override
	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return Integer.valueOf(headers.get(name));
	}

	@Override
	public String getPathInfo() {
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return sessionId;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return uri;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return requestedSessionCookie;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return requestedSessionCookieURL;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return requestedSessionCookieURL;
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void setRequestedSessionId(String value) {
		this.sessionId=value;
	}

	public void setRequestedSessionCookie(boolean b) {
		this.requestedSessionCookie=b;
	}

	public void setRequestedSessionURL(boolean b) {
		this.requestedSessionCookieURL=b;
	}

	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
	}

	public void setContentLength(int n) {
		this.contentLenght=n;
	}

	public void setContentType(String value) {
		this.contentType=value;

	}

	public void setMethod(String method) {
		this.method=method;
	}

	public void setProtocol(String protocol) {
		this.protocol=protocol;
	}

	public void setRequestURI(String normalizedUri) {
		this.uri=normalizedUri;
	}

	public void setQueryString(String string) {
		this.queryString=string;
	}

}
