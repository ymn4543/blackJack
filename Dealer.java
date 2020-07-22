/**
 * File: Dealer.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


/**
 * Dealer Class
 */
public class Dealer {
    private Deck deck;          // deck of cards
    private Hand dealerHand;    // dealer's hand
    public Player player;       // player class
    private int standOn;        // value the dealer stands on


    /**
     * Dealer constructor initializes a new dealer
     */
    public Dealer(Deck deck, Player player, int standOn){
        this.deck = deck;
        this.dealerHand = new Hand();
        this.player = player;
        this.standOn = standOn;
    }

    /**
     * shuffleDeck() allows the dealer to randomly shuffle the deck of cards.
     */
    public void shuffleDeck(){
        Collections.shuffle(Arrays.asList(this.deck.getCards()));
    }

    /**
     * startDeal() begins the turn. The dealer deals 2 cards to the player and himself.
     *              Only one of the dealer's cards is revealed to the player, if it is an
     *              Ace, the player is permitted to take an insurance bet. If the player
     *              has a blackjack, they automatically win the round. Otherwise, the
     *              player is prompted to make their next move.
     */
    public void startDeal(){
        // deal the player 2 cards and reveal them
        player.dealToPlayer(deck.drawCard(),deck.drawCard());
        Hand playerHand = player.getHand();
        playerHand.printHand(player);

        // draw two cards for dealer and reveal only one to player
        dealerDrawTwo();

        //if player has BLACKJACK
        if(playerHand.calculateHandValue()==21) {
            int dealerHandVal = dealerHand.calculateHandValue();
            player.blackJack(dealerHandVal);
            return;
        }

        //if dealer face up card is ace, insurance allowed
        if(dealerHand.get(0).getType().equals(CardType.ACE)){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Dealer's face up card is an ace, would you like to place an insurance " +
                    "bet of half your initial bet  " + "("+player.getBet()/2+")? y/n: \n" );
            String input = scanner.next();
            if(input.equals("y")){
                player.placeInsuranceBet();
            }
        }
        //otherwise continue
        playerMove(true);
        return;
    }


    /**
     * dealerDrawTwo() draws 2 new cards from the deck and adds them to the dealer's hand.
     * This is only done at the start at each round. Only one card is revealed, the other is
     * placed face down.
     */
    private void dealerDrawTwo(){
        dealerHand.addToHand(deck.drawCard());
        dealerHand.addToHand(deck.drawCard());
        System.out.print("Dealer draws 2 cards: [" + dealerHand.get(0).toString() +"] [--FACE DOWN--]\n");
    }


    /**
     * playerMove is the main function that controls the game flow. The dealer asks the player
     * what they would like to do depending on the game. If it is the first turn, the player has
     * the choice to double down. If the player has two cards of the same value initially, they can
     * choose to split, etc.
     * @param firstTurn this parameter determines if it is the first turn
     */
    public void playerMove(Boolean firstTurn){
        Boolean canSplit = player.getHand().canSplit(); // is it a splittable hand?
        int numMoves = 2;
        Scanner scanner = new Scanner(System.in);
        System.out.print("What would you like to do: 1-[HIT] 2-[STAND] ");
        if(firstTurn && canSplit){  // if its the first turn and the hand is splittable
            System.out.print("3-[DOUBLE DOWN] 4-[SPLIT]");
            numMoves = 4;
        }
        else if(firstTurn){ // if its the first turn
            System.out.print("3-[DOUBLE DOWN]");
            numMoves = 3;
        }
        System.out.print("\n");
        int playerChoice = scanner.nextInt(); // take in player choice


        if(playerChoice>numMoves||playerChoice<1){
            System.out.print("Invalid choice, please enter a valid number to choose your next move.\n");
            playerMove(firstTurn);
        }
        else {
            switch (playerChoice){
                case 1:                     // if player HITS
                    player.hit();
                    break;
                case 2:
                    player.stand();         // if player STANDS
                    if(player.getHand().equals(player.getHands().get(player.getNumHands()-1))){
                        player.endTurn();   //end turn if this is player's final hand
                    }
                    else{
                        return;
                    }
                    break;
                case 3:                     // if player DOUBLES DOWN
                    player.doubleDown();
                    player.endTurn();
                    break;
                case 4:                         // if player SPLITS
                    boolean aceSplit = false;
                    if(player.getHand().numAces()==2){
                         aceSplit = true;               // check for double aces
                    }
                    ArrayList<Hand> playerHands = player.split();       //let player split
                    for(int x = 0;x<playerHands.size();x++){            // iterate through hands, play each one
                        int plusone = x+1;
                        System.out.print("PLAYING HAND #"+ plusone +": \n");
                        player.setActiveHand(playerHands.get(x));
                        playerHands.get(x).printHand(player);
                        if(aceSplit){
                            System.out.print("You split a pair of aces, you can no longer hit this hand.\n");
                            player.stand();
                        }
                        else {
                            playerMove(true);
                        }
                    }
                    player.endTurn();       //after all hands are played edn the turn
                    break;
            }
        }
        if(player.hasBust()){   //if player busted end the round
            return;
        }
        if(player.isTurnOver()){       //if player did not bust but their turn is over, let the dealer play
            dealerTurn();
            return;
        }
        else {
            playerMove(false);  // otherwise the player continues playing
        }
    }


    /**
     * dealerTurn() allows the dealer to play his hand. the dealer stands on
     * the given standOn setting. The turn begins with the dealer flipping his hidden
     * card, if it is a blackjack, the dealer wins. Otherwise, the dealer will keep hitting
     * until the condition that his hand value is => 17, and will then either stand or have busted.
     */
    private void dealerTurn(){
        System.out.print("The dealer flips his card.\n");   //dealer flips hidden card
        dealerHand.printHand(this);                  // dealer hand revealed to player
        if(dealerHand.calculateHandValue()==21){            // check for blackjack
            System.out.print("Dealer has a blackJack! ");
            if(player.hasPlacedInsurance()){
                player.winInsurance();
            }
            else{
                player.loseBet();
            }
            return;
        }
        while (dealerHand.calculateHandValue()<this.standOn){   // while hand value less than standOn setting, dealer hits
            System.out.print("Dealer hits.\n");
            Card nextCard = deck.drawCard();
            dealerHand.addToHand(nextCard);
            dealerHand.printHand(this);
        }
        if(dealerHand.calculateHandValue()>21){         // if dealer hand value more than 21, dealer busts
            dealerbust();
            player.endTurn();
            return;
        }

        System.out.print("Dealer Stands.\n");
        endGame();                              // end the game after dealer stands
    }


    /**
     * endGame() handles all end game scenarios (wins,ties,losses).
     */
    private void endGame(){
        int dealerVal = dealerHand.calculateHandValue();    // calculate dealer's hand value
        if(player.getNumHands()>1){                         // if player had more than one hand (split)
            player.setActiveHand(player.getHands().get(0));
            int initial = player.getInitialBet();
            for(int x = 0;x<player.getNumHands();x++) {     // perform end game operations on each hand
                Hand hand = player.getHands().get(x);
                int playerVal = hand.calculateHandValue();
                int plusOne = x+1;
                System.out.print("Hand #" + plusOne + " had a value of: " + playerVal +
                        ". Dealer hand had a value of: " + dealerVal + " . "); // print hand val and dealer hand val
                player.setBet(initial);
                if (playerVal == dealerVal) {
                    player.tieGame();
                }
                else if (playerVal > dealerVal) {
                    player.winGame();
                }
                else {
                    player.loseGame();
                }
                player.resetTurn();
            }
        }
        else{                                                       // otherwise only focus on single hand
            int playerVal = player.getHand().calculateHandValue();
            if (playerVal == dealerVal) {
                player.tieGame();
            }
            else if (playerVal > dealerVal) {
                player.winGame();
            }
            else {
                player.loseGame();
            }
        }
    }

    /**
     * dealerbust() gives the win to the player in the case that the dealer busts.
     */
    public void dealerbust(){
        System.out.print("Dealer Bust! The dealer loses this round. \n");
        player.winGame();
    }

    /**
     * clearHands() resets the dealer's hand.
     */
    public void clearHands(){
        this.dealerHand = new Hand();
    }

}
