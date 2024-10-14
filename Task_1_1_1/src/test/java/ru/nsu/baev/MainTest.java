package ru.nsu.baev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testHeapSort1() {
        int[] input = {3, 1, 4, 1, 5, 9, 2};
        int[] expected = {1, 1, 2, 3, 4, 5, 9};

        Main.heapSort(input);

        assertArrayEquals(expected, input, "Array should be sorted");
    }

    @Test
    void testHeapSort2() {
        int[] input = {4, 6, 1, 43, 6, 4, 88, 9};
        int[] expected = {1, 4, 4, 6, 6, 9, 43, 88};

        Main.heapSort(input);

        assertArrayEquals(expected, input, "Array should be sorted");
    }

    @Test
    void testHeapSort3() {
        int[] input = {1};
        int[] expected = {1};

        Main.heapSort(input);

        assertArrayEquals(expected, input, "Array should be sorted");
    }

    @Test
    void testHeapSort4() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Main.heapSort(input);

        assertArrayEquals(expected, input, "Array should be sorted");
    }
}
