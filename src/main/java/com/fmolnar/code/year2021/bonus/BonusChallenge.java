package com.fmolnar.code.year2021.bonus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BonusChallenge {

    List<List<List<Integer>>> all = new ArrayList<>();
    List<Coordinate> coordinatesDirection = Arrays.asList(
            new Coordinate(0,0,1),
            new Coordinate(0,0,-1),
            new Coordinate(0,1,0),
            new Coordinate(0,-1,0),
            new Coordinate(1,0,0),
            new Coordinate(-1,0,0));
    List<Coordinate> locMins = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/bonus/input.txt");

        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            List<Integer> numbers = new ArrayList<>();
            String line;
            int z=0;
            List<List<Integer>> zSlide = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    for (int i = 0; i < line.length(); i++) {
                        if (i == line.length() - 1) {
                            numbers.add(Integer.valueOf(line.substring(i)));
                        } else {
                            numbers.add(Integer.valueOf(line.substring(i, i + 1)));
                        }
                    }
                    zSlide.add(numbers);
                    numbers = new ArrayList<>();
                }
                else if(line.length() == 0){
                    all.add(zSlide);
                    zSlide = new ArrayList<>();
                }
            }
        }

        calcul();
    }

    private void calcul() {
        int counter = 0;
        int melysegMax = all.size();
        int fuggolegesMax = all.get(0).size();
        int vizszintesMax = all.get(0).get(0).size();
        for (int melyseg = 0; melyseg < melysegMax; melyseg++) {
            for (int fuggoleges = 0; fuggoleges < fuggolegesMax; fuggoleges++) {
                for (int vizszintes = 0; vizszintes < vizszintesMax; vizszintes++) {
                    counter += checkIfLocMin(vizszintes, fuggoleges, melyseg);
                }
            }
        }
        System.out.println("Counter: " + counter);

    }

    private int checkIfLocMin(int vizszintes, int fuggoleges, int melyseg) {
        int numberToCheck = all.get(melyseg).get(fuggoleges).get(vizszintes);
        boolean isLocMin = true;
        for (Coordinate point : coordinatesDirection) {
            int z1 = point.z + melyseg;
            int y1 = point.y + fuggoleges;
            int x1 = point.x + vizszintes;
            if (0 <= z1 && z1< all.size() && 0 <= y1 && y1< all.get(0).size() && 0 <= x1 && x1< all.get(0).get(0).size()){
                // Loc Min 3D
                if(!(numberToCheck < all.get(z1).get(y1).get(x1))){
                    return 0;
                }
            }
        }
        locMins.add(new Coordinate(vizszintes, fuggoleges, melyseg));
        System.out.println(new Coordinate(vizszintes, fuggoleges, melyseg));
        return isLocMin ? 1 : 0;
    }



    class Coordinate {

        private int x;
        private int y;
        private int z;

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x &&
                    y == that.y &&
                    z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
}
