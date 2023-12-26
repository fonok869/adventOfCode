package com.fmolnar.code.year2023.day24;

import com.fmolnar.code.FileReaderUtils;

import javax.swing.text.Position;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day24 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day24/input.txt");

        Set<HailStone> hailStoneSet = new HashSet<>();
        for(String line: lines){
            int indexKukac = line.indexOf('@');
            String firstPart = line.substring(0, indexKukac);
            String[] positions = firstPart.split(",");
            String secondPart = line.substring(indexKukac+1);
            String[] velocities = secondPart.split(",");
            Position pos = new Position(Long.valueOf(positions[0].trim()), Long.valueOf(positions[1].trim()), Long.valueOf(positions[2].trim()));
            Velocity velo = new Velocity(Integer.valueOf(velocities[0].trim()), Integer.valueOf(velocities[1].trim()), Integer.valueOf(velocities[2].trim()));
            hailStoneSet.add(new HailStone(pos, velo));
            //System.out.println(new HailStone(pos, velo).nextXYPoint());
        }

        Set<HailStone> hailStoneSetToDelete = new HashSet<>(hailStoneSet);

        List<PointXY> points = new ArrayList<>();
        for(HailStone hailStoneActual : hailStoneSet){
            hailStoneSetToDelete.remove(hailStoneActual);
            for(HailStone hailStoneToCheck : hailStoneSetToDelete){
                points.add(hailStoneActual.getIntersection(hailStoneToCheck));
            }
        }

        double min = 200000000000000.0;
        double max = 400000000000000.0;
        int counter = 0;

        for(PointXY pointXYToCheck : points){
            if(pointXYToCheck == null){
                continue;
            }
            if(min<= pointXYToCheck.x && pointXYToCheck.x<=max &&
                    min<= pointXYToCheck.y && pointXYToCheck.y<=max){
                counter++;
            }
        }

        System.out.println("Result: " + counter);
        StringBuilder equations = new StringBuilder();
        Iterator<HailStone> hailStoneIterator = hailStoneSet.iterator();
        for (int i = 0; i < 3; i++) {
            String t = "t" + i;
            HailStone hailStone = hailStoneIterator.next();
            equations.append(t).append(" >= 0, ").append(hailStone.position.x).append(" + ").append(hailStone.velocity.vx).append(t).append(" == x + vx ").append(t).append(", ");
            equations.append(hailStone.position.y).append(" + ").append(hailStone.velocity.vy).append(t).append(" == y + vy ").append(t).append(", ");
            equations.append(hailStone.position.z).append(" + ").append(hailStone.velocity.vz).append(t).append(" == z + vz ").append(t).append(", ");
        }
        String sendToMathematica = "Solve[{" + equations.substring(0, equations.length() - 2) +  "}, {x,y,z,vx,vy,vz,t0,t1,t2}]";

        System.out.println(sendToMathematica);
        long x = 129723668686742l;
        long y = 353939130278484l;
        long z = 227368817349775l;

        long x1 = 261502975177164l;
        long y1 = 428589795012222l;
        long z1 = 196765966839909l;

        System.out.println("Result 2: " + ((x+y+z)<(x1+y1+z1) ? (x1+y1+z1) : (x+y+z)));
        //711031616315001


    }

    record HailStone(Position position, Velocity velocity){
        double getM(){
            return (double)velocity.vy/(double)velocity.vx;
        }

        double getP0(){
            return -1*((double)position.x*(double)velocity.vy/(double)velocity.vx) + (double)position.y;
        }

        PointXY nextXYPoint(){
            return new PointXY(position.x+1, ((double) position.x+1)*getM() + getP0());
        }

        double getB(){
            return -1.0;
        }

        PointXY getIntersection(HailStone hailStone2){
            double xSzamlalo = (getB()*hailStone2.getP0())-(hailStone2.getB()*getP0());
            double xNevezo = (getM()*hailStone2.getB())-(hailStone2.getM()*getB());

            double ySzamlalo = ((getP0()*hailStone2.getM()) -(hailStone2.getP0()*getM()));
            double yNevezo = ((getM()*hailStone2.getB())-(hailStone2.getM()*getB()));

            PointXY intersection = new PointXY(xSzamlalo/xNevezo, ySzamlalo/yNevezo);
            // isInTheFuture
            boolean isInTheFuture = true;
            // This hailStone
            if(velocity.vx<0){
                isInTheFuture = isInTheFuture && intersection.x<position.x;
            } else {
                isInTheFuture = isInTheFuture && intersection.x>position.x;
            }

            // hailStone2
            if(hailStone2.velocity.vx<0){
                isInTheFuture = isInTheFuture && intersection.x<hailStone2.position.x;
            } else {
                isInTheFuture = isInTheFuture && intersection.x>hailStone2.position.x;
            }

            if(velocity.vy<0){
                isInTheFuture = isInTheFuture && intersection.y<position.y;
            } else {
                isInTheFuture = isInTheFuture && intersection.y>position.y;
            }

            if(hailStone2.velocity.vy<0){
                isInTheFuture = isInTheFuture && intersection.y<hailStone2.position.y;
            } else {
                isInTheFuture = isInTheFuture && intersection.y>hailStone2.position.y;
            }

            if(isInTheFuture){
                return intersection;
            }

            return null;
        }
    };

    record PointXY(double x, double y){}

    record Position(long x, long y, long z){};
    record Velocity(int vx, int vy, int vz){}
}
