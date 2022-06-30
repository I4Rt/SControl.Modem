package com.i4rt.demo.Algorithms;

import com.i4rt.demo.model.DataBin;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;

public class CRC16 {
    public static byte[] addCRC16(byte[] bytes) throws IOException, InterruptedException {
        String result = getCRC2(bytes);
        String print = "";
        for (byte i: bytes){
            String hex = Integer.toHexString(i);
            //print += "\\u00";
            if (hex.length() == 1) print += 0;
            print += hex;

        }
        print += result;
        System.out.println(print);
        byte[] crc = DataBin.convertDataFromHexStrToByteArray(print);
        byte[] finalDataBin = DataBin.convertDataFromHexStrToByteArray(Hex.encodeHexString(bytes) + print);
        return finalDataBin;
        //Thread.sleep(5000);
    }

    public static String getCRC2(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= (int) bytes[i];
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) == 1) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        //Exchange high low, low in front, high in back
        //CRC = ((CRC & 0x0000FF00) >> 8) | ((CRC & 0x000000FF) << 8);
        String result = Integer.toHexString(CRC);
        return result;
    }
}
