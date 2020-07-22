/**
 * suit enum represents the types of suits a card can be.
 */

public enum Suit {

    CLUBS,
    DIAMONDS,
    SPADES,
    HEARTS;

    /**
     * toString() returns a string representation of the suit type.
     */
    public String toString(){
        String s = "";
        if(this.ordinal()==0){
            s= "♣";
        }
        if(this.ordinal()==1){
            s= "♦";
        }
        if(this.ordinal()==2){
            s= "♠";
        }
        if(this.ordinal()==3){
            s= "♥";
        }
        return s;
    }
}

