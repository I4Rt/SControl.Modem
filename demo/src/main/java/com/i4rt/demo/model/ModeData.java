package com.i4rt.demo.model;

import com.i4rt.demo.model.Threads.ReceiverReadingMode;
import com.i4rt.demo.model.Threads.ReceiverSettingMode;
import lombok.*;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ModeData {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "modeData")
    private Mode mode;

    public void setModeRegisterValueBin(String modeRegisterValueBin) {
        this.modeRegisterValueBin = modeRegisterValueBin;

        this.modeRegisterValueHex = DataBin.convertBinToHex(modeRegisterValueBin);
        this.resultData = "00018000000B314200" + modeRegisterValueHex.substring(2, 4) + modeRegisterValueHex.substring(0, 2);

        System.out.println();
        System.out.println(resultData);
        System.out.println();

        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (resultData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(resultData.substring(i, i+2), 16));
        }
        data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }

        System.out.println();
        System.out.println(Hex.encodeHexString(data));
        System.out.println();
    }

    private String modeRegisterValueBin;
    private String modeRegisterValueHex;

    private String resultData = "00018000000B314200";
    private byte[] data;
    private byte[] answer;



    public ModeData(String modeRegisterValueBin){
        this.modeRegisterValueBin = modeRegisterValueBin;

        this.modeRegisterValueHex = DataBin.convertBinToHex(modeRegisterValueBin);
        this.resultData = "00018000000B314200" + modeRegisterValueHex.substring(2, 4) + modeRegisterValueHex.substring(0, 2);


        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (resultData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(resultData.substring(i, i+2), 16));
        }
        data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }
    }

    public Boolean sendModeData(){
            try{
                this.mode.setSet(false);

                Thread thread = new Thread(new ReceiverSettingMode(this.getMode()));
                Thread readThread = new Thread(new ReceiverReadingMode(this.getMode()));

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(thread);
                Thread.sleep(5000);
                executor.shutdown();

                int setTrys = 0;
                while( this.mode.isSet() != true){
                    setTrys += 1;
                    if(setTrys > 30)
                    {
                        return false;
                    }
                    System.out.println(this.mode.isSet());
                    ExecutorService executor_reader = Executors.newSingleThreadExecutor();
                    executor_reader.execute(readThread);
                    Thread.sleep(400);

                    executor_reader.shutdown();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        return true;
    }

}
