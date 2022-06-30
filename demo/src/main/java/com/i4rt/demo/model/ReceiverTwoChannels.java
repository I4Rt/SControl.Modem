package com.i4rt.demo.model;

import com.i4rt.demo.model.Threads.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.net.*;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;

@AllArgsConstructor
public class ReceiverTwoChannels {

    private String IP;
    private Integer sendPort;
    private Integer receivePort;
    private Integer timeout;




    public byte[] sendData(byte[] msg) throws IOException {
        System.out.println(System.currentTimeMillis());


        ExchangeCCDData data = new ExchangeCCDData(); //temp container class to get result from the thread WTF?
        Thread threadGetAnswer = new Thread(new ReceiverGetAnswerTask(IP, receivePort, 1024, data));

        threadGetAnswer.start();

        Thread threadSender = new Thread(new ReceiverSendTask(IP, sendPort, msg));

        threadSender.start();

        Timer timer1 = new Timer();
        TimeOutTask timeOutTask1 = new TimeOutTask(threadSender, timer1);
        Timer timer2 = new Timer();
        TimeOutTask timeOutTask2 = new TimeOutTask(threadGetAnswer, timer2);
        timer1.schedule(timeOutTask1, timeout);
        timer2.schedule(timeOutTask2, timeout);

        try {
            Thread.sleep(timeout);

            threadSender.interrupt();
            threadGetAnswer.interrupt();


            System.out.println(threadSender.isInterrupted());
            System.out.println(threadGetAnswer.isInterrupted());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data.getBuf();
    }

}
