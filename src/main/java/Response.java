import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {

    private static final int BUFFER_SIZE = 1024;
    private OutputStream output;
    private PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    /* This method is used to serve a static page */
    public void sendStaticResource(String resourceURI) throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI */
            File file = new File(Constants.WEB_ROOT, resourceURI);
            fis = new FileInputStream(file);
	      /*
	         HTTP Response = Status-Line
	           *(( general-header | response-header | entity-header ) CRLF)
	           CRLF
	           [ message-body ]
	         Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	      */

            //RBS begin
            String HTTP_header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
            output.write(HTTP_header.getBytes());
            //RBS end

            int ch = fis.read(bytes, 0, BUFFER_SIZE);
            while (ch != -1) {
                output.write(bytes, 0, ch);
                ch = fis.read(bytes, 0, BUFFER_SIZE);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        } finally {
            if (fis != null)
                fis.close();
        }
    }

    public void sendText(String text) throws IOException {
        //RBS begin
        String HTTP_header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        output.write(HTTP_header.getBytes());
        output.write(text.getBytes());
    }

  /** implementation of ServletResponse  */
  public void flushBuffer() throws IOException {
  }

  public int getBufferSize() {
    return 0;
  }

  public String getCharacterEncoding() {
    return null;
  }

  public Locale getLocale() {
    return null;
  }

  public ServletOutputStream getOutputStream() throws IOException {
    return null;
  }

  public PrintWriter getWriter() throws IOException {
    /* autoflush is true, println() will flush,
    but print() will not.*/
    writer = new PrintWriter(output, true);
    return writer;
  }

  public boolean isCommitted() {
    return false;
  }

  public void reset() {
  }

  public void resetBuffer() {
  }

  public void setBufferSize(int size) {
  }

  public void setContentLength(int length) {
  }

  public void setContentType(String type) {
  }

  public void setLocale(Locale locale) {
  }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public void setContentLengthLong(long arg0) {
		// TODO Auto-generated method stub
		
	}
}
