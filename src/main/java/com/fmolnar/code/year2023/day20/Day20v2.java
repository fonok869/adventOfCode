package com.fmolnar.code.year2023.day20;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day20v2 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day20/input.txt");


        Map<String, FlipFlopModule> flips = new HashMap<>();
        Map<String, FlipFlopModule> flipsNewStep = new HashMap<>();
        Map<String, ConjModule> conjs = new HashMap<>();
        Map<String, ConjModule> conjsNewStep = new HashMap<>();
        BroadCaster broadCaster = null;

        for (String line : lines) {
            int indexOf = line.indexOf("-");
            String firstPArt = line.substring(0, indexOf - 1);
            String secondPart = line.substring(indexOf + 2);
            String[] outs = Arrays.stream(secondPart.split(",")).map(String::trim).toArray(String[]::new);
            ;
            if (line.startsWith("broadcaster")) {
                broadCaster = new BroadCaster(firstPArt, outs);
            } else if (line.startsWith("%")) {
                flips.put(firstPArt.substring(1), new FlipFlopModule(firstPArt.substring(1), outs, false, null));
            } else if (line.startsWith("&")) {
                conjs.put(firstPArt.substring(1), new ConjModule(firstPArt.substring(1), outs, new HashMap<>(), null));
            }
        }

        initAllAllConjModule(conjs, flips);

        for (Map.Entry<String, ConjModule> entryConj : conjs.entrySet()) {
            Arrays.stream(entryConj.getValue().outputs).forEach(s -> System.out.println(entryConj.getKey() + " "+s));
        }

        for (Map.Entry<String, FlipFlopModule> entryFlip : flips.entrySet()) {
            Arrays.stream(entryFlip.getValue().outputs).forEach(s -> System.out.println(entryFlip.getKey() + " "+s));

        }


        List<NewStep> touched = new ArrayList<>();

        long lowPulse = 0;
        long highPulse = 0;
        for (int i = 1; i <= 1000; i++) {
            Pulse pulseActual = null;
            List<NewStep> newTouched = new ArrayList<>();
            touched.add(new NewStep(Pulse.LOW, "broadcaster"));
            lowPulse++;
            while (true) {
                for (NewStep actualStep : touched) {
                    String[] outs;
                    String initName = actualStep.nextPulse();
                    pulseActual = actualStep.pulse;
                    if ("broadcaster".equals(initName)) {
                        outs = broadCaster.ouputs();
                    } else if (flips.containsKey(initName)) {
                        outs = flips.get(initName).outputs;
                    } else {
                        outs = conjs.get(initName).outputs;
                    }

                    for (String out : outs) {
                        if (flips.containsKey(out)) {
                            FlipFlopModule flipActual = flips.get(out);
                            if(Pulse.LOW == pulseActual){
                                lowPulse++;
                            } else {
                                highPulse++;
                            }
                            FlipFlopModule flipActualChanged = flipActual.treatPulse(pulseActual);
                            if (flipActualChanged == null) {
                                continue;
                            }
                            flipsNewStep.put(out, flipActualChanged);
                            newTouched.add(new NewStep(flipActualChanged.lastPulseOut, out));
                        } else if (conjs.containsKey(out)) {
                            ConjModule conjActual = conjs.get(out);
                            ConjModule conjModuleChanged = conjActual.treatPulse(initName, pulseActual);

                            if(Pulse.LOW == pulseActual){
                                lowPulse++;
                            } else {
                                highPulse++;
                            }

                            if(conjsNewStep.get(out) == null){
                                conjsNewStep.put(out, conjModuleChanged);
                            } else {
                                conjsNewStep.get(out).allHigh.put(initName, conjModuleChanged.lastPulseOut);
                            }
                            newTouched.add(new NewStep(conjModuleChanged.lastPulseOut, out));
                        } else {
                            if(Pulse.LOW == pulseActual){
                                lowPulse++;
                            } else {
                                highPulse++;
                            }
                        }
                    }
                }

                for (Map.Entry<String, ConjModule> entryConj : conjsNewStep.entrySet()) {
                    conjs.put(entryConj.getKey(), entryConj.getValue());
                }
                conjsNewStep.clear();

                for (Map.Entry<String, FlipFlopModule> entryFlip : flipsNewStep.entrySet()) {
                    flips.put(entryFlip.getKey(), entryFlip.getValue());
                }
                flipsNewStep.clear();

                if (newTouched.isEmpty()) {
                    break;
                }
                touched.clear();
                touched.addAll(newTouched);
                newTouched.clear();
            }

            touched.clear();
        }


        System.out.println("Sum : " + lowPulse*highPulse);


    }

    private static void initAllAllConjModule(Map<String, ConjModule> conjs, Map<String, FlipFlopModule> flips) {
        for (Map.Entry<String, ConjModule> conj : conjs.entrySet()) {
            // Feltetelezes nincs
            Set<String> containsOuts = new HashSet<>();
            String toSearchFor = conj.getKey();
            for (Map.Entry<String, ConjModule> conj2 : conjs.entrySet()) {
                ConjModule conjModule = conj2.getValue();
                if(Arrays.stream(conjModule.outputs).filter(out -> toSearchFor.equals(out)).findAny().isPresent()){
                    containsOuts.add(conj2.getKey());
                }
            }

            for (Map.Entry<String, FlipFlopModule> flip : flips.entrySet()) {
                FlipFlopModule flipModule = flip.getValue();
                if(Arrays.stream(flipModule.outputs).filter(out -> toSearchFor.equals(out)).findAny().isPresent()){
                    containsOuts.add(flip.getKey());
                }
            }
            if (!containsOuts.isEmpty()) {
                containsOuts.forEach(out ->
                        conj.getValue().allHigh.put(out, Pulse.LOW)
                );
            }
        }
    }

    record NewStep(Pulse pulse, String nextPulse) {
    }

    record BroadCaster(String name, String[] ouputs) {
    }

    record FlipFlopModule(String name, String[] outputs, boolean isOn, Pulse lastPulseOut) {

        FlipFlopModule treatPulse(Pulse pulse) {
            if (pulse == Pulse.HIGH) {
                return null;
            } else if (pulse == Pulse.LOW) {
                if (isOn) {
                    return new FlipFlopModule(name, outputs, !isOn, Pulse.LOW);
                } else {
                    return new FlipFlopModule(name, outputs, !isOn, Pulse.HIGH);
                }
            }
            return null;
        }
    }

    record ConjModule(String name, String[] outputs, Map<String, Pulse> allHigh, Pulse lastPulseOut) {
        ConjModule treatPulse(String initNam, Pulse pulse) {
            allHigh.put(initNam, pulse);
            if (allHigh.entrySet().stream().map(Map.Entry::getValue).allMatch(s -> Pulse.HIGH.equals(s))) {
                return new ConjModule(name, outputs, allHigh, Pulse.LOW);
            } else {
                return new ConjModule(name, outputs, allHigh, Pulse.HIGH);
            }
        }

    }

    ;

    enum Pulse {
        LOW,
        HIGH
    }
}
