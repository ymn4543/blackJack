/**
 * File: PLayer.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

import java.util.ArrayList;

/**
 * Player class represents the player the dealer is dealing to.
 */
public class Player {
    private Hand hand;              // player's initial hand
    public Boolean hasSplit;        // has the player split?
    private ArrayList<Hand> hands;  // collection of hands (used if player has split)
    private int bet;                // current bet
    private int netEarnings;        // net earnings
    public Deck deck;               // current deck
    private int currentHand;        // active hand
    private boolean turnOver;       // is the player's turn over?
    private int initialBet;         // initial bet
    private boolean bust;           // has the player bust this round?
    private boolean hasInsurance;   // has the player placed an insurance bet?

    /**
     * Player constructor initializes a new player class.
     */
    public Player(){
        this.hand = new Hand();
        this.deck = null;
        this.hasSplit = false;
        this.bet = 0;
        this.netEarnings = 0;
        this.hands = new ArrayList<>();
        this.hands.add(hand);
        this.currentHand = 0;
        this.turnOver = false;
        this.initialBet = 0;
        this.bust = false;
        this.hasInsurance = false;
    }


    //********** SETTERS ************//

    /**
     * bet setter
     * @param bet the value the bet will be set to
     */
    public void setBet(int bet){
        this.bet = bet;
        this.initialBet = bet;
    }

    /**
     * Active hand setter
     * @param hand the hand that will become the player's active hand
     */
    public void setActiveHand(Hand hand){
        this.hand = hand;
    }

    /**
     * deck setter
     * @param deck the new active deck
     */
    public void setDeck(Deck deck){
        this.deck = deck;
    }


    /**
     * resetTurn() resets the player's turn, making sure that the turnOver and bust
     * booleans are false.
     */
    public void resetTurn(){
        this.turnOver = false;
        this.bust = false;
    }

    /**
     * turnOver boolean setter, ends player's turn.
     */
    public void endTurn(){
        this.turnOver = true;
    }

    /**
     * clearHands() resets the player's hands.
     */
    public void clearHands(){
        this.hand = new Hand();
        this.hands.clear();
        this.hands.add(hand);
    }

    //********** SETTERS ************//



    //********** GETTERS ************//

    /**
     * numHands getter
     * @return the number of hands the player has
     */
    public int getNumHands(){
        return this.hands.size();
    }

    /**
     * hand getter
     * @return the player's active hand
     */
    public Hand getHand(){
        return this.hands.get(currentHand);
    }

    /**
     * Hands getter
     * @return the player's collection of hands
     */
    public ArrayList<Hand> getHands(){
        return this.hands;
    }

    /**
     * netEarnings getter
     * @return the player's net earnings
     */
    public int getNetEarnings(){
        return netEarnings;
    }

    /**
     * initialBet getter
     * @return the player's initial bet
     */
    public int getInitialBet() {
        return initialBet;
    }

    /**
     * bet getter
     * @return the player's active bet
     */
    public int getBet(){
        return this.bet;
    }

    /**
     * turnOver boolean getter used to identify if the player's turn has ended.
     */
    public boolean isTurnOver(){
        return turnOver;
    }

    /**
     * bust boolean getter used to identify if the player has busted this round.
     */
    public boolean hasBust(){
        return bust;
    }


    //********** GETTERS ************//

    //********** BETTING ACTIONS ************//

    /**
     * doubleBet() doubles the player's active bet.
     */
    public void doubleBet(){
        this.bet*=2;
    }

    /**
     * returnBet() returns the player's bet, the bet is reset to 0.
     */
    public void returnBet(){
        this.bet=0;
    }

    /**
     * placeInsuranceBet() allows player to place an insurance bet worth half of their
     * current bet.
     */
    public void placeInsuranceBet(){
        this.bet*=1.5;
        this.hasInsurance = true;
    }

    /**
     * hasPlacedInsurance() indicates if a player has placed an insurance bet.
     */
    public boolean hasPlacedInsurance(){
        return this.hasInsurance;
    }

    /**
     * loseBet() is called when a player loses their bet. Their net earnings are decreased.
     */
    public void loseBet(){
        netEarnings-= this.bet;
        this.bet -= this.bet;
    }

    //********** BETTING ACTIONS ************//



    //********** HAND ACTIONS ************//

    /**
     * dealToPlayer() deals one card to the player's hand.
     * @param card the card being dealt to the player.
     */
    public void dealToPlayer(Card card){
        this.hand.addToHand(card);
    }

    /**
     * dealToPlayer() deals two cards to the player's hand.
     * @param c1 the first card being dealt to the player.
     * @param c2 the second card being dealt to the player.
     */
    public void dealToPlayer(Card c1, Card c2){
        this.hand.addToHand(c1);
        this.hand.addToHand(c2);
    }

