package ru.nsu.baev;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * helper function for sorting
     * @param arr is integer array.
     * @param n maximal index.
     * @param i swap index.
     */
    public static void heapify(int[] arr, int n, int i) {

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;

        if (largest != i) {
            swap(arr, largest, i);
            heapify(arr, n, largest);
        }
    }

    /**
     * Swap function
     * @param arr is integer array.
     * @param i is first element index.
     * @param j is second element index.
     */
    public static void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * It is heap sort
     * @param arr is integer array.
     */
    public static void heapSort(int[] arr) {

        for (int i = arr.length - 1; i >= 0; i --) {

            heapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i >= 0; i --) {
            swap(arr, i, 0);
            heapify(arr, i, 0);
        }
    }


    public static void printArr(int[] arr) {

        for (int i = 0; i < arr.length; i ++) {

            System.out.print(arr[i]);
        }

        System.out.println("");
    }

    /**
     * main function
     * @param args is basic arguments.
     */
    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                list.add(number);
            }
        }

        int[] numbers = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            numbers[i] = list.get(i);
        }

        heapSort(numbers);

        printArr(numbers);
    }
}