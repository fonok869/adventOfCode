package com.fmolnar.code.year2022.day08;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day08 {

    int yLength ;
    int xLength ;
    int [][] trees;

    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2022/day08/input.txt");

        yLength = lines.size();
        xLength = lines.get(0).length();

        trees = new int[yLength][xLength];
        int y=0;
        for(String line : lines){
            for(int x=0; x<line.length(); x++){
                trees[y][x] = Integer.valueOf(line.substring(x, x+1));
            }
            y++;
        }

        int trresvisible = 0;
        List<Integer> maxViews = new ArrayList<>();

        for(int yy=0; yy<yLength; yy++){
            for(int xx=0;xx<xLength; xx++ ){
                trresvisible += calculateVisibility(xx,yy, trees);
                maxViews.add(calculateVisibilityView(xx,yy, trees));
            }
        }

        System.out.println("First: " + trresvisible);
        System.out.println("Second: " + maxViews.stream().mapToInt(s->s).max().getAsInt());
    }

    private int calculateVisibilityView(int xx, int yy, int[][] trees) {

        int treeNumber = trees[yy][xx];
        int x1 =0;
        int x2 =0;
        int y1 =0;
        int y2 =0;

        // xx // felulrol
        for(int i=xx-1;0<=i; i--){
            x1++;
            if(treeNumber <= trees[yy][i]){
                break;
            }
        }


        // xx // lent
        for(int i=xx+1; i<xLength; i++){
            x2++;
            if(treeNumber <= trees[yy][i]){
                break;
            }
        }

        //yy // balrol
        for (int i=yy-1; 0<=i; i--){
            y1++;
            if(treeNumber <= trees[i][xx]){
                break;
            }
        }


        // yy jobbrol
        boolean breakedJobb= false;
        for(int i=yy+1; i<yLength; i++){
            y2++;
            if(treeNumber <= trees[i][xx]){
                break;
            }
        }

        return x1*x2*y1*y2;
    }

    private int calculateVisibility(int xx, int yy, int[][] trees) {

        int treeNumber = trees[yy][xx];
        // szelso
        if(xx == xLength || xx ==0 || yy==0 || yy==yLength){
            return 1;
        }

        // xx // felulrol
        boolean breakedXXFel = false;
        for(int i=0;i<xx; i++){
            if(treeNumber <= trees[yy][i]){
                breakedXXFel = true;
                break;
            }
        }

        if (!breakedXXFel){
            return 1;
        }

        // xx // lent
        boolean breakedXXLent  = false;
        for(int i=xx+1; i<xLength; i++){
            if(treeNumber <= trees[yy][i]){
                breakedXXLent = true;
                break;
            }
        }
        if(!breakedXXLent){
            return 1;
        }

        //yy // balrol
        boolean breakedYYBalrol = false;
        for (int i=0; i<yy; i++){
            if(treeNumber <= trees[i][xx]){
                breakedYYBalrol = true;
                break;
            }
        }

        if(!breakedYYBalrol){
            return 1;
        }

        // yy jobbrol
        boolean breakedJobb= false;
        for(int i=yy+1; i<yLength; i++){
            if(treeNumber <= trees[i][xx]){
                breakedJobb = true;
                break;
            }
        }

        if(!breakedJobb){
            return 1;
        }


        return 0;
    }
}
