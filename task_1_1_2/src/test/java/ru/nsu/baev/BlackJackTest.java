package ru.nsu.baev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlackJackTest {

    @Test
    public void whenAssertingArraysEquality() {
        char[] expected = {'J','u','n','i','t'};
        char[] actual = "Junit".toCharArray();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testTakeCard() {
        Deck deck = new Deck();
        Player player = new Player(deck);

//        pla
    }
}