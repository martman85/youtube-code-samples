package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class Cleaner {

    public static void main(String[] args) {
        var service = Executors.newFixedThreadPool(3);
        try {
            CyclicBarrier c1 = new CyclicBarrier(3, () -> System.out.println("All cleaning is done, lets turn off the lights, activate alarm and go home!"));

            Cleaner cleaner = new Cleaner();
            for (int i = 0; i < 3; i++) {
                service.submit(() -> cleaner.clean(c1));
            }
        } finally {
            service.shutdown();
        }
    }

    private void clean(CyclicBarrier c1) {
        try {
            vacuumFloor();
            Thread.sleep(Math.round(Math.random() * 1000));
            coffeeBreak();
            Thread.sleep(Math.round(Math.random() * 1000));
            mopFloor();
            Thread.sleep(Math.round(Math.random() * 1000));
            c1.await();
            turnOffLights();
            activateAlarm();
        } catch (InterruptedException | BrokenBarrierException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void vacuumFloor() {
        System.out.println("Vacuming the floor...");
    }
    private void mopFloor() {
        System.out.println("Mopping the floor...");
    }

    private void coffeeBreak() {
        System.out.println("Time for a break!");
    }
    private void turnOffLights() {
        System.out.println("Turning off the lights...");
    }
    private void activateAlarm() {
        System.out.println("Activating the alarm...");
    }
}