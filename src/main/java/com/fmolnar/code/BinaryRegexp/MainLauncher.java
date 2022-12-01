package com.fmolnar.code.BinaryRegexp;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MainLauncher {

    public static void main(String[] args) {
        String text = "Elment a Lidi neni a vasarba csuhajja";
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        String bites = "";
        byte[] array = buffer.array();
        for (int i = 0; i < array.length; i++) {
            bites = bites + array[i] + "_";
        }
        System.out.println(bites);

        String[] splitted = bites.split("_");

        byte[] bitsToShow = new byte[splitted.length];
        for(int j=0; j<splitted.length; j++){
            bitsToShow[j] = Byte.valueOf(splitted[j]);
        }

        String converted = new String(bitsToShow,StandardCharsets.UTF_8);
        System.out.println("Converted: " + converted);
    }
}
