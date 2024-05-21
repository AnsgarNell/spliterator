package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;


public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> digits = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        sequentially(digits);
        inParallel(digits);
    }


    private static void inParallel(ArrayList<Integer> digits) {
        System.out.println("In Parallel");
        System.out.println("-------------");

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Spliterator<Integer> spliterator1 = digits.spliterator();
        Spliterator<Integer> spliterator2 = spliterator1.trySplit();

        forkJoinPool.submit(() -> spliterator1.forEachRemaining(Main::printThreadAndDigit)).join();
        forkJoinPool.submit(() -> spliterator2.forEachRemaining(Main::printThreadAndDigit)).join();

        System.out.println();
    }


    private static void sequentially(ArrayList<Integer> digits) {
        System.out.println("Sequentially");
        System.out.println("-------------");

        Spliterator<Integer> spliterator = digits.spliterator();
        while (spliterator.tryAdvance(Main::printThreadAndDigit));

        System.out.println();
    }


    private static void printThreadAndDigit(Integer digit) {
        System.out.println(Thread.currentThread().getName() + " " +  digit);
    }
}