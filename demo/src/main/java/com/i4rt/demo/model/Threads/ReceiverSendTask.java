package com.i4rt.demo.model.Threads;

import org.apache.commons.codec.binary.Hex;

import java.net.*;

public class ReceiverSendTask extends Thread{

    private DatagramSocket socket;
    private InetAddress address;
    private Integer port;


    private byte[] bufSend;

    public ReceiverSendTask(String IP, Integer port, byte[] bufSend){
        this.bufSend = bufSend;
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(IP);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){ // Not necessary loop code

            try{
                DatagramPacket packet = new DatagramPacket(bufSend, bufSend.length, address, port);
                System.out.println("Start sending: " + address + ":" + port + "\n" +
                        "    Data: " + ((Hex.encodeHexString(bufSend).length() > 128) ? Hex.encodeHexString(bufSend).substring(0, 127) : Hex.encodeHexString(bufSend)));
                socket.send(packet);
                System.out.println("SENT.");

                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
