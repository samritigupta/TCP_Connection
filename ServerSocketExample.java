import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExample extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;

    public ServerSocketExample( int port )
    {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket( port );
            this.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        running = true;
        while( running ) {
            try {
                System.out.println( "Listening for a connection" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler( socket );
                requestHandler.start();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) {
        int port = 80;
        System.out.println( "Start server on port: " + port );

        ServerSocketExample server = new ServerSocketExample( port );
        server.startServer();

        // Automatically shutdown in 1 minute
        try {
            Thread.sleep( 60000 );
        }
        catch( Exception e ) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}

