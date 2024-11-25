package ru.nsu.baev;

import java.util.Scanner;

/**
 * blackjack class
 */
public class Blackjack {

    private final Scanner scanner = new Scanner(System.in);
    private Deck deck = new Deck();
    private Dealer dealer = new Dealer(deck);
    private Player player = new Player(deck);

//    private int checkResult() {
//
//        if (this.player.sum > 21) {
//            return 2;
//        }
//        if (this.player.sum)
//
//        return 0;
//    }

    /**
     * init dealer.
     * init player.
     *
     * @return 3 if both blackjack, 2 if dealer blackjack, 1 if player blackjack else 0.
     */
    private int initRound() {
        int dealer_blackjack = this.dealer.startRound();
        int player_blackjack = this.player.startRound();
        dealer.printCards(0);
        player.printCards();

        if (dealer_blackjack == 1 && player_blackjack == 1) {
            this.dealer.win ++;
            this.player.win ++;
            return 3;
        } else if (dealer_blackjack == 1) {
            this.dealer.win ++;
            return 2;
        } else if (player_blackjack == 1) {
            this.player.win ++;
            return 1;
        }

        return 0;
    }

    /**
     * print score.
     */
    private void printScore() {
        System.out.println("player " + this.player.win + "/" + this.dealer.win + " dealer");
    }

    /**
     * print result.
     *
     * @param res - result.
     * @param blackjack - if somebody has blackjack.
     */
    private void printResult(int res, int blackjack) {
        dealer.printCards(1);
        player.printCards();

        if (blackjack == 3) {
            System.out.println("how...\ndraw");
        } else if (blackjack == 2) {
            System.out.println("What the hell\n" +
                    "BLACKJACK\n" +
                    "CASINO WIN!!!");
        } else if (blackjack == 1) {
            System.out.println("What the hell\n" +
                    "BLACKJACK\n" +
                    "YOU WIN!!!");
        } else {
            if (res == 2) {
                System.out.println("dealer win");
            } else if (res == 1) {
                System.out.println("you win");
            } else {
                System.out.println("draw");
            }
        }

        printScore();
    }

    /**
     * game func.
     */
    public void game() {

        boolean end_game = false;

        String choice = "";
        int result;
        int blackjack_res;
        int round = 1;


        while(!end_game) {
            System.out.println("Do you want play round " + round ++);
            choice = this.scanner.nextLine();
            if (choice.equals("no")) {
                return;
            }

            result = 0;

            blackjack_res = initRound();
            if (blackjack_res > 0){
                printResult(result, blackjack_res);
                continue;
            }

            result = this.player.playerTurn(scanner, dealer);
            if (result == 1) {
                this.dealer.win ++;
                printResult(2, 0);
                continue;
            }

            result = this.dealer.dealerTurn(scanner, player);
            if (result == 1) {
                this.player.win ++;
                printResult(1, 0);
                continue;
            }

            if (this.dealer.sum > this.player.sum) {
                this.dealer.win ++;
                printResult(2, 0);
            }
            else if (this.dealer.sum == this.player.sum) {
                this.player.win ++;
                this.dealer.win ++;
                printResult(0, 0);
            }
            else {
                this.player.win ++;
                printResult(1, 0);
            }

        }
    }

    /**
     * main.
     *
     * @param args - args for main.
     */
    public static void main(String[] args){
        Blackjack black_game = new Blackjack();

        black_game.game();
    }
}