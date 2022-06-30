package com.i4rt.demo.model.algs;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

public class FixPoint {

    public static byte[] getFixPointDataFromStr(String data) {
        Long fixPointValue =  Math.round(Double.parseDouble(data) * 92233720);
        System.out.println("Converting " + data + "to fix point value");


        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(fixPointValue);
        System.out.println("Result: " + Hex.encodeHexString(buffer.array()));
        return buffer.array();
    }

    public static byte[] getFixPointDataFromDouble(Double val) {
        Long fixPointValue =  Math.round(val * 92233720);
        System.out.println("Converting " + val + "to fix point value");
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(fixPointValue);
        System.out.println("Result: " + Hex.encodeHexString(buffer.array()));
        return buffer.array();
    }




}
