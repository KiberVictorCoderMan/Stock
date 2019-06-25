import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;


public class Request implements ServletRequest {

  private InputStream input;
  private String uri;
  private String rqst;
  private String[] rqstArr;

  public Request(InputStream input) {
    this.input = input;
  }

  public String getURI() {
    return uri;
  }

  private String parseUri(String requestString) {
    System.out.println(requestString);
    int index1, index2;
    index1 = requestString.indexOf(' ');
    if (index1 != -1) {
      index2 = requestString.indexOf(' ', index1 + 1);
      if (index2 > index1)
        return requestString.substring(index1 + 1, index2);
    }
    return null;
  }

  public void parse() {
    // Read a set of characters from the socket
    StringBuffer request = new StringBuffer(2048);
    int i;
    byte[] buffer = new byte[2048];
    try {
      i = input.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
      i = -1;
    }
    for (int j = 0; j < i; j++) {
      request.append((char) buffer[j]);
    }
    //System.out.print(request.toString());
    rqst = request.toString();
    rqstArr = rqst.split("\n");
    uri = parseUri(request.toString());
  }

  /* implementation of the ServletRequest*/
  public Object getAttribute(String attribute) {
    for (int i = 0; i < rqstArr.length; i++) {
      if (rqstArr[i].contains(attribute)) return rqstArr[i].substring(rqstArr[i].indexOf(":") + 1);
    }
    return null;
  }

  public Enumeration getAttributeNames() {
    return null;
  }

  public String getRealPath(String path) {
    return null;
  }

  @Override
  public int getRemotePort() {
    return 0;
  }

  @Override
  public String getLocalName() {
    return null;
  }

  @Override
  public String getLocalAddr() {
    return null;
  }

  @Override
  public int getLocalPort() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public AsyncContext startAsync() throws IllegalStateException {
    return null;
  }

  @Override
  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
    return null;
  }

  @Override
  public boolean isAsyncStarted() {
    return false;
  }

  @Override
  public boolean isAsyncSupported() {
    return false;
  }

  @Override
  public AsyncContext getAsyncContext() {
    return null;
  }

  @Override
  public DispatcherType getDispatcherType() {
    return null;
  }

  public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  public boolean isSecure() {
    return false;
  }

  public String getCharacterEncoding() {
    return null;
  }

  public int getContentLength() {
    return 0;
  }

  public String getContentType() {
    return null;
  }

  public ServletInputStream getInputStream() throws IOException {
    return null;
  }

  public Locale getLocale() {
    return null;
  }

  public Enumeration getLocales() {
    return null;
  }

  public String getParameter(String name) {
    String param = null;
    int index1, index2;
    try {
      if (rqst.contains(name)) {
        index1 = rqst.indexOf(name) + name.length();
        index2 = rqst.indexOf('&', index1);
        if (index2 == -1) index2 = rqst.length();
        if (index2 > index1) param = rqst.substring(index1 + 1, index2);
      }
      param = URLDecoder.decode(param, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return param;
  }

  public boolean getMethod(String meth) {
    boolean param = false;
    int index1, index2;

    if (rqst.startsWith(meth)) {
      param = true;
    }
    return param;
  }

  public String getType() {
    return rqstArr[0].substring(0, rqstArr[0].indexOf(" "));
  }

  public Map getParameterMap() {
    return null;
  }

  public Enumeration getParameterNames() {
    return null;
  }

  public String[] getParameterValues(String parameter) {
    return null;
  }

  public String getProtocol() {
    return null;
  }

  public BufferedReader getReader() throws IOException {
    return null;
  }

  public String getRemoteAddr() {
    return null;
  }

  public String getRemoteHost() {
    return null;
  }

  public String getScheme() {
   return null;
  }

  public String getServerName() {
    return null;
  }

  public int getServerPort() {
    return 0;
  }

  public String getBody() {
    return rqstArr[rqstArr.length - 1];
  }

  public void removeAttribute(String attribute) {
  }

  public void setAttribute(String key, Object value) {

  }

  public void setCharacterEncoding(String encoding)
    throws UnsupportedEncodingException {
  }

//    @Override
//    public int getRemotePort() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public String getLocalName() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public String getLocalAddr() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public int getLocalPort() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//  @Override
//  public ServletContext getServletContext() {
//    return null;
//  }
//
//  @Override
//  public AsyncContext startAsync() throws IllegalStateException {
//    return null;
//  }
//
//  @Override
//  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
//    return null;
//  }
//
//  @Override
//  public boolean isAsyncStarted() {
//    return false;
//  }
//
//  @Override
//  public boolean isAsyncSupported() {
//    return false;
//  }
//
//  @Override
//  public AsyncContext getAsyncContext() {
//    return null;
//  }
//
//  @Override
//  public DispatcherType getDispatcherType() {
//    return null;
//  }

//  @Override
//  public ServletContext getServletContext() {
//    return null;
//  }
//
//  @Override
//  public AsyncContext startAsync() throws IllegalStateException {
//    return null;
//  }
//
//  @Override
//  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
//    return null;
//  }
//
//  @Override
//  public boolean isAsyncStarted() {
//    return false;
//  }
//
//  @Override
//  public boolean isAsyncSupported() {
//    return false;
//  }
//
//  @Override
//  public AsyncContext getAsyncContext() {
//    return null;
//  }
//
//  @Override
//  public DispatcherType getDispatcherType() {
//    return null;
//  }

//    @Override
//    public ServletContext getServletContext() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public AsyncContext startAsync() throws IllegalStateException {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean isAsyncStarted() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public boolean isAsyncSupported() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public AsyncContext getAsyncContext() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public DispatcherType getDispatcherType() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

	public long getContentLengthLong() {
		// TODO Auto-generated method stub
		return 0;
	}

}
