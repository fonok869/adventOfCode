package com.fmolnar.code.year2024.day09;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day09 {
    public void calculateday092024() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2024/day09/input.txt");

        long initTime = System.currentTimeMillis();

        Map<Integer, Integer> representations = new HashMap<>();

        for (String line : lines) {
            int actualValue = 0;
            for (int i = 0; i < line.length(); i = i + 2) {
                int fileBlocks = Integer.valueOf(String.valueOf(line.charAt(i)));

                for (int fileBlockIndex = actualValue; fileBlockIndex < fileBlocks + actualValue; fileBlockIndex++) {
                    representations.put(fileBlockIndex, i / 2);
                }
                actualValue += fileBlocks;
                if (i + 1 < line.length()) {
                    int spaceBlocks = Integer.valueOf(String.valueOf(line.charAt(i + 1)));
                    for (int spaceBlocksIndex = actualValue; spaceBlocksIndex < spaceBlocks + actualValue; spaceBlocksIndex++) {
                        representations.put(spaceBlocksIndex, -1);
                    }
                    actualValue += spaceBlocks;
                }
            }
        }

        Map<Integer, Integer> representations2 = new HashMap<>(representations);

        compactingFirst(representations2);
        System.out.println("First checksum: " + getCheckSum(representations2));
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - initTime));
        compacting(representations);
        System.out.println("Second checksum: " + getCheckSum(representations));
        System.out.println("Time: " + (System.currentTimeMillis() - endTime));
    }

    private boolean compactingFirst(Map<Integer, Integer> representations) {

        int lastJobbIndex = representations.size() - 1;
        for (int balIndex = 0; balIndex < representations.size(); balIndex++) {
            if (representations.get(balIndex) == -1) {
                for (int indexJobb = lastJobbIndex; indexJobb >= 0; indexJobb--) {
                    if (balIndex >= indexJobb) {
                        break;
                    }
                    if (representations.get(indexJobb) != -1) {
                        Integer balToReplace = representations.get(indexJobb);
                        representations.put(balIndex, balToReplace);
                        representations.put(indexJobb, -1);
                        lastJobbIndex = indexJobb;
                        break;
                    }
                }
            }
        }
        return false;
    }

    private long getCheckSum(Map<Integer, Integer> representations) {

        long checkSum = 0;
        for (int balIndex = 0; balIndex < representations.size(); balIndex++) {
            if (representations.get(balIndex) == -1) {
                continue;
            }
            checkSum += Long.valueOf(representations.get(balIndex)) * Long.valueOf(balIndex);
        }
        return checkSum;
    }

    private void compacting(Map<Integer, Integer> representations) {

        Set<Integer> alreadyCompacted = new HashSet<>();
        for (int indexJobb = representations.size() - 1; indexJobb >= 0; indexJobb--) {

            boolean replaced = false;

            if (representations.get(indexJobb) != -1) {
                Integer actualValue = representations.get(indexJobb);

                if (alreadyCompacted.contains(actualValue)) {
                    continue;
                }

                int jobbHossz = 0;
                for (int jobbShortIndex = indexJobb; 0 <= jobbShortIndex; jobbShortIndex--) {
                    if (representations.get(jobbShortIndex).equals(actualValue)) {
                        jobbHossz++;
                    } else {
                        // masik
                        break;
                    }
                }


                for (int balIndex = 0; balIndex < representations.size(); balIndex++) {
                    if (indexJobb <= balIndex) {
                        break;
                    }

                    if (representations.get(balIndex) == -1) {
                        int balHossz = 0;
                        for (int ballShortIndex = balIndex; ballShortIndex < representations.size(); ballShortIndex++) {
                            if (representations.get(ballShortIndex) == -1) {
                                balHossz++;
                            } else {
                                // keressuk tovabb
                                break;
                            }
                        }

                        // checkBefer
                        if (balHossz >= jobbHossz) {
                            // igen befer
                            for (int i = 0; i < jobbHossz; i++) {
                                Integer balToReplace = representations.get(indexJobb - i);
                                representations.put(balIndex + i, balToReplace);
                                representations.put(indexJobb - i, -1);
                            }
                            replaced = true;
                            break;
                        }

                    }
                }
                if (!replaced) {
                    alreadyCompacted.add(actualValue);
                    indexJobb = indexJobb - jobbHossz + 1;
                }
            }
        }
    }
}


