package org.tomcat.connector.http;

import org.tomcat.catalina.Context;
import org.tomcat.connector.ResponseStream;
import org.tomcat.connector.ResponseWriter;
import org.tomcat.util.CookieTools;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class HttpResponse implements HttpServletResponse {
    private static final int BUFFER_SIZE = 1024;
    protected byte[] buffer = new byte[BUFFER_SIZE];
    private HttpRequest request;
    private OutputStream outputStream;
    private FileInputStream fis;
    private int bufferPos;
    private int contentCount;
    private String encoding;
    private String contentType;
    private ResponseWriter writer;
    private int contentLength;
    private boolean committed;
    private Context context;

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public HttpResponse(OutputStream outputStream) {
        super();
        this.outputStream = outputStream;
    }

    public HttpResponse() {
    }

    public void write(int b) throws IOException {
        if (bufferPos > buffer.length) {
            flushBuffer();
        }else{
            buffer[bufferPos++]=(byte)b;
            contentCount++;
        }
    }
    public void write(byte[] buffer) throws IOException {
        write(buffer,0,buffer.length);
    }
    public void write(byte[] buf,int offset,int len) throws IOException {
            if(len==0)
                return ;
            if(len<(buf.length-bufferPos)){
                System.arraycopy(buf,offset,this.buffer,bufferPos,len);
                bufferPos+=len;
                contentCount+=len;
            }else{
                flushBuffer();
                int iterations=len/this.buffer.length;
                for(int i=0;i<iterations;i++){
                    write(buf, offset + (i * this.buffer.length), this.buffer.length);
                }
               int leftover= len-iterations*this.buffer.length;
                if(leftover>0)
                    write(buf, offset + (iterations * this.buffer.length),leftover);
            }
    }
    public void setRequest(HttpRequest request) {
        this.request = request;
    }


    @Override
    public String getCharacterEncoding() {
        if(encoding==null){
            encoding= "ISO-8859-1";
        }
        return encoding;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        this.encoding=charset;
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return contentType;
    }

    @Override
    public void setContentType(String type) {
    this.contentType=type;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        ResponseStream stream=new ResponseStream(this);
        stream.setCommit(false);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        OutputStreamWriter osr=new OutputStreamWriter(outputStream,getCharacterEncoding());
       writer= new ResponseWriter(osr);;
        return writer;
    }

    @Override
    public void setContentLength(int len) {
    this.contentLength=len;
    }

    @Override
    public int getBufferSize() {

        return 0;
    }

    @Override
    public void setBufferSize(int size) {
        // TODO Auto-generated method stub

    }

    @Override
    public void flushBuffer() throws IOException {
        if (bufferPos > 0) {
            try {
                outputStream.write(buffer, 0, bufferPos);
            } finally {
                bufferPos = 0;
            }
        }


    }


    @Override
    public void resetBuffer() {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean isCommitted() {
        return false;
    }


    @Override
    public void reset() {
        // TODO Auto-generated method stub


    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public void addCookie(Cookie cookie) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean containsHeader(String name) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public String encodeURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String encodeRedirectURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String encodeUrl(String url) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String encodeRedirectUrl(String url) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void sendError(int sc, String msg) throws IOException {
        // TODO Auto-generated method stub

    }


    @Override
    public void sendError(int sc) throws IOException {
        // TODO Auto-generated method stub

    }


    @Override
    public void sendRedirect(String location) throws IOException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setDateHeader(String name, long date) {
        // TODO Auto-generated method stub

    }


    @Override
    public void addDateHeader(String name, long date) {
        // TODO Auto-generated method stub

    }


    @Override
    public void setHeader(String name, String value) {
        // TODO Auto-generated method stub

    }


    @Override
    public void addHeader(String name, String value) {
        // TODO Auto-generated method stub

    }


    @Override
    public void setIntHeader(String name, int value) {
        // TODO Auto-generated method stub

    }


    @Override
    public void addIntHeader(String name, int value) {
        // TODO Auto-generated method stub

    }


    @Override
    public void setStatus(int sc) {
        // TODO Auto-generated method stub

    }
    /**
     * Send the HTTP response headers, if this has not already occurred.
     */
    protected void sendHeaders() throws IOException {

    }


    @Override
    public void setStatus(int sc, String sm) {
        // TODO Auto-generated method stub

    }


    public void finishResponse() {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public void setContext(Context context) {
        this.context=context;
    }
}
