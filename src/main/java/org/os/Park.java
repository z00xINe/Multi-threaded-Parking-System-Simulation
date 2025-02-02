package org.os;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Park {

    private long waiting = 0;
    private final SynchronizedCounter sc;
    private final Semaphore semaphore;


    public Park(SynchronizedCounter sc, Semaphore semaphore) {
        this.sc = sc;
        this.semaphore = semaphore;
    }

    public void parkCar(Car car) throws InterruptedException {
        System.out.println("Car " + car.id+" from Gate "+car.gate+
                " arrived at time " +car.arrived);
        try {
            if (sc.valueStatus() == 4) {
                System.out.println("Car "+car.id+" from Gate "+
                        car.gate +" waiting for a spot.");
                waiting++;
            }
            semaphore.acquire();
            synchronized (this) {
                sc.incrementStatus();
                sc.incrementServed();
                waiting += sc.getTime() - car.arrived;

                if (waiting > 0) {
                    System.out.println("Car "+car.id+" from Gate "+
                            car.gate+" parked after waiting for "+
                            waiting + " units of time. (Parking Status: "+
                            sc.valueStatus() +" spots occupied)");
                }

                else {
                    System.out.println("Car "+car.id+" from Gate "+
                            car.gate+" parked. (Parking Status: "+
                            sc.valueStatus()+" spots occupied)");
                }

                Thread.sleep(car.time * 1000L);
                System.out.println("Car "+car.id+" from Gate "+
                        car.gate+" left after "+ car.time+
                        " units of time. (Parking Status: "+
                        (sc.valueStatus()-1)+" spots occupied)");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sc.decrementStatus();
            semaphore.release();
        }
    }

    public synchronized void reportStatus() {
        System.out.println("\nTotal cars served: " + sc.valueServed());
        System.out.println("Current Cars in Parking: "
                +sc.valueStatus());
    }
}