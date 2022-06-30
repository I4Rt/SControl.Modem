package com.i4rt.demo.model;

import com.i4rt.demo.model.Threads.ReceiverRunningTask;
import com.i4rt.demo.model.Threads.TimeOutTask;
import lombok.*;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Setter
@Getter
@ToString
@Table(name="registers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Register {



    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String input;

    private int timeout;

    private int position;

    private String mode = "modem";


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id", referencedColumnName = "id")
    private Data data;


    @ManyToMany(mappedBy = "registers")
    private Set<Mode> modes = new HashSet<>();

    public Register(String name, String input, String position){
        this.name = name;
        this.input = input;
        this.position = Integer.parseInt(position);

        DataBin dataBin = new DataBin(this.position, this.input, this.mode);

        this.data = new Data(dataBin.getResultData());
        System.out.println(data);
    }

    public String sendData() throws IOException {
        Date date = new Date();
        Thread thread = new Thread(new ReceiverRunningTask(this));

        thread.start();

        Timer timer = new Timer();
        TimeOutTask timeOutTask = new TimeOutTask(thread, timer);
        timer.schedule(timeOutTask, timeout);
        try {
            Thread.currentThread().join(timeout);
            thread.interrupt();

            System.out.println(thread.isInterrupted());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
//        try {
//            this.getData().setAnswer(null);
//
//            long startTime = date.getTime();
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(thread);
//            while(this.getData().getAnswer() == null && date.getTime() - startTime < timeout){
//                Thread.sleep(200);
//            }
//
//            executor.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return (this.getData().getAnswer() != null) ? Hex.encodeHexString( this.getData().getAnswer() ) : "Error";
    }

}
