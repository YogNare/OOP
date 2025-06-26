package ru.nsu.baev;

import java.util.Scanner;

/** player class. */
public class Player extends Hand {

  int win = 0;

  /**
   * init function.
   *
   * @param deck - Deck deck.
   */
  public Player(Deck deck) {
    this.deck = deck;
  }

  /** print player cards. */
  public void printCards() {

    System.out.println("Player: " + sum);

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

  /**
   * logic of player.
   *
   * @param scanner - Scanner scanner.
   * @param dealer - Dealer dealer.
   * @return 1 if sum > 21 else 0.
   */
  public int playerTurn(Scanner scanner, Dealer dealer) {
    String choice;
    int result = 0;
    while (true) {
      System.out.println("Card?");
      choice = scanner.nextLine();

      if (choice.equals("yes")) {
        result = takeCard();
      } else if (choice.equals("no")) {
        break;
      }

      System.out.print("\033[2J");
      dealer.printCards(0);
      printCards();

      if (result > 0) {
        break;
      }
    }

    return result;
  }
}
