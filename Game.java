/**
 * File: Game.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

import java.util.Scanner;

/**
 * Game class represents a game of blackJack.
 */
public class Game {
    private Boolean gameOver;   //represents if a game is over

    /**
     * Game constructor initializes new game class.
     */
    public Game(){
        this.gameOver = false;
    }

    /**
     * startGame introduces the game to the player and asks for an initial bet.
     * @return the initial bet
     */
    private int startGame(){
        System.out.print("Welcome to blackJack, Settings: Dealer STANDS on 17, 1 deck of cards\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Place initial bet: ");
        return scanner.nextInt();
    }

    /**
     * playAgain() asks the player if they'd like to play another round.
     * @return boolean representing yes or no
     */
    private Boolean playAgain(){
        System.out.print("Would you like to play again? y/n: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();
        if(choice.equals("y")){
            return true;
        }
        else {
            System.out.print("Thanks for playing!\n");
            return false;
        }
    }

    public void startnewGame(){
        String[] s = new String[0];
        main(s);
    }

    //-----------TEST DECKS----------//
    public Deck[] initTestDecks(){
        Deck[] testDecks = new Deck[5];
        //double split
        Deck DoubleSplitdeck = new Deck();
        DoubleSplitdeck.swapCards(47,38);
        testDecks[0] = DoubleSplitdeck;

        //split
        Deck Splitdeck = new Deck();
        testDecks[1] = Splitdeck;

        //insurance
        Deck insuranceDeck = new Deck();
        insuranceDeck.swapCards(0,49);
        testDecks[2] = insuranceDeck;

        //blackJack
        Deck blackJack = new Deck();
        blackJack.swapCards(51,0);
        testDecks[3] = blackJack;

        //ace split
        Deck aceSplit = new Deck();
        aceSplit.swapCards(51,0);
        aceSplit.swapCards(50,13);
        testDecks[4] = aceSplit;

        return testDecks;
    }



    /**
     * main() is the head of the entire operation. This function will initialize
     *        a new game, player, dealer, and deck. startGame() will be called to
     *        take the player's bet, the dealer than shuffles the deck and begins
     *        to deal. At the end of the round the player is asked if they'd like
     *        play again, if that is the case the hands are reset and the process
     *        repeats.
     */
    public static void main(String[] args){
        Game game = new Game();             // new game
        Deck[] testDecks = game.initTestDecks();
        Player player = new Player();       // new player
        while (!game.gameOver) {
            int bet = game.startGame();     // take bet
            Deck deck = new Deck();         // new deck
            player.setBet(bet);
            player.setDeck(deck);

            //TESTING OCCURS HERE// (Turn off shuffling to test the tester decks)
            //Deck tester = testDecks[4];
            //player.setDeck(tester);


            Dealer dealer = new Dealer(deck,player,17); //new dealer stands on 17
            dealer.shuffleDeck();         // dealer shuffles cards
            dealer.startDeal();           // dealer begins to deal
            if(!game.playAgain()){
                return;
            }
            player.clearHands();        // hands and turns reset
            dealer.clearHands();
            player.resetTurn();
        }
    }




}

