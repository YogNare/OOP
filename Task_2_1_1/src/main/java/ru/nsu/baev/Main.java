package ru.nsu.baev;

import com.sun.source.tree.ArrayAccessTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        for (int i = 1; i < 10; i ++) {
            ArrayList<Integer> array = new ArrayList<>(List.of((int) pow(10, i)));
//            long startTime1 = System.currentTimeMillis();
//            ThreadPrimeNumbers test = new ThreadPrimeNumbers();
//            test.notAllPrime(array, 5);
//            long endTime1 = System.currentTimeMillis();
//            long duration = endTime1 - startTime1;

            long startTime2 = System.currentTimeMillis();
            PrimeNumbers test2 = new PrimeNumbers();
            test2.notAllPrime(array);
            long endTime2 = System.currentTimeMillis();
            long duration = endTime2 - startTime2;

            System.out.println(duration);
        }

    }
}