package com.fmolnar.code.year2022.day11;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11Challenge02 {
    Map<Integer, BigInteger> activities = new HashMap<>();

    public void calculate() throws IOException {
        List<Monkey> monkeys = new ArrayList<>();

        activities.put(0,BigInteger.valueOf(0L));
        activities.put(1,BigInteger.valueOf(0L));
        activities.put(2,BigInteger.valueOf(0L));
        activities.put(3,BigInteger.valueOf(0L));
        activities.put(4,BigInteger.valueOf(0L));
        activities.put(5,BigInteger.valueOf(0L));
        activities.put(6,BigInteger.valueOf(0L));
        activities.put(7,BigInteger.valueOf(0L));

        // Monkey 1
        Map map1 = createMap(93, 54, 69, 66, 71);
        monkeys.add(new Monkey(0,map1, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.multiply(BigInteger.valueOf(3l));
            }
        }, BigInteger.valueOf(7l), BigInteger.valueOf(map1.size()),7, 1));


        // Monkey 2
        Map map2 = createMap(89, 51, 80, 66);
        monkeys.add(new Monkey(1,map2, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.multiply(BigInteger.valueOf(17l));
            }
        }, BigInteger.valueOf(19l), BigInteger.valueOf(map2.size()), 5, 7));


        // Monkey 3
        Map three= createMap(90, 92, 63, 91, 96, 63, 64);
        monkeys.add(new Monkey(2,three, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.add(BigInteger.ONE);
            }
        }, BigInteger.valueOf(13l), BigInteger.valueOf(three.size()), 4, 3));

        // Monkey 4
        Map map4 = createMap(65, 77);
        monkeys.add(new Monkey(3,map4, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.add(BigInteger.valueOf(2));
            }
        }, BigInteger.valueOf(3l), BigInteger.valueOf(map4.size()), 4, 6));

        // Monkey 5
        Map map5 = createMap(76, 68, 94);
        monkeys.add(new Monkey(4,map5, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.multiply(i);
            }
        }, BigInteger.valueOf(2l), BigInteger.valueOf(map5.size()), 0, 6));

        // Monkey 6
        Map map6 = createMap(86, 65, 66, 97, 73, 83);
        monkeys.add(new Monkey(5,map6, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.add(BigInteger.valueOf(8L));
            }
        }, BigInteger.valueOf(11l), BigInteger.valueOf(map6.size()), 2, 3));

        // Monkey 7
        Map map7 = createMap(78);
        monkeys.add(new Monkey(6,map7, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.add(BigInteger.valueOf(6L));
            }
        }, BigInteger.valueOf(17l), BigInteger.valueOf(map7.size()), 0, 1));

        // Monkey 8
        Map map8 = createMap(89, 57, 59, 61, 87, 55, 55, 88);
        monkeys.add(new Monkey(7,map8, new Operation() {
            @Override
            public BigInteger transform(BigInteger i) {
                return i.add(BigInteger.valueOf(7L));
            }
        }, BigInteger.valueOf(5l), BigInteger.valueOf(map8.size()), 2, 5));

        for (int i = 0; i < 10000; i++) {
            playRound(monkeys);
        }

        List<BigInteger> acts = get2MostImportant();
        System.out.println("Second result: " + (acts.get(0).multiply(acts.get(1))));

    }

    private List<BigInteger> get2MostImportant() {
        List<BigInteger> acts = new ArrayList<>();
        for(Map.Entry<Integer, BigInteger> actual : activities.entrySet()){
            acts.add(actual.getValue());
        }
        Collections.sort(acts);
        Collections.reverse(acts);
        return acts;
    }

    private Map<BigInteger, BigInteger> createMap(int... values) {
        Map<BigInteger, BigInteger> mapper = new HashMap<>();
        for(int i=0; i < values.length ; i++){
            mapper.put(BigInteger.valueOf(Long.valueOf(i)), BigInteger.valueOf(Long.valueOf(values[i])));
        }
        return mapper;
    }

    private void playRound(List<Monkey> monkeys) {
        for (int i = 0; i < monkeys.size(); i++) {
            lepes(monkeys.get(i), monkeys);
        }
    }

    public void lepes(Monkey monkeyActual, List<Monkey> monkeys) {
        long ismetles = idEmptyMap(monkeyActual.items) ? 0L : Long.valueOf(monkeyActual.items.size());
        BigInteger activity = activities.get(monkeyActual.position);
        activity = BigInteger.valueOf(ismetles).add(activity);
        activities.put(monkeyActual.position, activity);

        List<BigInteger> trues = new ArrayList<>();
        List<BigInteger> falses = new ArrayList<>();
        for (long i = 0; i < (ismetles); i++) {
            BigInteger actual = monkeyActual.items.get(BigInteger.valueOf(i));
            BigInteger actualNew = monkeyActual.operation.transform(actual);
            BigInteger[] result = actualNew.divideAndRemainder(monkeyActual.test);

            if (result[1].equals(BigInteger.ZERO)) {
                trues.add(actualNew);
            } else {
                falses.add(actualNew);
            }

        }

        addNewList(trues, monkeys.get(monkeyActual.trueA), monkeys);
        addNewList(falses, monkeys.get(monkeyActual.falseA), monkeys);

        monkeys.remove(monkeyActual.position);
        monkeys.add(monkeyActual.position, new Monkey(monkeyActual.position, new HashMap<>(), monkeyActual.operation,monkeyActual.test, BigInteger.ZERO, monkeyActual.trueA, monkeyActual.falseA));
    }

    private void addNewList(List<BigInteger> falses, Monkey monkey, List<Monkey> monkeys) {
        long nexTmaxValue = monkey.nextMaxValue.longValue();
        Map<BigInteger, BigInteger> newM = new HashMap<>(monkey.items);
        Long oszto = 5l*17l*11l*2l*3l*13l*19l*7l;
        for(long i=0; i<falses.size(); i++){
            BigInteger szam = falses.get((int) i).mod(BigInteger.valueOf(oszto));
            newM.put(BigInteger.valueOf(nexTmaxValue + i), szam == BigInteger.ZERO ? monkey.test : szam);
        }
        BigInteger newNextMaxValue = BigInteger.valueOf(nexTmaxValue + Long.valueOf(falses.size()));
        monkeys.remove(monkey.position);
        monkeys.add(monkey.position, new Monkey(monkey.position, newM, monkey.operation,monkey.test, newNextMaxValue, monkey.trueA, monkey.falseA));
    }

    record Monkey(int position, Map<BigInteger, BigInteger> items, Operation operation, BigInteger test, BigInteger nextMaxValue, int trueA, int falseA) {


    }
    private boolean idEmptyMap(Map<BigInteger, BigInteger> items) {
        if(items == null){
            return true;
        }

        if(items.size() == 0){
            return true;
        }

        return false;
    }


    public interface Operation {
        BigInteger transform(BigInteger i);
    }

}
