package com.company;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static CacheSimulator cache = new CacheSimulator();

    private static String EXAMPLE_STRING =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. ";

    private static int iterations = 10_000;

    public static void main(String args[]) {
        leakMemoryByAddingObjects();

    }

    static void leakMemoryByAddingObjects() {
        while (true) {
            //print heap info
            printHeapAvailable();

            int option = menu();
            switch (option) {
                case 1:
                    saveRandomData();
                    break;
                case 2:
                    cache.clearData();
                    break;
                case 3:
                    return;
                default:

            }

        }
    }

    static void saveRandomData() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < iterations; j++) {
                cache.storeData(EXAMPLE_STRING + random.nextInt());
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //print heap info
            printHeapAvailable();
        }

    }

    static void printHeapAvailable() {
        long maxHeap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
        long heapUsed = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        System.out.println("Available Heap: " + (((maxHeap - heapUsed) * 100) / maxHeap) + "%");
    }

    static int menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Que hacer ahora?  1. agregar elementos , 2. limpiar lista,  3. terminar ");
        return scanner.nextInt();
    }
}

class CacheSimulator {

    private static List<String> list = new ArrayList<>();

    public static void storeData(String data) {
        list.add(data);
    }

    public static void clearData() {
        list.clear();
    }
}