package com.fmolnar.code.multihthreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    Lock lock = new ReentrantLock();
    Condition isFull = lock.newCondition();
    Condition isEmpty = lock.newCondition();
    int[] buffer = new int[10];
    int count = 0;

    class Producer{
        public void produce(){
            try{
                lock.lock();
                while (isFull(buffer)){
                    isFull.await();
                }
                buffer[count++]=1;
                isEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private boolean isFull(int[] buffer) {
            if(buffer[9] == 1){
                return true;
            }
            return false;
        }
    }

    class Consumer{
        public void consumer(){
            try {
                lock.lock();
                while (isEmptyS(buffer)){
                    isEmpty.await();
                }
                buffer[--count] = 0;
                isFull.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        private boolean isEmptyS(int[] buffer) {
            if(buffer[0] == 0){
                return true;
            }
            return false;
        }

    }
}
