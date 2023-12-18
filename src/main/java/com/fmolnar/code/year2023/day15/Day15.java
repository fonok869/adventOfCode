package com.fmolnar.code.year2023.day15;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class Day15 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day15/input.txt");

        long sum = 0;
        Map<Integer, List<HashLens>> hashMapLense = new HashMap<>();
        IntStream.range(0, 256).forEach(i -> hashMapLense.put(i, new ArrayList<>()));

        for (String line : lines) {
            String[] sor = line.split(",");
            for (int i = 0; i < sor.length; i++) {
                sum += calculateHash(sor[i], hashMapLense);
            }
        }
        long sumAll = 0l;
        for (Map.Entry<Integer, List<HashLens>> entry : hashMapLense.entrySet()) {
            Integer actualIndex = entry.getKey();
            List<HashLens> lenses = entry.getValue();
            for (int slot = 0; slot < lenses.size(); slot++) {
                sumAll += (actualIndex+1) * (slot + 1) * lenses.get(slot).lens;
            }
        }
        System.out.println("Sum : " + sum);
        System.out.println("Second : " + sumAll);
    }

    private static long calculateHash(String s, Map<Integer, List<HashLens>> hashMapLense) {
        int actualValue = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '=') {
                //System.out.println("Box : = " + actualValue + " " + s);
                HashLens hashLens = new HashLens(s.substring(0, s.indexOf('=')), Integer.valueOf(s.substring(s.indexOf('=') + 1)));
                int index = hashMapLense.get(actualValue).indexOf(hashLens);
                if (index != -1) {
                    hashMapLense.get(actualValue).remove(index);
                    hashMapLense.get(actualValue).add(index, hashLens);
                } else {
                    hashMapLense.get(actualValue).add(hashLens);
                }
                return actualValue;
            }
            if (s.charAt(i) == '-') {
                //System.out.println("Box : - " + actualValue + " " + s);
                hashMapLense.get(actualValue).remove(new HashLens(s.substring(0, s.indexOf('-')), 0));
                return actualValue;
            }
            int aschiiActual = s.charAt(i);
            actualValue += ((aschiiActual));
            actualValue = (actualValue * 17) % 256;
        }
        return actualValue;
    }

    record HashLens(String string, int lens) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashLens hashLens = (HashLens) o;
            return Objects.equals(string, hashLens.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string);
        }
    }

    ;
}
