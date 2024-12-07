package com.fmolnar.code.year2023.day16;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day16 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day16/input.txt");

        int yMax = lines.size();
        int xMax = lines.get(0).length();
        Set<Point> fuggoleges = new HashSet<>();
        Set<Point> vizszintes = new HashSet<>();
        Set<Point> jobbraDolt = new HashSet<>();
        Set<Point> balraDolt = new HashSet<>();

        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < xMax; i++) {
                char charActual = line.charAt(i);
                switch (charActual) {
                    case '|':
                        fuggoleges.add(new Point(j, i));
                        break;
                    case '-':
                        vizszintes.add(new Point(j, i));
                        break;
                    case '/':
                        jobbraDolt.add(new Point(j, i));
                        break;
                    case '\\':
                        balraDolt.add(new Point(j, i));
                        break;
                }
            }
        }

        List<Beam> hypoteticalStartingPoint = new ArrayList<>();
        for (int i = 0; i < xMax; i++) {
            hypoteticalStartingPoint.add(new Beam(Direction.S, new Point(0, i)));
            hypoteticalStartingPoint.add(new Beam(Direction.N, new Point(yMax - 1, i)));
        }

        for (int j = 0; j < yMax; j++) {
            hypoteticalStartingPoint.add(new Beam(Direction.E, new Point(j, 0)));
            hypoteticalStartingPoint.add(new Beam(Direction.W, new Point(j, xMax - 1)));
        }

        Set<Integer> maxEnegized = new HashSet<>();

        hypoteticalStartingPoint.forEach(beamActual -> {

            Set<Point> energized = new HashSet<>();

            Set<Beam> startingBeam = new HashSet<>();
            startingBeam.add(beamActual);
            Set<Beam> allStartingBeam = new HashSet<>();
            allStartingBeam.add(beamActual);

            for (int lepes = 1; lepes < 10000; lepes++) {
                Set<Beam> newStartingBeam = new HashSet<>();
                startingBeam.forEach(
                        beam -> {
                            Point nextPoint = beam.point;
                            energized.add(nextPoint);
                            switch (beam.direction) {
                                case N: {
                                    if (fuggoleges.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.N, new Point(beam.point.y - 1, beam.point.x)));
                                    } else if (vizszintes.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(nextPoint.y, nextPoint.x + 1)));
                                        newStartingBeam.add(new Beam(Direction.W, new Point(nextPoint.y, nextPoint.x - 1)));
                                    } else if (jobbraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(nextPoint.y, nextPoint.x + 1)));
                                    } else if (balraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.W, new Point(nextPoint.y, nextPoint.x - 1)));
                                    } else {
                                        newStartingBeam.add(new Beam(Direction.N, new Point(beam.point.y - 1, beam.point.x)));
                                    }
                                }
                                break;
                                case S: {
                                    if (fuggoleges.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(beam.point.y + 1, beam.point.x)));
                                    } else if (vizszintes.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(nextPoint.y, nextPoint.x + 1)));
                                        newStartingBeam.add(new Beam(Direction.W, new Point(nextPoint.y, nextPoint.x - 1)));
                                    } else if (jobbraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.W, new Point(nextPoint.y, nextPoint.x - 1)));
                                    } else if (balraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(nextPoint.y, nextPoint.x + 1)));
                                    } else {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(beam.point.y + 1, beam.point.x)));
                                    }
                                }
                                break;
                                case E: {
                                    if (vizszintes.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(beam.point.y, beam.point.x + 1)));
                                    } else if (fuggoleges.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(nextPoint.y + 1, nextPoint.x)));
                                        newStartingBeam.add(new Beam(Direction.N, new Point(nextPoint.y - 1, nextPoint.x)));
                                    } else if (jobbraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.N, new Point(nextPoint.y - 1, nextPoint.x)));
                                    } else if (balraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(nextPoint.y + 1, nextPoint.x)));
                                    } else {
                                        newStartingBeam.add(new Beam(Direction.E, new Point(beam.point.y, beam.point.x + 1)));
                                    }

                                }
                                break;
                                case W: {
                                    if (vizszintes.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.W, new Point(beam.point.y, beam.point.x - 1)));
                                    } else if (fuggoleges.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(nextPoint.y + 1, nextPoint.x)));
                                        newStartingBeam.add(new Beam(Direction.N, new Point(nextPoint.y - 1, nextPoint.x)));
                                    } else if (jobbraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.S, new Point(nextPoint.y + 1, nextPoint.x)));
                                    } else if (balraDolt.contains(nextPoint)) {
                                        newStartingBeam.add(new Beam(Direction.N, new Point(nextPoint.y - 1, nextPoint.x)));
                                    } else {
                                        newStartingBeam.add(new Beam(Direction.W, new Point(beam.point.y, beam.point.x - 1)));
                                    }
                                }
                                break;
                            }

                        }
                );

                Set<Beam> insideBeams = newStartingBeam.stream().filter(p -> 0 <= p.point.x && p.point.x < xMax && 0 <= p.point.y && p.point.y < yMax).collect(Collectors.toSet());
                if (allStartingBeam.containsAll(insideBeams)) {
                    maxEnegized.add(energized.size());
                    break;
                }
                allStartingBeam.addAll(insideBeams);
                energized.addAll(insideBeams.stream().map(beam -> beam.point).collect(Collectors.toSet()));
//            for (Beam beam : insideBeams) {
//                System.out.println(beam.point + " D: " + beam.direction);
//            }
                startingBeam.clear();
                startingBeam.addAll(insideBeams);
                newStartingBeam.clear();

            }
        });

        System.out.println("Result: " + maxEnegized.stream().mapToInt(s -> s).max().getAsInt());

    }

    record Beam(Direction direction, Point point) {
    }

    record Point(int y, int x) {
    }

    ;

    enum Direction {
        N,
        E,
        W,
        S
    }
}
