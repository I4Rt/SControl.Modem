package com.i4rt.demo.model.Threads;

import java.net.*;

public class ReceiverGetAnswerTask extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    private Integer port;

    //Need to be created outside and passed as a parameter (finally it will be changed by this thread)
    private ExchangeCCDData outsideData;
    private byte[] bufReceived;




    public ReceiverGetAnswerTask(String IP, Integer port, Integer bufSize, ExchangeCCDData data){
        this.bufReceived = new byte[bufSize];
        this.outsideData = data;
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

                DatagramPacket packet = new DatagramPacket(bufReceived, bufReceived.length, address, port);
                System.out.println("Start receive: " + address + ":" + port);
                socket.receive(packet);
                System.out.println(bufReceived);
                System.out.println("RECEIVED.");

                outsideData.setBuf(bufReceived);

                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
