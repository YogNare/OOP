package ru.nsu.baev;

import java.util.ArrayList;
import java.util.List;


/**
 * hand class.
 */
public class Hand {

    public int sum = 0;
    protected int aceCount = 0;
    protected Deck deck;
    protected final String[] cardValue = {"2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A"};
    protected final String[] cardSuit = {"♠", "♣", "♥", "♦"};
    protected final Integer[] intCardValue = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    protected List<Integer> hand = new ArrayList<>();


    /**
     * delay function.
     *
     * @param second - delay time.
     */
    public static void sleepy(int second) {
        try {
            Thread.sleep(second * 1000); // Задержка на 1 секунду
        } catch (InterruptedException e) {
            System.out.println("Поток был прерван");
        }
    }

    /**
     *
     * @return 1 if sum > 21 else 0.
     */
    public int takeCard() {
        int card = this.deck.getCard();

        this.hand.add(card);

        if (card % 13 == 12) {
            this.aceCount += 1;
            this.sum += 11;
        }
        else
            this.sum += this.intCardValue[card % 13];

        while (this.aceCount > 0 && this.sum > 21) {
            this.sum -= 10;
            this.aceCount -= 1;
        }

        if (this.sum > 21) return 1;
        else return 0;
    }

    /**
     * clear hand.
     */
    public void reset() {
        this.hand = new ArrayList<>();
        this.aceCount = 0;
        this.sum = 0;
    }

    /**
     * clear hand.
     * give 2 cards.
     *
     * @return 1 if blackjack else 0.
     */
    public int startRound(){
        reset();
        takeCard();
        takeCard();

        if (this.sum == 21)
            return 1;
        else
            return 0;
    }
//    public void firstDistribution() {
//        deck.getCard();
//        deck.getCard();
//    }

}