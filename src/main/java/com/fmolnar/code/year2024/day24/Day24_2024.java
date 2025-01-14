package com.fmolnar.code.year2024.day24;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24_2024 {

    private final List<String> lines;
    private final Map<String, Byte> xBytes = new HashMap<>();
    private final Map<String, Byte> yBytes = new HashMap<>();
    private final Set<String> zBytes = new HashSet<>();
    private final Map<String, Byte> allBytes = new HashMap<>();
    private final List<Operation> operations = new ArrayList<>();
    private final Set<String> startsWithZ = new HashSet<>();
    private final Set<String> gates = new HashSet<>();
    private Map<String, Operation> gatesAND = new HashMap<>();
    private Set<String> gatesANDNumbers = new HashSet<>();
    private Map<String, Operation> gatesXOR = new HashMap<>();
    private List<Operation> gateXORSNotDirectXY = new ArrayList<>();
    private List<Operation> gateANDSNotDirectXY = new ArrayList<>();
    private Map<String, Operation> gatesOR = new HashMap<>();
    private Set<String> gatesORNumbers = new HashSet<>();
    private Set<String> gatesXORNumbers = new HashSet<>();
    private Set<String> debugged = new HashSet<>();


    public Day24_2024() throws IOException {
        lines = AdventOfCodeUtils.readFile("/2024/day24/input.txt");

        boolean firstPart = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                firstPart = false;
                continue;
            }
            if (firstPart) {
                int space = line.indexOf(' ');
                int twoDots = line.indexOf(':');
                String first = line.substring(0, twoDots);
                Byte number = Byte.valueOf(line.substring(space + 1));
                if (line.startsWith("x")) {
                    xBytes.put(first, number);

                } else if (line.startsWith("y")) {
                    yBytes.put(first, number);
                }
                allBytes.put(first, number);
            }
            if (!firstPart) {
                int index = line.indexOf('>');
                String[] firstEquation = line.substring(0, index - 2).trim().split(" ");
                String resultString = line.substring(index + 1).trim();
                String operationSign = firstEquation[1];
                String firstNumber = firstEquation[0];
                String secondNumber = firstEquation[2];
                Operation operation = new Operation(firstNumber, secondNumber, operationSign, resultString);
                operations.add(operation);
                checkIfStartsWithZ(firstNumber, secondNumber, resultString);
                gates.add(resultString);


                if ("AND".equals(operationSign)) {
                    gatesAND.put(resultString, operation);
                    gatesANDNumbers.add(firstNumber);
                    gatesANDNumbers.add(secondNumber);
                }

                if ("OR".equals(operationSign)) {
                    gatesOR.put(resultString, operation);
                    gatesORNumbers.add(firstNumber);
                    gatesORNumbers.add(secondNumber);
                }

                if ("XOR".equals(operationSign)) {
                    gatesXOR.put(resultString, operation);
                    gatesXORNumbers.add(firstNumber);
                    gatesXORNumbers.add(secondNumber);
                }

            }
        }
    }

    private void checkIfStartsWithZ(String s, String s1, String resultString) {
        extracted(s);
        extracted(s1);
        extracted(resultString);

    }

    private void extracted(String s) {
        if (s.startsWith("z")) {
            startsWithZ.add(s);
        }
    }

    public void calculateDay24_2024() throws IOException {

        while (true) {
            for (Operation operation : operations) {
                makeOperation(operation);
            }

            if (isDone()) {
                break;
            }
        }

        // Each Z has to have XOR
        operations.stream().filter(operation -> operation.resultNumber().startsWith("z"))
                .filter(operation -> !"XOR".equals(operation.operation()))
                .filter(operation -> !"z45".equals(operation.resultNumber()))
                .forEach(operation -> debugged.add(operation.resultNumber()));


        // XOR
        for (Map.Entry<String, Operation> orOperation : gatesXOR.entrySet()) {
            Operation or = orOperation.getValue();
            String n1 = or.number1();
            String n2 = or.number2();
            String result = or.resultNumber();
            String op = or.operation();
            if ((isStartWithXY00(n1, n2) || isStartWithXY00(n2, n1)) &&
                    "XOR".equals(or.operation()) && "z00".equals(or.resultNumber())) {
                // x00 XOR y00 = z00
            } else if (isStartWithXY(n1, n2) || isStartWithXY(n2, n1)) {
                // xqq + yqq != z
                // Z -vel nem vegzodhet
                if (result.startsWith("z") && !"z00".equals(result)) {
                    debugged.add(result);
                    System.out.println("Nem jo mert x es y nem lehet z: " + op);
                } else {
                    if (!gatesANDNumbers.contains(result)) {
                        System.out.println("Carrier nem AND-del folytatja: " + or);
                        debugged.add(result);
                    } else {
                        Optional<Operation> andOperationResult = operations.stream()
                                .filter(operation -> (result.equals(operation.number1()) || result.equals(operation.number2())) &&
                                        "AND".equals(operation.operation())).filter(res -> !gatesORNumbers.contains(res.resultNumber())).findFirst();
                        if (andOperationResult.isPresent()) {
                            System.out.println("And Not finished By OR: " + andOperationResult.get());
                            debugged.add(andOperationResult.get().resultNumber());
                        }
                    }
                }

            } else if (result.startsWith("z") && !"z00".equals(result)) {

            } else {
                System.out.println("Nem jo nem Z-vel vegzodik: " + or);
                debugged.add(or.resultNumber());
            }
        }


        //  AND --> OR
        for (Map.Entry<String, Operation> andOperator : gatesAND.entrySet()) {
            Operation and = andOperator.getValue();
            String result = and.resultNumber();
            if (isStartWithXY(and.number1(), and.number2()) || isStartWithXY(and.number2(), and.number1())) {
                if (isStartWithXY00(and.number1(), and.number2()) || isStartWithXY00(and.number2(), and.number1())) {
                    // no carrier  --> 3 ands
                } else if (!gatesORNumbers.contains(and.resultNumber()) && !"z45".equals(result)) {
                    System.out.println("Problem After AND --> OR is missing: " + and);
                    debugged.add(and.resultNumber());
                }
            }
        }


        List<String> zStrings = allBytes.keySet().stream().filter(s -> s.startsWith("z")).sorted(Comparator.reverseOrder()).toList();


        String allXStrings = allBytes.keySet().stream().filter(s -> s.startsWith("x")).sorted(Comparator.reverseOrder()).map(key -> String.valueOf(allBytes.get(key))).collect(Collectors.joining());
        String allYStrings = allBytes.keySet().stream().filter(s -> s.startsWith("y")).sorted(Comparator.reverseOrder()).map(key -> String.valueOf(allBytes.get(key))).collect(Collectors.joining());

        System.out.println("X: " + Long.parseLong(allXStrings, 2));
        System.out.println("Y: " + Long.parseLong(allYStrings, 2));

        String binaries = zStrings.stream().map(s -> allBytes.get(s).toString()).collect(Collectors.joining());

        System.out.println("First: " + Long.parseLong(binaries, 2));

        String output = debugged.stream().sorted().collect(Collectors.joining(","));
        System.out.println("Second: " + output);
    }

    private static boolean isStartWithXY00(String n1, String n2) {
        return "x00".equals(n1) && "y00".equals(n2);
    }

    private static boolean isStartWithXY(String n1, String n2) {
        return n1.startsWith("x") && n2.startsWith("y");
    }

    private boolean isDone() {
        return allBytes.keySet().containsAll(startsWithZ);
    }

    void makeOperation(Operation op) {

        if (!allBytes.containsKey(op.number1()) || !allBytes.containsKey(op.number2())) {
            return;
        }
        int value = switch (op.operation()) {
            case "AND" -> (allBytes.get(op.number1()) & allBytes.get(op.number2()));
            case "XOR" -> (allBytes.get(op.number1()) ^ allBytes.get(op.number2()));
            case "OR" -> ((allBytes.get(op.number1()) | allBytes.get(op.number2())));
            default -> throw new RuntimeException("Nincs kezelve");
        };
        Byte byteNow = null;
        if (value == 0 || value == 2) {
            byteNow = 0;
        } else if (value == 1) {
            byteNow = 1;
        } else {
            new RuntimeException("Totto");
        }
        allBytes.put(op.resultNumber(), byteNow);
    }
}

record Operation(String number1, String number2, String operation, String resultNumber) {

}
