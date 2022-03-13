
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.SocketException;
import java.net.InetAddress;


public class Rfc865UdpServer {

    static int RFC865_PORT = 17;
    static String QUOTE = "The best way to predict the future is to invent it.";

    public static void main(String[] argv) {
        //
        // 1. Open UDP socket at well-known port
        //
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(RFC865_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        while (true) {
            try {
                //
                // 2. Listen for UDP request from client
                //
                byte[] buf = new byte[512];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                socket.receive(request);
                
                //
                // 3. Send UDP reply to client
                //
                // quote to send
                String replyStr = QUOTE;
                byte[] replyBuf = replyStr.getBytes("UTF-8");

                // reply address & port
                InetAddress clientAddr = request.getAddress();
                int clientPort = request.getPort();

                // reply packet
                DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length, clientAddr, clientPort);
                socket.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        socket.close();
    }
}