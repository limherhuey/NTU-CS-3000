
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;


public class Rfc865UdpClient {

    static int RFC865_PORT = 17;
    static String SERVER_NAME = "swlab2-c.scse.ntu.edu.sg";
    // static String SERVER_NAME = "localhost";
    final static String NAME = "Lim Her Huey";
    final static String LAB_GROUP = "SSP1";

    public static void main(String[] argv) {
        //
        // 1. Open UDP socket at well-known port
        //
        DatagramSocket socket = null;
        // InetAddress serverAddr;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_NAME);
            socket = new DatagramSocket();
            socket.connect(serverAddr, RFC865_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        try {
            //
            // 2. Send UDP request to server
            //
            // prepare buffer to send
            String myAddr = InetAddress.getLocalHost().getHostAddress();
            String msg = new String(NAME + ", " + LAB_GROUP + ", " + myAddr);
            byte[] buf = msg.getBytes("UTF-8");

            // request packet
            DatagramPacket request = new DatagramPacket(buf, buf.length);
            socket.send(request);
            
            //
            // 3. Receive UDP reply from server
            //
            byte[] replyBuf = new byte[512];
            DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
            socket.receive(reply);

            // print reply
            String res = replyBuf.toString();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}