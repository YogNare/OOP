package ru.nsu.baev;
import java.util.Random;

/**
 * deck class.
 */
public class Deck {

    private int[] main_deck = new int[52];
    private int counter = 0;

    /**
     * init Deck.
     */
    public Deck() {

        for (int i = 0; i < 52; i ++) {
            this.main_deck[i] = i;
        }
    }

    /**
     * Get card.
     * @return number of card.
     */
    public int getCard() {
        this.counter %= 52;
        if (this.counter == 0)
            shuffleArray();

        return this.main_deck[this.counter ++];
    }

    /**
     * shuffle array.
     */
    private void shuffleArray() {

        Random random = new Random();

        for (int i = 51; i > 0; i --) {

            int j = random.nextInt(i + 1);

            int temp = this.main_deck[j];
            this.main_deck[j] = this.main_deck[i];
            this.main_deck[i] = temp;
        }
    }
}