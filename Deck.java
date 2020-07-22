/**
 * File: Deck.Java
 * Date: 7/22/20
 * Author: Youssef Naguib </ymn4543@rit.edu>
 */

/**
 * Deck class represents a deck of cards.
 */
public class Deck {
    private static int NUM_CARDS = 52; //number of cards in the deck
    private Card[] cards;              // array of cards
    private int cardsLeft;             // number of cards remaining in deck


    /**
     * Deck constructor initializes new deck.
     */
    public Deck(){
        this.cards = new Card[NUM_CARDS];   // new array to store cards
        this.cardsLeft = 51;                // cards left is by index so 51 will be last card
        int card_position = 0;
        Suit[] suit = Suit.values();
        for(int x = 0;x<4;x++){         //for each suit, add the cards into deck
            //make ace
            this.cards[card_position] = new Card(suit[x],1,CardType.ACE);
            //make 2-10
            for(int i = 2;i<11;i++){
                this.cards[card_position+i-1] = new Card(suit[x],i,CardType.NORMAL);
            }
            //make royals
            this.cards[card_position+10] = new Card(suit[x],10,CardType.JACK);
            this.cards[card_position+11] = new Card(suit[x],10,CardType.QUEEN);
            this.cards[card_position+12] = new Card(suit[x],10,CardType.KING);
            card_position+=13;
        }
    }

    /**
     * cards getter returns the array of cards.
     */
     public Card[] getCards(){
         return this.cards;
     }

    /**
     * drawCard draws the top card from the deck and returns it
     */
     public Card drawCard(){
        this.cardsLeft-=1;
        return cards[this.cardsLeft+1];
     }

     public void swapCards(int index1, int index2){
         Card c1 = this.cards[index1];
         this.cards[index1] = this.cards[index2];
         this.cards[index2] = c1;
     }

    /**
     * toString() prints out all the cards in deck, mainly used for testing
     * purposes.
     */
    @Override
    public String toString() {
        for(int c = 0;c<52;c++){
            System.out.print(this.cards[c].toString() + "\n");
        }
        return "\n";
    }








}