    //********** HAND ACTIONS ************//



    //********** PLAYER MOVES ************//

    /**
     * hit() represents a player hitting. The player receives one card from
     *       the dealer with the risk of a bust.
     */
    public void hit(){
        System.out.print("You hit. ");
        dealToPlayer(deck.drawCard());
        Hand playerHand = getHand();
        playerHand.printHand(this);
        if(playerHand.calculateHandValue()>21){
            bust();
        }
    }

    /**
     * stand() represents a player standing. The player receives no more cards
     *         from the dealer, and their turn ends.
     */
    public void stand(){
        System.out.print("You stand. \n");
    }

    /**
     * doubleDown() represents a player doubling down. The player doubles their bet,
     *              receives one more card from the dealer, and then stands.
     */
    public void doubleDown(){
        System.out.print("You double down. You double your bet of $" + this.bet + " to $" +(this.bet*2)+".\n");
        this.doubleBet();
        hit();
        if(!turnOver) {
            stand();
        }
    }

    /**
     * doubleDown() represents a player doubling down. The player doubles their bet,
     *              receives one more card from the dealer, and then stands.
     */
    public ArrayList<Hand> split(){
        System.out.print("You split. ");
        Hand newHand = new Hand();      //give the player a new hand
        Card splitter = hand.get(1);    // split the cards from original hand
        newHand.addToHand(splitter);
        hand.removeFromHand(splitter);
        hands.add(newHand);             // add new hand to collection
        doubleBet();                    // double the bet
        hand.addToHand(deck.drawCard());        //deal new card to each hand
        newHand.addToHand(deck.drawCard());
        return hands;
    }

    /**
     * bust() represents a player busting. When the player's hand reaches
     *        a value higher than 21, they bust and lose the round, regardless
     *        of the dealer's hand.
     */
    public void bust(){
        System.out.print("Bust! The dealer wins this round. You lose your bet of $" + this.bet +"\n");
        loseBet();
        System.out.print("Net earnings: $" + this.netEarnings +"\n");
        this.turnOver = true;
        this.bust = true;
    }

    //********** PLAYER MOVES ************//


    //********** END GAME SCENARIOS ************//

    /**
     * blackJackWin() represents a player winning the round off a blackjack.
     *                The bet is payed back 3:2.
     */
    public void blackJackWin(){
        int initial = getBet();
        this.bet *= 1.5;
        this.netEarnings+=getBet()-initial;
    }

    /**
     * blackJackWin() represents a player having a blackjack. This function
     *                determines if the round ends in a tie (dealer blackjack)
     *                or a win.
     */
    public void blackJack(int dealerHandVal){
        //tie
        if(dealerHandVal==21){
            System.out.print("The dealer also had a blackjack! The round ends in a tie. Your initial bet of $"+this.getBet()+
                    " is returned to you.\nNet earnings: $"+ this.getNetEarnings() +"\n");
            this.returnBet();
        }
        //win
        else{
            this.blackJackWin();
            System.out.print("Blackjack! You win this round. You earned $"+this.getBet() +
                    "\nNet earnings: $"+ this.getNetEarnings() +"\n");
        }
        turnOver = true;
    }

    /**
     * winInsurance() is called when a player has placed an insurance bet after
     *                the dealer drew an ace at the start of the round, and proceeded
     *                to have a blackjack. The bet is payed back 2:1.
     */
    public void winInsurance(){
        int bet = getBet();
        int winnings = (bet/3)*2;
        this.netEarnings-=(bet-winnings);
        System.out.print("Your initial bet of $"+this.initialBet+
                " is returned to you.\nNet earnings: $"+ this.getNetEarnings() +"\n");
        this.returnBet();
        turnOver = true;
    }

    /**
     * tieGame() represents a round that ended in a tie. The player's bet is returned.
     */
    public void tieGame(){
        System.out.print("The round ends in a tie. Your initial bet of $"+this.getBet()+
                " is returned to you.\nNet earnings: $"+ this.getNetEarnings() +"\n");
        this.returnBet();
        turnOver = true;
    }

    /**
     * winGame() represents a round that ended in a win. The player's bet is payed back 1:1.
     */
    public void winGame(){
        int bet = getBet();
        netEarnings+=bet;
        System.out.print("You win this round!. You earned $"+this.getBet()+
                "\nNet earnings: $"+ this.getNetEarnings() +"\n");
        this.returnBet();
        turnOver = true;
    }

    /**
     * loseGame() represents a round that ended in a loss. The player's bet is lost.
     */
    public void loseGame(){
        int bet = getBet();
        loseBet();
        System.out.print("You lose this round!. You lose your bet of $"+bet+
                "\nNet earnings: $"+ this.getNetEarnings() +"\n");
        turnOver = true;
    }

    //********** END GAME SCENARIOS ************//

}

