package com.fmolnar.code.year2020.day04;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04Challenge02 {

    String pattern = "([a-z]{3})\\:(.*)";
    Pattern patternToLine = Pattern.compile(pattern);

    public Day04Challenge02() {
    }

    public void calculateDay0402() throws IOException {
        getList();
    }

    public List<PassPort> getList() throws IOException {
        List<PassPort> passports = new ArrayList<>();

        InputStream reader = getClass().getResourceAsStream ("/2020/day04/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            List<String> passportDetails = new ArrayList<>();
            while ((line = file.readLine()) != null) {

                if (line.length() != 0) {
                    passportDetails.add(line);
                } else {
                    passports.add(convertLinesToPassPort(passportDetails));
                    passportDetails = new ArrayList<>();
                }
            }
        }

        System.out.println("All passport : " + passports.size());
        List<PassPort> passPortsValid = passports.stream().filter(PassPort::isValid).collect(Collectors.toList());
        System.out.println("Valid passports : " + passPortsValid.size());

        return passports;
    }


    private PassPort convertLinesToPassPort(List<String> lines) {
        PassPort passPort = new PassPort();
        for (String line : lines) {
            String[] elements = line.split("\\s");
            for (int j = 0; j < elements.length; j++) {
                Matcher matcher = patternToLine.matcher(elements[j]);
                if (matcher.find()) {
                    fillOut(passPort, matcher.group(1), matcher.group(2));
                }
            }
        }
        return passPort;
    }

    private void fillOut(PassPort passPort, String key, String value) {
        switch (key.toLowerCase()) {
            case "ecl":
                passPort.setEcl(value);
                break;
            case "byr":
                passPort.setByr(value);
                break;
            case "iyr":
                passPort.setIyr(value);
                break;
            case "eyr":
                passPort.setEyr(value);
                break;
            case "hgt":
                passPort.setHgt(value);
                break;
            case "hcl":
                passPort.setHcl(value);
                break;
            case "pid":
                passPort.setPid(value);
                break;
            case "cid":
                passPort.setCid(value);
                break;
            default:
                throw new RuntimeException("Unknown value");
        }
    }

    private class PassPort {
        private String byr;
        private String iyr;
        private String eyr;
        private String hgt;
        private String hcl;
        private String ecl;
        private String pid;
        private String cid;

        public PassPort() {
        }

        public String getByr() {
            return byr;
        }

        public void setByr(String byr) {
            if (byr.length() == 4 && 1920 <= Integer.valueOf(byr) && Integer.valueOf(byr) <= 2002) {
                this.byr = byr;
                return;
            }
            this.byr = null;
        }

        public String getIyr() {
            return iyr;
        }

        public void setIyr(String iyr) {
            if (iyr.length() == 4 && 2010 <= Integer.valueOf(iyr) && Integer.valueOf(iyr) <= 2020) {
                this.iyr = iyr;
                return;
            }
            this.iyr = null;
        }

        public String getEyr() {
            return eyr;
        }

        public void setEyr(String eyr) {

            if (eyr.length() == 4 && 2020 <= Integer.valueOf(eyr) && Integer.valueOf(eyr) <= 2030) {
                this.eyr = eyr;
                return;
            }
            this.eyr = null;
        }

        public String getHgt() {
            return hgt;
        }

        public void setHgt(String hgt) {
           if(hgt.length() == 5){
               String height = hgt.substring(0,3);
               String format = hgt.substring(3);
              if("cm".equals(format) && 150 <= Integer.valueOf(height) && Integer.valueOf(height) <= 193){
                  this.hgt = hgt;
                  return;
              }
               this.hgt = null;
              return;
           }
            if(hgt.length() == 4){
                String height = hgt.substring(0,2);
                String format = hgt.substring(2);
                if("in".equals(format) && 59 <= Integer.valueOf(height) && Integer.valueOf(height) <= 76){
                    this.hgt = hgt;
                    return;
                }
                this.hgt = null;
                return;
            }
            this.hgt = null;
        }

        public String getHcl() {
            return hcl;
        }

        public void setHcl(String hcl) {
            String pattern = "(\\#[0-9a-f]{6})";
            Pattern patternToLine = Pattern.compile(pattern);
            Matcher matcher = patternToLine.matcher(hcl);
            if (matcher.find()) {
                this.hcl = hcl;
                return;
            }
            this.hcl = null;
        }

        public String getEcl() {
            return ecl;
        }

        public void setEcl(String ecl) {
            if(ecl.length() !=3){
                this.ecl = null;
                return;
            }
            List<String> colors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
            if (colors.contains(ecl)) {
                this.ecl = ecl;
                return;
            }
            this.ecl = null;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            String pattern = "(^[0-9]{9}$)";
            Pattern patternToLine = Pattern.compile(pattern);
            Matcher matcher = patternToLine.matcher(pid);
            if (matcher.find()) {
                this.pid = pid;
                return;
            }
            this.pid = null;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public boolean isValid() {
            if (StringUtils.isNotEmpty(byr) && StringUtils.isNotEmpty(iyr) && StringUtils.isNotEmpty(eyr) &&
                    StringUtils.isNotEmpty(hgt) && StringUtils.isNotEmpty(hcl) && StringUtils.isNotEmpty(ecl) &&
                    StringUtils.isNotEmpty(pid)) {
                return true;

            }
            return false;
        }

        @Override
        public String toString() {
            return "PassPort{" +
                    "byr='" + byr + '\'' +
                    ", iyr='" + iyr + '\'' +
                    ", eyr='" + eyr + '\'' +
                    ", hcl='" + hcl + '\'' +
                    ", ecl='" + ecl + '\'' +
                    ", pid='" + pid + '\'' +
                    ", hgt='" + hgt + '\'' +
                    '}';
        }
    }
}
