package com.fmolnar.code.year2022.day11;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day11Challenge01 {
    Map<Integer, Long> activities = new HashMap<>();

    public void calculate() throws IOException {
        List<Monkey> monkeys = new ArrayList<>();

        activities.put(0,0l);
        activities.put(1,0l);
        activities.put(2,0l);
        activities.put(3,0l);
        activities.put(4,0l);
        activities.put(5,0l);
        activities.put(6,0l);
        activities.put(7,0l);

        // Monkey 0
        LinkedList<Long> linkedlist = new LinkedList<>();
        linkedlist.add(93l);// 93, 54, 69, 66, 71
        linkedlist.add(54l);// 93, 54, 69, 66, 71
        linkedlist.add(69l);// 93, 54, 69, 66, 71
        linkedlist.add(66l);// 93, 54, 69, 66, 71
        linkedlist.add(71l);// 93, 54, 69, 66, 71
        monkeys.add(new Monkey(0,linkedlist, new Operation() {
            @Override
            public long transform(long i) {
                return i * 3;
            }
        }, 7, 7, 1));


        // Monkey 1
        LinkedList<Long> linkedlist1 = new LinkedList<>();
        linkedlist1.add(9l);// 9, 51, 80, 66
        linkedlist1.add(51l);// 9, 51, 80, 66
        linkedlist1.add(80l);// 9, 51, 80, 66
        linkedlist1.add(66l);// 9, 51, 80, 66

        monkeys.add(new Monkey(1,linkedlist1, new Operation() {
            @Override
            public long transform(long i) {
                return i * 17;
            }
        }, 19, 5, 7));


        // Monkey 2
        LinkedList<Long> linkedlist2 = new LinkedList<>();
        linkedlist2.add(90l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(92l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(63l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(91l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(96l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(63l); // 90, 92, 63, 91, 96, 63, 64
        linkedlist2.add(64l); // 90, 92, 63, 91, 96, 63, 64

        monkeys.add(new Monkey(2,linkedlist2, new Operation() {
            @Override
            public long transform(long i) {
                return i +1;
            }
        }, 13, 4, 3));

        // Monkey 3
        LinkedList<Long> linkedlist3 = new LinkedList<>();
        linkedlist3.add(5l);// 5, 77
        linkedlist3.add(77l);// 5, 77
        monkeys.add(new Monkey(3,linkedlist3, new Operation() {
            @Override
            public long transform(long i) {
                return i + 3;
            }
        }, 3, 4, 6));

        // Monkey 4
        LinkedList<Long> linkedlist4 = new LinkedList<>();
        linkedlist4.add(76l);// 76, 68, 94
        linkedlist4.add(68l);// 76, 68, 94
        linkedlist4.add(94l);// 76, 68, 94
        monkeys.add(new Monkey(4,linkedlist4, new Operation() {
            @Override
            public long transform(long i) {
                return i *i;
            }
        }, 2, 0, 6));

        // Monkey 5
        LinkedList<Long> linkedlist5 = new LinkedList<>();
        linkedlist5.add(86l);// 86, 65, 66, 97, 73, 83
        linkedlist5.add(65l);// 86, 65, 66, 97, 73, 83
        linkedlist5.add(66l);// 86, 65, 66, 97, 73, 83
        linkedlist5.add(97l);// 86, 65, 66, 97, 73, 83
        linkedlist5.add(73l);// 86, 65, 66, 97, 73, 83
        linkedlist5.add(83l);// 86, 65, 66, 97, 73, 83

        monkeys.add(new Monkey(5,linkedlist5, new Operation() {
            @Override
            public long transform(long i) {
                return i +8;
            }
        }, 11, 2, 3));

        // Monkey 6
        LinkedList<Long> linkedlist6 = new LinkedList<>();
        linkedlist6.add(78l);

        monkeys.add(new Monkey(6,linkedlist6, new Operation() {
            @Override
            public long transform(long i) {
                return i +6;
            }
        }, 17, 0, 1));

        // Monkey 7
        LinkedList<Long> linkedlist7 = new LinkedList<>();
        linkedlist7.add(89l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(57l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(59l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(61l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(87l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(55l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(55l); //89, 57, 59, 61, 87, 55, 55, 88
        linkedlist7.add(88l); //89, 57, 59, 61, 87, 55, 55, 88

        monkeys.add(new Monkey(7,linkedlist7, new Operation() {
            @Override
            public long transform(long i) {
                return i +7;
            }
        }, 5, 2, 5));

        for (int i = 0; i < 20; i++) {
            playRound(monkeys);
        }

        List<Long> acts = new ArrayList<>();

        for(Map.Entry<Integer, Long> actual : activities.entrySet()){
            acts.add(actual.getValue());
        }
        Collections.sort(acts);
        Collections.reverse(acts);

        System.out.println("First result: " + (acts.get(0)* acts.get(1)));

    }

    private void playRound(List<Monkey> monkeys) {
        for (int i = 0; i < monkeys.size(); i++) {
            Monkey monkey = monkeys.get(i);
            monkey.lepes(monkeys);
        }
    }

    class Monkey {
        int position;
        LinkedList<Long> items;
        Operation operation;
        long test;
        int trueA;
        int falseA;

        public Monkey(int position, LinkedList<Long> items, Operation operation, long test, int trueA, int dalseA) {
            this.position = position;
            this.items = items;
            this.operation = operation;
            this.test = test;
            this.trueA = trueA;
            this.falseA = dalseA;
        }

        public void lepes(List<Monkey> monkeys) {
            long ismetles = CollectionUtils.isEmpty(this.items) ? 0 : this.items.size();
            Long activity = activities.get(position);
            activity += ismetles;
            activities.put(position, activity);


            for (long i = 0; i < ismetles; i++) {
                Long actual = items.removeFirst();
                long actualNew = operation.transform(actual);
                actualNew = actualNew / 3;

                if ((actualNew % test) == 0) {
                    Monkey monkeyTrue = monkeys.get(trueA);
                    monkeyTrue.items.add(actualNew);
                } else {
                    Monkey monkeyFalse = monkeys.get(falseA);
                    monkeyFalse.items.add(actualNew);
                }

            }
        }
    }

    interface Operation {
        long transform(long i);
    }

}
