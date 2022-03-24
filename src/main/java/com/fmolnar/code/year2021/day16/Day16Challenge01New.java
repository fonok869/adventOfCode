package com.fmolnar.code.year2021.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day16Challenge01New {

    private List<String> lines = new ArrayList<>();
    private List<Integer> versions = new ArrayList<>();
    private String allLineHexa = "";
    private int index=0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day18/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {

                    for (int i = 0; i < line.length(); i++) {
                        allLineHexa = allLineHexa + transformToBinary(line.charAt(i));
                    }
                }
            }
        }

        treatAllHexa(allLineHexa, 0, allLineHexa.length()+1, false, 0);


        System.out.println("Sum: " + versions.stream().mapToInt(s->s).sum());
    }

    private int treatAllHexa(String allLineHexa, int beginIndex, int maxLength, boolean oneliteralValue, int initiali) {

        boolean isOnePackage = false;
        for (int i = beginIndex; ; ) {
            if (i >= maxLength) {
                return i;
            }
            if (allLineHexa.substring(i).matches("^0+")) {
                return i;
            }
            if(isOnePackage && oneliteralValue){
                return i;
            }
            int packetVersion = transformToDecimal(allLineHexa.substring(i, i+3));
            int typeID = transformToDecimal(allLineHexa.substring(i+3,i+6));
            System.out.println("Version number: " + packetVersion + " tyepID: " + typeID);
            versions.add(packetVersion);
            if (typeID == 4) {
                i = i + 6;
                String subPacket = allLineHexa.substring(i, i + 5);
                String allSubPacketValue = "";
                for (; ; ) {
                    allSubPacketValue = allSubPacketValue + subPacket.substring(1);
                    if (subPacket.startsWith("0")) {
                        System.out.println("i: " + (i + initiali) + " Literal Value: " + transformToDecimal(allSubPacketValue));
                        isOnePackage = true;
                        i = i + 5;
                        break;
                    }
                    i = i + 5;
                    subPacket = allLineHexa.substring(i, i + 5);
                }
            } else {
                char lengthTypeID = allLineHexa.charAt(i+6);
                i = i + 7;
                if (lengthTypeID == '0') {
                    System.out.println("i: " + i  + " lengthTypeID = 0: " + allLineHexa.substring(i, i + 15));
                    int totalLength0 = transformToDecimal(allLineHexa.substring(i, i + 15));
                    treatAllHexa(allLineHexa, i + 15, i + 15 + totalLength0, false, (i + initiali));
                    i = i + 15 + totalLength0;
                    System.out.println(" TypeID = 0: i: " + i);
                } else {
                    int nbLiteralValue = transformToDecimal(allLineHexa.substring(i, i + 11));
                    System.out.println("i: " + i  + " lengthTypeID = 1: " + allLineHexa.substring(i, i + 11));
                    i = i+11;
                    for(int nbSubPackage = 0; nbSubPackage<nbLiteralValue; nbSubPackage++){
                        i = treatAllHexa(allLineHexa, i, allLineHexa.length(), true, (i + initiali));
                    }
                }
                isOnePackage = true;
            }

        }

    }

    public int transformToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public String transformToBinary(char ch) {
        switch (ch) {
            case '0':
                return "0000";
            case '1':
                return "0001";
            case '2':
                return "0010";
            case '3':
                return "0011";
            case '4':
                return "0100";
            case '5':
                return "0101";
            case '6':
                return "0110";
            case '7':
                return "0111";
            case '8':
                return "1000";
            case '9':
                return "1001";
            case 'A':
                return "1010";
            case 'B':
                return "1011";
            case 'C':
                return "1100";
            case 'D':
                return "1101";
            case 'E':
                return "1110";
            case 'F':
                return "1111";
            default:
                throw new RuntimeException("Nincs megfelelo");
        }
    }
}
