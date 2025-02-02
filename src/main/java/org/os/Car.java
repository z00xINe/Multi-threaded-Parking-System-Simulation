package org.os;

import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    public int gate;
    public int id;
    public int arrived;
    public int time;
    Park park;

    Car(String gate, String id, String arrived,
        String time, SynchronizedCounter counter,
        Semaphore semaphore) {

        this.gate = Integer.parseInt(gate.substring(0, gate.length() - 1));
        this.id = Integer.parseInt(id.substring(0, id.length() - 1));
        this.arrived = Integer.parseInt(arrived.substring(0, arrived.length() - 1));
        this.time = Integer.parseInt(time);
        this.park = new Park(counter, semaphore);
    }

    @Override
    public void run() {
        try {
            park.parkCar(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}