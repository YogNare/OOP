package ru.nsu.baev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class PrimeNumbers {
    private boolean[] primes;

    private void createSieve(int n) {
        if (n < 2) {
            primes = new boolean[0];
            return;
        }

        primes = new boolean[n];
        Arrays.fill(primes, true);

        primes[0] = primes[1] = false;

        for (int i = 2; i * i < n; i ++) {
            if (primes[i]) {
                for (int j = i * i; j < n; j += i) {
                    primes[j] = false;
                }
            }
        }
    }

    public boolean notAllPrime(ArrayList<Integer> array) {
        if (!array.isEmpty()) {
            Integer maxNum = Collections.max(array);
            createSieve(maxNum + 1);
            if (primes.length < 1) return true;
            for (Integer num : array) {
                if (!primes[num]) return true;
            }
        }

        return false;
    }

    public void printPrimes() {
        for (int i = 0; i < primes.length; i++) {
            if (primes[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }


}
