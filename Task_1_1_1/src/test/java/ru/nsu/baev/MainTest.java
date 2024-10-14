package ru.nsu.baev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testGetGreeting() {
        Main main = new Main();
        String greeting = main.getGreeting();
        assertEquals("Hello World!", greeting, "Greeting should be 'Hello World!'");
    }

    @Test
    void testHeapSort() {
        Main main = new Main();
        int[] input = {3, 1, 4, 1, 5, 9, 2};
        int[] expected = {1, 1, 2, 3, 4, 5, 9};

        Main.heapSort(input);

        assertArrayEquals(expected, input, "Array should be sorted");
    }
}
