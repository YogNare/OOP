package ru.nsu.baev;

import java.util.List;

import static java.lang.Math.pow;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException{


        for (int i = 1; i < 10; i ++) {
            List<Integer> array = List.of((int) pow(10, i));

            long startTime = System.nanoTime();
//            PrimeNumberChecker checker = new PrimeNumberChecker();
            ThreadPrimeNumbers checker = new ThreadPrimeNumbers();
            checker.allPrimes(array, 5);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            

            System.out.println(duration);
        }

    }
}
