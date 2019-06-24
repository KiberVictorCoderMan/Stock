import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {

    /**
     * WEB_ROOT is the directory where our HTML and other files reside. For this
     * package, WEB_ROOT is the "webroot" directory under the working directory.
     * The working directory is the location in the file system from where the
     * java command was invoked.
	 *
	 * Authors Rodionov, Sinitsyna
     */
	private static final int NTHREADS = 100;
	private static final Executor exec
	= Executors.newFixedThreadPool(NTHREADS);
	
    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // the shutdown command received
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void handleRequest(Socket connection) throws SQLException, ClassNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
    	try (
    		InputStream input = connection.getInputStream();
    		OutputStream output = connection.getOutputStream();   			
    			)
    	{
    		// create Request object and parse
            Request request = new Request(input);
            request.parse();

            // create Response object
            Response response = new Response(output);
            // check if this is a request for a servlet or a static resource
            // a request for a servlet begins with "/servlet/"
            Processor processor;

			if (request.getURI().startsWith("/servlet/")) {
                processor = new ServletProcessor();
            } else {
                processor = new StaticResourceProcessor();
            }
            
            processor.process(request, response);

            //check if the previous URI is a shutdown command
            //connection.close();
            shutdown = request.getURI().equals(SHUTDOWN_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void await() {
        int port = 8891;

        // Loop waiting for a request
        	try(
        			ServerSocket socket = new ServerSocket(port);       			
        			)
        	{
        	    System.out.println("Server is waiting for request at port: " + port);
        	    while (!shutdown) {
            		final Socket connection = socket.accept();
            		Runnable task = new Runnable() {
            		public void run() {
                        try {
                            handleRequest(connection);
                        } catch (SQLException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        }
                    }
            		};
            		exec.execute(task);
            		}
                	    		
        	}
        	catch(Exception e) {
    			e.printStackTrace();
    			System.exit(1);
    		}
        }
    
 
}

