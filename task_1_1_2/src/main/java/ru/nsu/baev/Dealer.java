package ru.nsu.baev;

import java.util.Scanner;

/**
 * dealer class.
 */
public class Dealer extends Hand {

    int win = 0;

    /**
     * init function.
     * @param deck - Deck deck.
     */
    public Dealer(Deck deck) {
        this.deck = deck;
    }

    /**
     * print dealer cards.
     * @param visible - visible, if visible == 0 1 card not visible else all cards visible.
     */
    public void printCards(int visible) {
        if (visible == 0) {
            System.out.println("Bot: ");
            String suit = this.cardSuit[this.hand.get(0)/13];
            String value = this.cardValue[this.hand.get(0)%13];
            System.out.printf(
                    "┌───────┐ ┌───────┐\n" +
                    "│%s     %s│ │?     ?│\n" +
                    "│       │ │       │\n" +
                    "│   %s   │ │   ?   │\n" +
                    "│       │ │       │\n" +
                    "│%s     %s│ │?     ?│\n" +
                    "└───────┘ └───────┘\n\n", suit, suit, value, suit, suit);
        }
        else {
            System.out.println("Bot: " + sum);

            String top = "┌───────┐ ";
            String middle1 = "│%s     %s│ ";
            String middle2 = "│       │ ";
            String middle3 = "│   %s   │ ";
            String bottom = "└───────┘ ";

            // Печатаем карты в одну строку
            System.out.print(top.repeat(this.hand.size()) + "\n");

            for (Integer card : this.hand) {
                String suit = this.cardSuit[card / 13];
                System.out.printf(middle1, suit, suit);
            }
            System.out.print("\n");

            System.out.print(middle2.repeat(this.hand.size()) + "\n");

            for (Integer card : this.hand) {
                String value = this.cardValue[card % 13];
                System.out.printf(middle3, value);
            }
            System.out.print("\n");

            System.out.print(middle2.repeat(this.hand.size()) + "\n");

            for (Integer card : this.hand) {
                String suit = this.cardSuit[card / 13];
                System.out.printf(middle1, suit, suit);
            }
            System.out.print("\n");

            System.out.print(bottom.repeat(this.hand.size()) + "\n\n");
        }
    }

    /**
     * logic of dealer.
     * @param scanner - Scanner scanner.
     * @param player - player.
     * @return 1 if sum > 21 else 0.
     */
    public int dealerTurn(Scanner scanner, Player player) {
        int result = 0;
        while (this.sum < 17) {
            result = takeCard();

            sleepy(1);

            System.out.print("\033[2J");
            printCards(1);
            player.printCards();
        }

        return result;
    }
}