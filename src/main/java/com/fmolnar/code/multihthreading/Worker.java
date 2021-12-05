package com.fmolnar.code.multihthreading;

import java.util.Timer;
import java.util.TimerTask;

public class Worker extends Thread {

    private volatile boolean quittingTime = false;
    private Object lock = new Object();

    public void run(){
        while(!quittingTime){
            working();
            System.out.println("Still working...");
        }
        System.out.println("Coffee is good !");
    }

    private void working() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void quit() throws InterruptedException {
        synchronized (lock) {
            quittingTime = true;
            System.out.println("Calling join!");
            join();
            System.out.println("Back From join;");
        }
    }

    synchronized void keepWorking(){
        synchronized (lock){
            quittingTime = false;
            System.out.println("Keep Working");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final Worker worker= new Worker();
        worker.start();

        Timer t = new Timer(true); // Daemon Thread
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                worker.keepWorking();
            }
        },500);
        Thread.sleep(400);
        worker.quit();
    }
}
