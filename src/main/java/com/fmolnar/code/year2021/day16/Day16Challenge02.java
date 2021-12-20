package com.fmolnar.code.year2021.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day16Challenge02 {

    public static final int BINARY = 2;
    private List<String> lines = new ArrayList<>();
    private List<String> binaries = new ArrayList<>();
    private List<Long> packetsValues = new ArrayList<>();
    private List<Integer> packetsVersion = new ArrayList<>();
    List<Integer> distances = new ArrayList<>();

    private String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day16/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        transformToBinary();
        String binary = binaries.get(0);

        List<Long> sum = calculateNumbers(binary);


        System.out.println("Elem: " + findPackets(binary));

        System.out.println("Sum: " + sum.stream().mapToLong(s -> s).sum());
    }

    private long findPackets(String in) {
        long sum = 0;
        for (int i = 0; i < in.length(); ) {
            if (in.substring(i).chars().allMatch(e -> e == '0')) break;
            int version = binToDec(in.substring(i, i + 3));
            sum += version;
            int id = binToDec(in.substring(i + 3, i + 6));
            i += 6;
            if (id == 4) {
                for (; ; i += 5) {
                    if (in.charAt(i) == '0') {
                        i += 5;
                        break;
                    }
                }
            } else {
                int lengthLength = 15;
                boolean b = in.charAt(i) == '1';
                if (b) {
                    lengthLength = 11;
                }
                i++;
                int length = binToDec(in.substring(i, i + lengthLength));
                i += lengthLength;
                if (!b) {
                    sum += findPackets(in.substring(i, i + length));
                    i += length;
                }
            }
        }
        return sum;
    }

    int binToDec(String s) {
        return Integer.parseInt(new BigInteger(s, 2).toString(10));
    }

    private List<Long> calculateNumbers(String binary) {
        List<Long> sums = new ArrayList<>();

        for (int i = 0; i < binary.length(); ) {
            if (binary.substring(i).chars().allMatch(c -> c == '0')) {
                break;
            }

            // PacketVersion
            String packet = binary.substring(i, i + 3);
            int packetVersion = binToDec(packet);
            packetsVersion.add(packetVersion);

            // PacketType
            String typeID = binary.substring(i + 3, i + 6);
            int typeIDVersion = binToDec(typeID);

            i += 6;
            if (typeIDVersion == 4) {
                int steps = 5;
                String actual = "";
                for (; ; i = i + 5) {
                    String toExamine = binary.substring(i, i + steps);
                    if ('1' == toExamine.charAt(0)) {
                        actual = actual + toExamine.substring(1);
                    } else if ('0' == toExamine.charAt(0)) {
                        actual = actual + toExamine.substring(1);
                        i += 5;
                        break;
                    }
                }
                sums.add(Long.parseLong(actual, BINARY));
            } else {

                int lengthNow = 15;
                if (binary.charAt(i) == '1') {
                    lengthNow = 11;
                }
                i++;
                int realLength = binToDec(binary.substring(i, i + lengthNow));
                i += lengthNow;
                List<Long> numbers = new ArrayList<>();
                if (lengthNow == 15) {
                    numbers = calculateNumbers(binary.substring(i, i + realLength));
                    i += realLength;
                } else if(lengthNow == 11){
                       numbers =  calculateNumbers(binary.substring(i, (i + (lengthNow * realLength))));
                    i += (lengthNow * realLength);
                }
                sums.add(getOperators(typeIDVersion, numbers));

            }
        }
        return sums;
    }



    private Long getOperators(int typeIDVersion, List<Long> numbers) {
        switch (typeIDVersion) {
            case 0: {
                return numbers.stream().mapToLong(s -> s).sum();
            }
            case 1: {
                return numbers.stream().mapToLong(s -> s).reduce((a, b) -> a * b).getAsLong();
            }
            case 2: {
                return numbers.stream().mapToLong(s -> s).min().getAsLong();
            }
            case 3: {
                return numbers.stream().mapToLong(s -> s).max().getAsLong();
            }
            case 5: {
                return numbers.get(0) > numbers.get(1) ? 1L : 0L;
            }
            case 6: {
                return numbers.get(0) < numbers.get(1) ? 1L : 0L;
            }
            case 7: {
                return numbers.get(0) == numbers.get(1) ? 1L : 0L;
            }
        }
        return null;
    }

    private void transformToBinary() {
        for (String stringActual : lines) {
            binaries.add(hexToBin(stringActual));
        }
    }
}
