package ru.nsu.baev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ThreadPrimeNumbers {

    public boolean notAllPrime(ArrayList<Integer> array, int numThreads) {
        if (!array.isEmpty()) {
            Integer maxNum = Collections.max(array);
            boolean[] isPrime = sieve(maxNum + 1, numThreads);
            if (isPrime.length < 1) return true;
            for (Integer num : array) {
                if (!isPrime[num]) return true;
            }
        }

        return false;
    }

    public static boolean[] sieve(int limit, int numThreads) {
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        int sqrtLimit = (int) Math.sqrt(limit);

        for (int i = 2; i <= sqrtLimit; i++) {
            if (isPrime[i]) {
                for (int multiple = i * i; multiple <= sqrtLimit; multiple += i) {
                    isPrime[multiple] = false;
                }
            }
        }

        int blockSize = (limit - sqrtLimit) / numThreads + 1;
        Thread[] threads = new Thread[numThreads];

        for (int thread = 0; thread < numThreads; thread++) {
            final int start = sqrtLimit + 1 + thread * blockSize;
            final int end = Math.min(start + blockSize - 1, limit);

            threads[thread] = new Thread(() -> {
                for (int i = 2; i <= sqrtLimit; i++) {
                    if (isPrime[i]) {
                        int firstMultiple = ((start + i - 1) / i) * i;
                        for (int multiple = Math.max(firstMultiple, i * i); multiple <= end; multiple += i) {
                            isPrime[multiple] = false;
                        }
                    }
                }
            });

            threads[thread].start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return isPrime;
    }
}