package com.fmolnar.code.year2021.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day25Challenge01 {

    private List<East> easts = new ArrayList<>();
    private List<South> souths = new ArrayList<>();
    private List<Empty> empties = new ArrayList<>();
    private int xMax = 0;
    private int yMax = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day25/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counterY = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    for (int x = 0; x < line.length(); x++) {
                        char actual = line.charAt(x);
                        if (actual == '>') {
                            easts.add(new East(x, counterY));
                        } else if (actual == 'v') {
                            souths.add(new South(x, counterY));
                        } else {
                            empties.add(new Empty(x, counterY));
                        }
                    }
                    counterY++;
                    xMax = line.length();
                }
            }
            yMax = counterY;
        }
        int counter = 0;
        while (true) {

            boolean changedE = stepUpEastChanged();
            boolean changedS = stepUpSouthChanged();
            counter++;
            if(!(changedE || changedS)) {
                break;
            }
        }

        System.out.println("Day25Challenge01: " + counter);
    }

    private boolean stepUpEastChanged() {
        List<East> eastsAll = new ArrayList<>(easts);
        List<Empty> emptiesAll = new ArrayList<>(empties);
        List<Empty> emptiesAllOld = new ArrayList<>(empties);
        List<East> newEast = new ArrayList<>();
        boolean changed = false;
        for (East eastActual : eastsAll) {
            int xNew = (eastActual.x()+1) % xMax;
            int yNew = eastActual.y();
            if (emptiesAllOld.contains(new Empty(xNew, yNew))){
                emptiesAll.remove(new Empty(xNew, yNew));
                newEast.add(new East(xNew, yNew));
                emptiesAll.add(new Empty(eastActual.x(), eastActual.y()));
                changed = true;
            } else {
                newEast.add(eastActual);
            }
        }
        easts = new ArrayList<>(newEast);
        empties = new ArrayList<>(emptiesAll);
        return changed;

    }

    private boolean stepUpSouthChanged() {
        List<South> southsAll = new ArrayList<>(souths);
        List<Empty> emptiesAll = new ArrayList<>(empties);
        List<Empty> emptiesAllOld = new ArrayList<>(empties);
        List<South> newSouth = new ArrayList<>();
        boolean changed = false;
        for (South southActual : southsAll) {
            int xNew = southActual.x();
            int yNew = (southActual.y() + 1) % yMax;
            if (emptiesAllOld.contains(new Empty(xNew, yNew))){
                emptiesAll.remove(new Empty(xNew, yNew));
                newSouth.add(new South(xNew, yNew));
                emptiesAll.add(new Empty(southActual.x(), southActual.y()));
                changed = true;
            } else {
                newSouth.add(southActual);
            }
        }
        souths = new ArrayList<>(newSouth);
        empties = new ArrayList<>(emptiesAll);
        return changed;

    }

    public static record East(int x, int y) {

    }

    public static record South(int x, int y) {

    }

    public static record Empty(int x, int y) {

    }


}


