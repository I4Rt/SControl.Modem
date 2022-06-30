package com.i4rt.demo.model.Threads;

import com.i4rt.demo.interfaces.DataRepo;
import com.i4rt.demo.interfaces.ModeDataRepo;
import com.i4rt.demo.interfaces.ModeRepo;
import com.i4rt.demo.interfaces.RegisterRepo;
import com.i4rt.demo.model.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ReceiverSettingMode extends Thread {

    @Autowired
    private ModeRepo modeRepo;

    private Mode mode;


    public ReceiverSettingMode(Mode mode){
        this.mode = mode;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                mode.getModeData().setAnswer(null);
                mode.setSet(false);

                Receiver receiver = new Receiver("192.168.1.125", 30020);
                System.out.println("Sending mode assets:");
                System.out.println(Hex.encodeHexString(mode.getModeData().getData()));
                byte[] answer = receiver.sendData(mode.getModeData().getData());

                //String rowAnswer =  Hex.encodeHexString(answer);

                mode.getModeData().setAnswer(answer);



                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}