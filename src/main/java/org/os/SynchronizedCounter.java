package org.os;

public class SynchronizedCounter {
    private int carsServed = 0;
    private int parkingStatus = 0;
    private int nowTime = 0;

    public synchronized void updateTime(int time) {
        nowTime = time;
    }

    public synchronized int getTime() {
        return nowTime;
    }

    public synchronized void incrementServed() {
        int register = carsServed;
        register = register+1;
        carsServed = register;
    }

    public synchronized int valueServed() {
        return carsServed;
    }

    public synchronized void incrementStatus() {
        int register = parkingStatus;
        register = register+1;
        parkingStatus = register;
    }

    public synchronized void decrementStatus() {
        int register = parkingStatus;
        register = register-1;
        parkingStatus = register;
    }

    public synchronized int valueStatus() {
        return parkingStatus;
    }
}
