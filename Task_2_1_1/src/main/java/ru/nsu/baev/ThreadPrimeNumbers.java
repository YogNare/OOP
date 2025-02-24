package ru.nsu.baev;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThreadPrimeNumbers {

    public boolean allPrimes(List<Integer> array, int numThreads) throws InterruptedException {
        if (!array.isEmpty()) {
            if (Collections.min(array) < 2) {
                return false;
            }
            Integer maxNum = Collections.max(array);
            boolean[] isPrime = createSieve(maxNum + 1, numThreads);
            for (Integer num : array) {
                if (!isPrime[num]) return false;
            }
        }
        return true;
    }

    /**
     * This array contains flags. Flag primes[i] determine that i-th number is prime.
     */
    private static boolean[] createSieve(int limit, int numThreads) throws InterruptedException {
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
            thread.join();
        }

        return isPrime;
    }
}