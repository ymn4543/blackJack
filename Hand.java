/**
 * File: Hand.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

import java.util.ArrayList;

/**
 * Hand class represents a hand of cards.
 */
public class Hand {
    private ArrayList<Card> hand; //a list of cards

    /**
     * Hand constructor initializes new hand.
     */
    public Hand(){
        this.hand = new ArrayList<Card>(); //empty hand
    }

    /**
     * numAces() returns the number of aces in the hand.
     */
    public int numAces(){
        int num = 0;
        for(int x = 0;x<hand.size();x++){
            if(hand.get(x).getType().equals(CardType.ACE)){
                num+=1;
            }
        }
        return num;
    }

    /**
     * calculateHandValue() calculates the value of a hand, a key aspect
     *                      of blackjack.
     */
    public int calculateHandValue(){
        int total = 0;
        for(int x = 0;x<hand.size();x++){                       // add up non-aces in hand
            if(!hand.get(x).getType().equals(CardType.ACE)){
                total += hand.get(x).getValue();
            }
        }
        if(this.numAces()==2){          //if 2 aces present, add 12 to value
            total+=12;
        }
        else if(this.numAces()==1){     //if one ace present
            if(total+11>21){            // if ace being worth 11 results in bust
                total+=1;               // ace will be worth 1
            }
            else {total+=11;}           // otherwise ace worth 11
        }

        return total;
    }

    /**
     * addToHand() adds a card to the hand.
     * @param card card to be added
     */
    public void addToHand(Card card){
        hand.add(card);
    }

    /**
     * removeFromHand() removes a card from the hand.
     * @param card card to be added
     */
    public void removeFromHand(Card card){
        hand.remove(card);
    }

    /**
     * get() returns a card from the hand without removing it
     * @param index is the index of the card
     * @return a card
     */
    public Card get(int index){
        return hand.get(index);
    }

    /**
     * canSplit() determines if a hand can be split. This is true if
     *            the first 2 cards in the hand have same value.
     */
    public Boolean canSplit(){
        Card c1 = this.hand.get(0);
        Card c2 = this.hand.get(1);
        return(c1.getValue() == c2.getValue());
    }

    /**
     * printHand(Dealer dealer) prints the dealer's hand out along with the hand value.
     */
    public void printHand(Dealer dealer){
        int dealerHandVal = calculateHandValue();
        String handString = "Dealer hand is: ";
        for(int x = 0;x<hand.size();x++){
            handString += "[" + hand.get(x).toString() + "]";
        }
        handString += " Dealer hand value: " + dealerHandVal +"\n";
        System.out.print(handString);
    }

    /**
     * printHand(PLayer player) prints the player's hand out along with the hand value.
     */
    public void printHand(Player player){
        int handVal = calculateHandValue();
        String handString = "You have been dealt: ";
        for(int x = 0;x<hand.size();x++){
            handString += "[" +hand.get(x).toString() + "] ";
        }
        handString += " Hand value: " + handVal +"\n";
        System.out.print(handString);
    }

}
