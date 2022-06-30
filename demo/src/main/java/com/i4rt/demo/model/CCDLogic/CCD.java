package com.i4rt.demo.model.CCDLogic;

import com.i4rt.demo.model.Receiver;
import lombok.Getter;

import java.io.IOException;


public final class CCD {

    private static final String IP = "192.168.1.25";
    private static final Integer sendPort = 10001;
    private static final Integer receivePort = 10002;
    private static Receiver receiver;

    public static String getIP(){
        return IP;
    }

    public static Integer getSendPort(){
        return sendPort;
    }

    public static Integer getReceivePort(){
        return receivePort;
    }



    public static byte[] sendMessage(byte[] data) throws IOException {
        if(receiver == null){
            receiver = new Receiver(IP, sendPort);
        }
        return receiver.sendDataNoReceive(data);
//        DatagramSocket socket;
//        InetAddress address;
//        socket = new DatagramSocket();
//        address = InetAddress.getByName(IP);
//        DatagramPacket packet
//                = new DatagramPacket(data, data.length, address, port);
//        System.out.println("presending");
//        socket.send(packet);
//
//        packet = new DatagramPacket(data, data.length);
//        System.out.println("prereceiving");
//        socket.receive(packet);

        //return data;
    }
}
