import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Receiver {

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();

        byte[] sendData = {(byte) (Integer.parseInt("00", 16)),
                           (byte) (Integer.parseInt("01", 16)),
                           (byte) (Integer.parseInt("80", 16)),
                           (byte) (Integer.parseInt("00", 16)),
                           (byte) (Integer.parseInt("00", 16)),
                           (byte) (Integer.parseInt("0D", 16)),
                           (byte) (Integer.parseInt("31", 16)),
                           (byte) (Integer.parseInt("43", 16)),
                           (byte) (Integer.parseInt("00", 16)),
                           (byte) (Integer.parseInt("00", 16)),
                           (byte) (Integer.parseInt("01", 16)),
        };
        //"00018000000D3143000001".getBytes();
        System.out.println(sendData);
        System.out.println(Integer.parseInt("ad", 16));
        String echo = client.sendEcho(sendData);
        System.out.println("send");
        System.out.println(echo);


    }

    public void run(int port) {
        try {
            DatagramSocket serverSocket = new DatagramSocket();
            byte[] receiveData = new byte[10];
            String sendString = "polo";
            byte[] sendData = {(byte) 00, (byte) 01, (byte) 80, (byte) 00, (byte) 00, (byte) 0D, (byte) 31, (byte) 43, (byte) 00, (byte) 00, (byte) 01};

            System.out.printf("Listening on udp:%s:%d%n",
                    InetAddress.getLocalHost().getHostAddress(), port);
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            while(true)
            {
                serverSocket.receive(receivePacket);
                String sentence = new String( receivePacket.getData(), 0,
                        receivePacket.getLength() );
                System.out.println("RECEIVED: " + sentence);
                // now send acknowledgement packet back to sender
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        // should close serverSocket in finally block
    }
}