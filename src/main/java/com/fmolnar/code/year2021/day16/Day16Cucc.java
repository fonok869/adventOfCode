package com.fmolnar.code.year2021.day16;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16Cucc {

    private static final Map<String, String> hexadecimalToBinaryMap = new HashMap<>();
    private static String messageBinary;
    private static Packet outerPacket;
    private static int index = 0;

    public static void main(String[] args) {
        String path = "src/main/resources/2021/day16/input.txt";
        initMap();
        messageBinary = readMessage(path);
        outerPacket = nextPacket();
        long sum = calculateSumOfVersions(outerPacket, 0);
        System.out.printf("The sum of version numbers is: %d", sum);
        System.out.println();
        calculateValueOfPacket(outerPacket);
        long value = outerPacket.getLiteralValue();
        System.out.printf("The value of the outermost packet is %d", value);
        System.out.println();
    }

    private static void initMap() {
        hexadecimalToBinaryMap.put("0", "0000");
        hexadecimalToBinaryMap.put("1", "0001");
        hexadecimalToBinaryMap.put("2", "0010");
        hexadecimalToBinaryMap.put("3", "0011");
        hexadecimalToBinaryMap.put("4", "0100");
        hexadecimalToBinaryMap.put("5", "0101");
        hexadecimalToBinaryMap.put("6", "0110");
        hexadecimalToBinaryMap.put("7", "0111");
        hexadecimalToBinaryMap.put("8", "1000");
        hexadecimalToBinaryMap.put("9", "1001");
        hexadecimalToBinaryMap.put("A", "1010");
        hexadecimalToBinaryMap.put("B", "1011");
        hexadecimalToBinaryMap.put("C", "1100");
        hexadecimalToBinaryMap.put("D", "1101");
        hexadecimalToBinaryMap.put("E", "1110");
        hexadecimalToBinaryMap.put("F", "1111");
    }

    private static String readMessage(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int line;
            while ((line = reader.read()) != -1) {
                char c = (char) line;
                String binary = hexadecimalToBinaryMap.get("" + c);
                sb.append(binary);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(sb);
    }

    private static void extractLiteralPacket(Packet packet) {
        StringBuilder sb = new StringBuilder();
        int prefix = 0;
        do {
            prefix = Integer.parseInt(messageBinary.substring(index, index + 1));
            sb.append(messageBinary.substring(index + 1, index + 5));
            index += 5;
        } while (prefix != 0);
        System.out.println("i: "+ index + "  Literal Value: " + binaryToDecimal(String.valueOf(sb)));
        packet.setLiteralValue(binaryToDecimal(String.valueOf(sb)));
    }

    private static void extractOperatorPacket(Packet packet) {
        int lengthTypeID = Integer.parseInt(messageBinary.substring(index, index + 1));
        index++;
        if (lengthTypeID == 0) {
            System.out.println("i: "+ index +" TypeID = 0: " + messageBinary.substring(index, index + 15));
            long length = binaryToDecimal(messageBinary.substring(index, index + 15));
            index += 15;
            long limit = index + length;
            while (limit > index) {
                packet.addSubPacket(nextPacket());
            }
        } else if (lengthTypeID == 1) {
            System.out.println("i: "+ index +" TypeID = 1: " + messageBinary.substring(index, index + 11));
            long number = binaryToDecimal(messageBinary.substring(index, index + 11));
            index += 11;
            for (int i = 0; i < number; i++) {
                packet.addSubPacket(nextPacket());
            }
        }
    }

    private static Packet nextPacket() {
        long version = binaryToDecimal(messageBinary.substring(index, index + 3));
        long typeID = binaryToDecimal(messageBinary.substring(index + 3, index + 6));
        index += 6;
        Packet newPacket = new Packet(version, typeID);
        if (typeID == 4) {
            extractLiteralPacket(newPacket);
        } else {
            extractOperatorPacket(newPacket);
        }
        return newPacket;
    }

    private static long calculateSumOfVersions(Packet packet, long sum) {
        for (Packet p : packet.getPackets()) {
            sum = calculateSumOfVersions(p, sum);
            System.out.println("Sum: " + sum);
        }
        return sum + packet.getVersion();
    }

    private static long calculateValueOfPacket(Packet packet) {
        switch ((int) packet.getTypeId()) {
            case 0:
                return sumSubPackets(packet);
            case 1:
                return productSubPackets(packet);
            case 2:
                return miminumSubPackets(packet);
            case 3:
                return maximumSubPackets(packet);
            case 4:
                return packet.getLiteralValue();
            case 5:
                return greaterThatSubPackets(packet);
            case 6:
                return lessThanSubPackets(packet);
            case 7:
                return equalsSubPackets(packet);
            default: return 0;
        }
    }

    private static long sumSubPackets(Packet packet) {
        long sum = 0L;
        for (Packet p : packet.getPackets()) {
            sum += calculateValueOfPacket(p);
        }
        packet.setLiteralValue(sum);
        return sum;
    }

    private static long productSubPackets(Packet packet) {
        long multi = 1L;
        for (Packet p : packet.getPackets()) {
            multi *= calculateValueOfPacket(p);
        }
        packet.setLiteralValue(multi);
        return multi;
    }

    private static long miminumSubPackets(Packet packet) {
        long minValue = Long.MAX_VALUE;
        for (Packet p : packet.getPackets()) {
            long value = calculateValueOfPacket(p);
            minValue = value < minValue ? value : minValue;
        }
        packet.setLiteralValue(minValue);
        return minValue;
    }

    private static long maximumSubPackets(Packet packet) {
        long maxValue = Long.MIN_VALUE;
        for (Packet p : packet.getPackets()) {
            long value = calculateValueOfPacket(p);
            maxValue = value > maxValue ? value : maxValue;
        }
        packet.setLiteralValue(maxValue);
        return maxValue;
    }

    private static long greaterThatSubPackets(Packet packet) {
        long firstValue = calculateValueOfPacket(packet.getPackets().get(0));
        long secondValue = calculateValueOfPacket(packet.getPackets().get(1));
        long res = firstValue > secondValue ? 1 : 0;
        packet.setLiteralValue(res);
        return res;
    }

    private static long lessThanSubPackets(Packet packet) {
        long firstValue = calculateValueOfPacket(packet.getPackets().get(0));
        long secondValue = calculateValueOfPacket(packet.getPackets().get(1));
        long res = firstValue < secondValue ? 1 : 0;
        packet.setLiteralValue(res);
        return res;
    }

    private static long equalsSubPackets(Packet packet) {
        long firstValue = calculateValueOfPacket(packet.getPackets().get(0));
        long secondValue = calculateValueOfPacket(packet.getPackets().get(1));
        long res = firstValue == secondValue ? 1 : 0;
        packet.setLiteralValue(res);
        return res;
    }

    private static long binaryToDecimal(String binary) {
        String num = binary;
        long decimal = 0L;
        long base = 1;
        int length = num.length();
        for (int i = length - 1; i >= 0; i--) {
            if (num.charAt(i) == '1') {
                decimal += base;
            }
            base = base * 2;
        }
        return decimal;
    }
}

class Packet {

    private final long version;
    private final long typeId;
    private long literalValue;
    private final List<Packet> packets = new ArrayList<>();

    public Packet(long version, long typeId) {
        System.out.println("Version number: " + version + " tyepID: " + typeId);
        this.version = version;
        this.typeId = typeId;
    }

    public long getVersion() {
        return version;
    }

    public long getTypeId() {
        return typeId;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void addSubPacket(Packet subPacket) {
        packets.add(subPacket);
    }

    public long getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(long literalValue) {
        this.literalValue = literalValue;
    }
}
