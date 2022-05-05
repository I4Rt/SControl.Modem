package com.i4rt.demo.model;

import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

@Table
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rowData;

    private byte[] data;

    private byte[] answer;

    @OneToOne(mappedBy = "data")
    private Register register;

    public Data(String rowData) {
        this.rowData = rowData;
        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (rowData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(rowData.substring(i, i+2), 16));
        }
        data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }

    }

    public void generateData(){
        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (rowData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(rowData.substring(i, i+2), 16));
        }
        data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }
    }

    public String sendData() throws IOException {
        Receiver receiver = new Receiver();
        answer = receiver.sendData(data);
        return Hex.encodeHexString( answer );
    }
}
