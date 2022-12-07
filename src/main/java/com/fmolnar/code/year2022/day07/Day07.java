package com.fmolnar.code.year2022.day07;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 {

    public static final String $CD = "$ cd ";
    public static final String $CD__ = "$ cd ..";
    public static final String DIR = "dir";

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day07/input.txt");
        Map<String, Long> sizeMap = new HashMap<>();
        Map<String, List<String>> mapper = new HashMap<String, List<String>>();

        String actualDirectory = "";
        for (String line : lines) {
            if (line.startsWith($CD) && !line.startsWith($CD__)) {
                actualDirectory = actualDirectory + "/" + line.substring($CD.length());
            } else if (line.startsWith($CD__)) {
                actualDirectory = actualDirectory.substring(0, actualDirectory.lastIndexOf("/"));
            } else if (!"$ ls".equals(line)) {
                List<String> contents = new ArrayList<>();
                contents.add(line);
                if (mapper.get(actualDirectory) == null) {
                    mapper.put(actualDirectory, contents);
                } else {
                    mapper.get(actualDirectory).add(line);
                }
            }
        }

        while (true) {
            if (mapper.size() == sizeMap.size()) {
                break;
            }
            for (Map.Entry<String, List<String>> entry : mapper.entrySet()) {
                if (sizeMap.containsKey(entry.getKey())) {
                    continue;
                }
                long sum = 0l;
                boolean breaked = false;
                for (String filename : entry.getValue()) {
                    if (filename.startsWith(DIR)) {
                        breaked = true;
                        break;
                    } else {
                        sum += Long.valueOf(filename.substring(0, filename.indexOf(" ")));
                    }
                }
                if (!breaked) {
                    sizeMap.put(entry.getKey(), sum);
                }
            }

            for (Map.Entry<String, List<String>> entry : mapper.entrySet()) {
                String key = entry.getKey();
                List<String> strings = new ArrayList<>(entry.getValue());
                for (String directory : strings) {
                    if (directory.startsWith(DIR)) {
                        String directoryName = directory.substring("dir ".length());
                        String path = key + "/" + directoryName;
                        if (sizeMap.containsKey(path)) {
                            mapper.get(key).remove(directory);
                            mapper.get(key).add((sizeMap.get(path).toString() + " dirxykdfkterijjtoej"));
                        }
                    }
                }
            }
        }
         // Part 1
        long maxSize = 100000L;
        System.out.println("First: " + sizeMap.entrySet().stream().mapToLong(Map.Entry::getValue).filter(s -> s <= maxSize).sum());

        // Part 2
        long max = 70000000l;
        long need = 30000000l;

        long toDelete = need - (max - sizeMap.get("//"));
        System.out.println("Second: " + sizeMap.entrySet().stream().mapToLong(Map.Entry::getValue).filter(s->toDelete<=s).min().getAsLong());

    }
}
