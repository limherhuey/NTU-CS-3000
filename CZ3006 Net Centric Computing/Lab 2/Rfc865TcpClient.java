
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Rfc865TcpClient {
    
    static int RFC865_PORT = 17;
    static String SERVER_NAME = "swlab2-c.scse.ntu.edu.sg";
    // static String SERVER_NAME = "localhost";
    final static String NAME = "Lim Her Huey";
    final static String LAB_GROUP = "SSP1";

    public static void main(String[] argv) {
        //
        // 1. Establish TCP connection with server
        //
        Socket socket = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_NAME);
            socket = new Socket(serverAddr, RFC865_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
       
        try {
            //
            // 2. Send TCP request to server
            //
            // prepare buffer to send
            String myAddr = InetAddress.getLocalHost().getHostAddress();
            String msg = new String(NAME + ", " + LAB_GROUP + ", " + myAddr);
            byte[] buf = msg.getBytes("UTF-8");

            // write to output stream
            OutputStream os = socket.getOutputStream();
            os.write(buf);
            
            //
            // 3. Receive TCP reply from server
            //
            byte[] replyBuf = new byte[512];
            InputStream is = socket.getInputStream();
            is.read(replyBuf);

            // print reply
            String res = replyBuf.toString();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
