package com.fmolnar.code.year2021.day24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24Simple01 {

    Map<Integer, Map<Integer, Integer>> mapLines = new HashMap<>();
    Map<Integer, Boolean> bools = new HashMap<>();
    Map<Integer, Integer> inputs = new HashMap<>();
    List<Element> lists = new ArrayList<>();


    public void calculate() {
        init();
        initBool();
        int z = 0;
        int[] MONAD = new int[14];
        ArrayDeque<Element> stack = new ArrayDeque<>();
        for (int i = 1; i < 15; i++) {


            Map.Entry<Integer, Integer> values = mapLines.get(i).entrySet().iterator().next();
            if (bools.get(i)) {
                stack.push(new Element(values.getKey(), values.getValue(), i-1));
            } else {
                int currentIdx = i-1;
                Element prev = stack.pop();
                if ((prev.getyInput() + values.getKey()) >= 0) {
                    MONAD[currentIdx] = 9;
                    MONAD[prev.getPosition()] = MONAD[currentIdx] - (prev.getyInput() + values.getKey());
                } else {
                    MONAD[prev.getPosition()] = 9;
                    MONAD[currentIdx] = MONAD[prev.getPosition()] + (prev.getyInput() + values.getKey());
                }

                System.out.println("Diveded by 26: " + "xinput (<10) : " + values.getKey() + " yValue: " + values.getValue());
            }


        }
        StringBuilder answer = new StringBuilder();
        Arrays.stream(MONAD).forEach(answer::append);
        System.out.println("Answer: " + answer);

    }

    private void initBool() {
        bools.put(1, true);
        bools.put(2, true);
        bools.put(3, true);
        bools.put(4, true);
        bools.put(5, false);
        bools.put(6, true);
        bools.put(7, true);
        bools.put(8, false);
        bools.put(9, true);
        bools.put(10, false);
        bools.put(11, false);
        bools.put(12, false);
        bools.put(13, false);
        bools.put(14, false);
    }

    private void init() {
        mapLines.put(1, getMapFrom(12, 6));
        mapLines.put(2, getMapFrom(11, 12));
        mapLines.put(3, getMapFrom(10, 5));
        mapLines.put(4, getMapFrom(10, 10));
        mapLines.put(5, getMapFrom(-16, 7));
        mapLines.put(6, getMapFrom(14, 0));
        mapLines.put(7, getMapFrom(12, 4));
        mapLines.put(8, getMapFrom(-4, 12));
        mapLines.put(9, getMapFrom(15, 14));
        mapLines.put(10, getMapFrom(-7, 13));
        mapLines.put(11, getMapFrom(-8, 10));
        mapLines.put(12, getMapFrom(-4, 11));
        mapLines.put(13, getMapFrom(-15, 9));
        mapLines.put(14, getMapFrom(-8, 9));

        for(int i=1; i<15;i++){
            Map.Entry<Integer, Integer> entry= mapLines.get(i).entrySet().iterator().next();
            lists.add(new Element(entry.getKey(), entry.getValue(), i-1));
        }
    }

    class Element {
        private int xInput;
        private int yInput;
        private int position;

        public Element(int xInput, int yInput, int position) {
            this.xInput = xInput;
            this.yInput = yInput;
            this.position = position;
        }

        public int getxInput() {
            return xInput;
        }

        public int getyInput() {
            return yInput;
        }

        public int getPosition() {
            return position;
        }
    }

    private Map<Integer, Integer> getMapFrom(int xinput, int yinput) {
        Map<Integer, Integer> integs = new HashMap<>();
        integs.put(xinput, yinput);
        return integs;
    }
}
