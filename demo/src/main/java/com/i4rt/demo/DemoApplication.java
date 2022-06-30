package com.i4rt.demo;

import com.i4rt.demo.model.CCDLogic.CCDCommands.N5746APowerSupplySet;
import com.i4rt.demo.model.ReceiverTwoChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        //Test place
//        ReceiverTwoChannels senderTest = new ReceiverTwoChannels("111.111.111.111", 6060, 6061, 1000);
//        try {
//            System.out.println(senderTest.sendData(new byte[]{10, 10, 10}));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        SpringApplication.run(DemoApplication.class, args);
    }

}
