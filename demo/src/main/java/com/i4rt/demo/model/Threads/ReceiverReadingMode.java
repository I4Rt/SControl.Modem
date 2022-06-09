package com.i4rt.demo.model.Threads;

import com.i4rt.demo.interfaces.ModeRepo;
import com.i4rt.demo.model.DataBin;
import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Receiver;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ReceiverReadingMode extends Thread {

    @Autowired
    private ModeRepo modeRepo;

    private Mode mode;


    public ReceiverReadingMode(Mode mode){
        this.mode = mode;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                mode.getModeData().setAnswer(null);
                mode.setSet(false);


                Receiver receiver = new Receiver();
                System.out.println("Sending read command:");
                System.out.println("00018000000B3147000000");
                byte[] dataToSend = DataBin.generateDataToSend("00018000000B3147000000");
                byte[] answer = receiver.sendData(dataToSend);
                System.out.println("Got data answer:");
                System.out.println(Hex.encodeHexString(answer));
                //String rowAnswer =  Hex.encodeHexString(answer);

                mode.getModeData().setAnswer(answer);
                mode.setSet(true);



                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}