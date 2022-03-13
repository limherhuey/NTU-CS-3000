
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Rfc865TcpServer {

    static int RFC865_PORT = 17;
    static String QUOTE = "The best way to predict the future is to invent it.";

    public static void main(String[] argv) {
        //
        // 1. Open TCP socket at well-known port
        //
        ServerSocket parentSocket = null;
        try {
            parentSocket = new ServerSocket(RFC865_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
       
        while (true) {
            try {
                //
                // 2. Listen to establish TCP connection with clnt
                //
                Socket childSocket = parentSocket.accept();

                //
                // 3. Create new thread to handle client connection
                //
                ClientHandler client = new ClientHandler(childSocket);
                Thread thread = new Thread(client);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            parentSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}

class ClientHandler implements Runnable {

    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //
            // 4. Receive TCP request from client
            //
            byte[] buf = new byte[512];
            InputStream is = this.socket.getInputStream();
            is.read(buf);
            
            //
            // 5. Send TCP reply to client
            //
            // prepare buffer to send
            String replyStr = Rfc865TcpServer.QUOTE;
            byte[] replyBuf = replyStr.getBytes("UTF-8");

            // write to output stream
            OutputStream os = socket.getOutputStream();
            os.write(replyBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
