package com.i4rt.demo.model;

import java.io.IOException;
import java.net.*;

public class Receiver {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] bufSend;
    private byte[] bufReceiver = new byte[11];

    public Receiver() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("192.168.1.125");
    }

    public byte[] sendData(byte[] msg) throws IOException {
        bufSend = msg;
        DatagramPacket packet
                = new DatagramPacket(bufSend, bufSend.length, address, 30020);
        System.out.println("presending");
        socket.send(packet);
        System.out.println("aftersending");
        packet = new DatagramPacket(bufReceiver, bufReceiver.length);
        System.out.println("prereceiving");
        socket.receive(packet);
        System.out.println(bufReceiver);
        System.out.println("afterreceiving");

        return bufReceiver;
    }

    public void close() {
        socket.close();
    }
}
