package org.tomcat.connector;

import org.tomcat.connector.http.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseStream extends ServletOutputStream {
    private  boolean closed;
    protected   boolean commit;
    protected int length = -1;
    private  int count;
    private HttpResponse response;

    public ResponseStream(HttpResponse response) {
        this.response = response;
        closed = false;
        commit = false;
        count = 0;
    }

    public boolean getCommit() {

        return (this.commit);

    }

    public void setCommit(boolean commit) {

        this.commit = commit;

    }


    @Override
    public void close() throws IOException {
        if(closed)
            throw new IOException("responseStream.close.closed");
        super.close();
        closed=true;
    }

    @Override
    public void write(int b) throws IOException {
        if (closed)
            throw new IOException("responseStream.write.closed");

        if ((length > 0) && (count >= length))
            throw new IOException("responseStream.write.count");
        response.write(b);
        count++;
    }

    @Override
    public void write(byte b[]) throws IOException {

        write(b, 0, b.length);

    }


    @Override
    public void write(byte b[], int off, int len) throws IOException {
        System.out.println(new String(b)+ "  2");
        if (closed)
            throw new IOException("responseStream.write.closed");

        int actual = len;
        if ((length > 0) && ((count + len) >= length))
            actual = length - count;
        response.write(b, off, actual);
        count += actual;
        if (actual < len)
            throw new IOException("responseStream.write.count");

    }
    void reset() {
        count = 0;
    }

    boolean closed() {
        return (this.closed);

    }
    @Override
    public void flush() throws IOException {
        System.out.println("flush");
        if(closed)
            throw new IOException("responseStream.close.closed");
        if (commit)
            response.flushBuffer();
    }
}
