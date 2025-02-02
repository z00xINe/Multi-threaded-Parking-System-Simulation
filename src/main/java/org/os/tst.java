//package org.os;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.Semaphore;
//import java.util.regex.*;
//
//public class tst {
//    public static void main(String[] args) throws IOException, InterruptedException {
//        ParkingLot parkingLot = new ParkingLot(4);
//        List<Car> gate1Cars = new ArrayList<>();
//        List<Car> gate2Cars = new ArrayList<>();
//        List<Car> gate3Cars = new ArrayList<>();
//
//        Pattern pattern = Pattern.compile("Gate\\s(\\d+),\\sCar\\s(\\d+),\\sArrive\\s(\\d+),\\sParks\\s(\\d+)");
//
//        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Matcher matcher = pattern.matcher(line);
//
//                if (!matcher.matches()) {
//                    System.out.println("Skipping line due to incorrect format: " + line);
//                    continue;
//                }
//
//                try {
//                    int gate = Integer.parseInt(matcher.group(1));
//                    int carId = Integer.parseInt(matcher.group(2));
//                    int arrivalTime = Integer.parseInt(matcher.group(3));
//                    int parkingDuration = Integer.parseInt(matcher.group(4));
//
//                    Car car = new Car(carId, gate, arrivalTime, parkingDuration, parkingLot);
//
//                    switch (gate) {
//                        case 1 -> gate1Cars.add(car);
//                        case 2 -> gate2Cars.add(car);
//                        case 3 -> gate3Cars.add(car);
//                        default -> System.out.println("Unknown gate number: " + gate);
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Error parsing line: " + line);
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        // Start gate threads
//        Thread gate1Thread = new Thread(new Gate(1, gate1Cars));
//        Thread gate2Thread = new Thread(new Gate(2, gate2Cars));
//        Thread gate3Thread = new Thread(new Gate(3, gate3Cars));
//
//        gate1Thread.start();
//        gate2Thread.start();
//        gate3Thread.start();
//
//        // Wait for all gate threads to finish
//        gate1Thread.join();
//        gate2Thread.join();
//        gate3Thread.join();
//
//        // Print final report after all cars have been processed
//        System.out.println("Total Cars Served: " + parkingLot.getTotalCarsServed());
//        System.out.println("Current Cars in Parking: " + parkingLot.getCurrentCarsInParking());
//        System.out.println("Details:");
//        System.out.println("- Gate 1 served " + gate1Cars.size() + " cars.");
//        System.out.println("- Gate 2 served " + gate2Cars.size() + " cars.");
//        System.out.println("- Gate 3 served " + gate3Cars.size() + " cars.");
//    }
//}
//
//class ParkingLot {
//    private final Semaphore parkingSpots;
//    private int totalCarsServed = 0;
//    private int currentCarsInParking = 0;
//
//    public ParkingLot(int spots) {
//        parkingSpots = new Semaphore(spots, true);
//    }
//
//    public void enter() throws InterruptedException {
//        parkingSpots.acquire(); // Blocks until a spot is available
//        synchronized (this) {
//            currentCarsInParking++;
//        }
//    }
//
//    public void leave() {
//        parkingSpots.release();
//        synchronized (this) {
//            currentCarsInParking--;
//            totalCarsServed++;
//        }
//    }
//
//    public synchronized int getTotalCarsServed() {
//        return totalCarsServed;
//    }
//
//    public synchronized int getCurrentCarsInParking() {
//        return currentCarsInParking;
//    }
//}
//
//class Car implements Runnable {
//    private final int carId;
//    private final int gate;
//    private final int arrivalTime;
//    private final int parkingDuration;
//    private final ParkingLot parkingLot;
//
//    public Car(int carId, int gate, int arrivalTime, int parkingDuration, ParkingLot parkingLot) {
//        this.carId = carId;
//        this.gate = gate;
//        this.arrivalTime = arrivalTime;
//        this.parkingDuration = parkingDuration;
//        this.parkingLot = parkingLot;
//    }
//
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(arrivalTime * 1000);
//            System.out.println("Car " + carId + " from Gate " + gate + " arrived at time " + arrivalTime);
//
//            // Attempt to enter parking lot, or wait if it's full
//            synchronized (parkingLot) {
//                if (parkingLot.getCurrentCarsInParking() < 4) {
//                    parkingLot.enter(); // Blocks until a parking spot is available
//                    System.out.println("Car " + carId + " from Gate " + gate + " parked. (Parking Status: "
//                            + parkingLot.getCurrentCarsInParking() + " spots occupied)");
//                } else {
//                    System.out.println("Car " + carId + " from Gate " + gate + " waiting for a spot.");
//                    parkingLot.enter(); // Wait until a spot is available
//                    System.out.println("Car " + carId + " from Gate " + gate + " parked after waiting for "
//                            + arrivalTime + " units of time. (Parking Status: "
//                            + parkingLot.getCurrentCarsInParking() + " spots occupied)");
//                }
//            }
//
//            Thread.sleep(parkingDuration * 1000);
//            parkingLot.leave();
//            System.out.println("Car " + carId + " from Gate " + gate + " left after " + parkingDuration
//                    + " units of time. (Parking Status: " + parkingLot.getCurrentCarsInParking() + " spots occupied)");
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
//
//class Gate implements Runnable {
//    private final int gateId;
//    private final List<Car> cars;
//
//    public Gate(int gateId, List<Car> cars) {
//        this.gateId = gateId;
//        this.cars = cars;
//    }
//
//    @Override
//    public void run() {
//        List<Thread> carThreads = new ArrayList<>();
//        for (Car car : cars) {
//            Thread carThread = new Thread(car);
//            carThreads.add(carThread);
//            carThread.start();
//        }
//
//        // Wait for all car threads in this gate to finish
//        for (Thread carThread : carThreads) {
//            try {
//                carThread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//}