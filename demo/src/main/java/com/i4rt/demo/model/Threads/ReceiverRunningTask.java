package com.i4rt.demo.model.Threads;

import com.i4rt.demo.interfaces.RegisterRepo;
import com.i4rt.demo.model.Receiver;
import com.i4rt.demo.model.Register;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ReceiverRunningTask extends Thread {
    @Autowired
    private RegisterRepo registerRepo;

    private Register register;

    public ReceiverRunningTask(RegisterRepo registerRepo, Register register) {
        this.registerRepo = registerRepo;
        this.register = register;

    }

    public ReceiverRunningTask(Register register) {
        this.register = register;


    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                register.getData().setAnswer(null);


                Receiver receiver = new Receiver();
                System.out.println("Sending:");
                System.out.println(Hex.encodeHexString(register.getData().getData()));
                byte[] answer = receiver.sendData(register.getData().getData());

                System.out.println("Got data answer:");
                System.out.println(Hex.encodeHexString(answer));

                //String rowAnswer =  Hex.encodeHexString(answer);

                register.getData().setAnswer(answer);

                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}