package org.os;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    static void R() throws IOException {
        if (!System.getProperty("ONLINE_JUDGE", "false").equals("true")) {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream("output.txt"));
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        R();

        Scanner sc = new Scanner(System.in);
        SynchronizedCounter counter = new SynchronizedCounter();
        Semaphore semaphore = new Semaphore(4);

        int mx = 0, g1 = 0, g2 = 0, g3 = 0;

        Map<Integer, List<Thread>> gateThreads = new Hashtable<>();

        for (int i = 0; i < 15; i++) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            Car car = new Car(tokens[1], tokens[3],
                    tokens[5], tokens[7], counter, semaphore);
            Thread t = new Thread(car);

            if (car.gate == 1) g1++;

            else if (car.gate == 2) g2++;

            else if (car.gate == 3) g3++;

            else continue;

            if (car.arrived > mx)
                mx = car.arrived;

            gateThreads.putIfAbsent(car.arrived, new ArrayList<>());
            gateThreads.get(car.arrived).add(t);
        }

        for (int i = 0; i <= mx; i++) {
            counter.updateTime(i);
            for (int j = 0; j < gateThreads.get(i).size(); j++)
                gateThreads.get(i).get(j).start();

            Thread.sleep(950);
        }

        for (int i = 0; i <= mx; i++) {
            for (int j = 0; j < gateThreads.get(i).size(); j++)
                gateThreads.get(i).get(j).join();
        }

        Park park = new Park(counter, semaphore);
        park.reportStatus();
        System.out.println("\nDetails:\n- Gate 1 served " +
                g1 + " cars.\n- Gate 2 served " +
                g2 + " cars.\n" + "- Gate 3 served " +
                g3 + " cars.\n");
    }
}