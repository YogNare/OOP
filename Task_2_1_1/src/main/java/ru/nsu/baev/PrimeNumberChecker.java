package ru.nsu.baev;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrimeNumberChecker {
    /**
     * This array contains flags. Flag primes[i] determine that i-th number is prime.
     */
    private boolean[] isPrime;

    public boolean allPrimes(List<Integer> array) {
        if (!array.isEmpty()) {
            if (Collections.min(array) < 2) {
                return false;
            }
            Integer maxNum = Collections.max(array);
            createSieve(maxNum + 1);
            for (Integer num : array) {
                if (!isPrime[num]) return false;
            }
        }
        return true;
    }

    private void createSieve(int n) {
        isPrime = new boolean[n];
        Arrays.fill(isPrime, true);

        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i < n; i ++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }

    void printPrimes() {
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
