import java.io.IOException;
import java.net.*;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClient() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("192.168.1.125");
    }

    public String sendEcho(byte[] msg) throws IOException {
        buf = msg;
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 30020);
        System.out.println("presending");
        socket.send(packet);
        System.out.println("aftersending");
        packet = new DatagramPacket(buf, buf.length);
        System.out.println("prereceiving");
        socket.receive(packet);
        System.out.println("afterreceiving");
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}