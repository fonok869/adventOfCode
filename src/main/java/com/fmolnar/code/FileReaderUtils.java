package com.fmolnar.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class FileReaderUtils {

    public static List<String> readFile(String fullpath) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream reader = FileReaderUtils.class.getResourceAsStream(fullpath);
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader))) {
            String line;
            while ((line = file.readLine()) != null) {
                lines.add(line);
            }

        }
        return lines;
    }
}
