/**
 * File: Card.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

/**
 * Card class represents a single card in a standard 52-card deck.
 */
public class Card {

    private Suit suit;      // the card suit
    private int value;      // the card value
    private CardType type;  // the card type

    /**
     * Card constructor initializes a new Card.
     */
    public Card(Suit suit, int value, CardType type){
        this.suit = suit;
        this.value = value;
        this.type = type;
    }

    /**
     * getValue() returns the value of the card.
     */
    public int getValue(){
        return this.value;
    }

    /**
     * getType() returns the type of the card.
     */
    public CardType getType(){
        return this.type;
    }

    /**
     * toString returns a string representation of the card.
     * @return
     */
    @Override
    public String toString() {
        if(this.type == CardType.NORMAL){
            return value + " of " + this.suit.toString();
        }
        else {
            return type + " of " + this.suit.toString();
        }
    }


}
