package com.fmolnar.code.year2020.day04;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04Challenge01 {

    String pattern = "([a-z]{3})\\:(.*)";
    Pattern patternToLine = Pattern.compile(pattern);

    public Day04Challenge01() {
    }

    public void calculateDay0401() throws IOException {
        getList();
    }

    public List<PassPort> getList() throws IOException {
        List<PassPort> passports = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream ("/2020/day04/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            List<String> passeportDetails = new ArrayList<>();
            while ((line = file.readLine()) != null) {

                if(line.length()!= 0){
                    passeportDetails.add(line);
                }
                else{
                    passports.add(convertLinesToPassePort(passeportDetails));
                    passeportDetails = new ArrayList<>();
                }
            }
        }

        System.out.println("All passport : " + passports.size());
        System.out.println("Valid passports : " + passports.stream().filter(PassPort::isValid).collect(Collectors.toList()).size());

        return passports;
    }


    private PassPort convertLinesToPassePort(List<String> lines){
        PassPort passPort = new PassPort();
        for(String line : lines){
            String[] elements = line.split("\\s");
            for(int j=0; j<elements.length; j++){
                Matcher matcher =patternToLine.matcher(elements[j]);
                if(matcher.find()){
                    fillOut(passPort, matcher.group(1),  matcher.group(2) );
                }
            }
        }
        return passPort;
    }

    private void fillOut(PassPort passPort, String key, String value){
        switch (key.toLowerCase()){
            case "ecl":
                passPort.setEcl(value);
                break;
            case "byr" :
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
            default: throw new RuntimeException("Unknown value");
        }
    }

    private class PassPort{
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
            this.byr = byr;
        }

        public String getIyr() {
            return iyr;
        }

        public void setIyr(String iyr) {
            this.iyr = iyr;
        }

        public String getEyr() {
            return eyr;
        }

        public void setEyr(String eyr) {
            this.eyr = eyr;
        }

        public String getHgt() {
            return hgt;
        }

        public void setHgt(String hgt) {
            this.hgt = hgt;
        }

        public String getHcl() {
            return hcl;
        }

        public void setHcl(String hcl) {
            this.hcl = hcl;
        }

        public String getEcl() {
            return ecl;
        }

        public void setEcl(String ecl) {
            this.ecl = ecl;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public boolean isValid(){
            if(StringUtils.isNotEmpty(byr) && StringUtils.isNotEmpty(iyr) && StringUtils.isNotEmpty(eyr) &&
            StringUtils.isNotEmpty(hgt) && StringUtils.isNotEmpty(hcl) && StringUtils.isNotEmpty(ecl) &&
            StringUtils.isNotEmpty(pid)){
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
                    ", hgt='" + hgt + '\'' +
                    ", hcl='" + hcl + '\'' +
                    ", ecl='" + ecl + '\'' +
                    ", pid='" + pid + '\'' +
                    ", cid='" + cid + '\'' +
                    '}';
        }
    }
}
