package com.i4rt.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.util.ArrayList;


// Пока работает для регистров прибора

@Setter
@Getter
@ToString
@NoArgsConstructor
public class DataBin {

    private static DataBin instance;

    private static int position;

    private static String positionHex;

    private static String rowDataBin;

    private static String mainPartHex;

    private static String resultData = "00018000000D314300";

    private static byte[] data;

    private String mode;

    public DataBin(int position, String rowDataBin, String mode) {
        this.mode = mode;

        this.position = position;
        this.rowDataBin = rowDataBin;

        this.mainPartHex = convertBinToHex(rowDataBin);
        this.positionHex = convertDemToHex(position);

        this.resultData = "00018000000D3143" + ((this.mode.equals("modem")) ? "00" : "41") + this.positionHex.substring(2, 4) + this.positionHex.substring(0, 2) + this.mainPartHex.substring(2, 4) + this.mainPartHex.substring(0, 2);
        System.out.println(this.resultData);

        this.data = generateDataToSend(this.resultData);
    }

    public static String convertBinToHex(String binaryStr){
        int decimal = Integer.parseInt(binaryStr.replaceAll(" ", ""),2);
        String hexStr = Integer.toString(decimal,16);
        if(hexStr.length() < 4){
            String strToAdd = "0".repeat(4 - hexStr.length());
            hexStr = strToAdd + hexStr;
        }
        return hexStr;
    }

    public static String convertDemToHex(String binaryStr){
        int decimal = Integer.parseInt(binaryStr.replaceAll(" ", ""),10);
        String hexStr = Integer.toString(decimal,16);
        if(hexStr.length() < 4){
            String strToAdd = "0".repeat(4 - hexStr.length());
            hexStr = strToAdd + hexStr;
        }
        return hexStr;
    }
    public static String convertDemToHex(int val){
        String hexStr = Integer.toString(val,16);
        if(hexStr.length() < 4){
            String strToAdd = "0".repeat(4 - hexStr.length());
            hexStr = strToAdd + hexStr;
        }
        return hexStr;
    }



    public static byte[] generateDataToSend(String rowData){
        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (rowData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(rowData.substring(i, i+2), 16));
        }
        byte[] data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }
        return data;
    }

    public static byte[] convertHexStringToBin(String rowData) {
        ArrayList<Byte> temp_data = new ArrayList<>();
        for (int i = 0; i <= (rowData.length()/2 - 1) * 2; i+=2){
            temp_data.add((byte) Integer.parseInt(rowData.substring(i, i+2), 16));
        }
        byte[] data = new byte[temp_data.size()];
        for(int i = 0; i < temp_data.size(); i++){
            data[i] = temp_data.get(i);
        }
        return data;
    }


    public String sendData() throws IOException {
        Receiver receiver = new Receiver("192.168.1.125", 30020);
        byte[] answer = receiver.sendData(this.data);
        return Hex.encodeHexString(answer);
    }

    public static String getResultData() {
        return resultData;
    }
}
