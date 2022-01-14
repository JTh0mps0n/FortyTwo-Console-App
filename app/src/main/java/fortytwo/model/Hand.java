package fortytwo.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Domino> dominoes;     //dominoes in this hand
    private Player owner;                   //player who owns this hand


    /**
     * Constructor for hand
     *
     * @param owner the player who owns this hand
     */
    public Hand(Player owner) {
        dominoes = new ArrayList<>();
        this.owner = owner;
        owner.setHand(this);
    }

    /**
     * gets the size of this players hand
     *
     * @return number dominoes in this players hand
     * */
    public int getSize() {
        return dominoes.size();
    }

    /**
     * returns the domino at index i
     *
     * @throws IndexOutOfBoundsException if the domino at index i doesn't exist
     * @param i the index of the wanted domino
     * @return domino at index i
     */
    public Domino getDomino(int i) {
        if(i > dominoes.size() || i < 0){
            throw new IndexOutOfBoundsException();
        }
        return dominoes.get(i);
    }

    /**
     * addDomino() adds a new domino to this hand
     *
     * @param d domino to be added
     * @return the domino's toString()
     */
    public String addDomino(Domino d) {
        d.setOwner(owner);
        dominoes.add(d);
        return d.toString();
    }

    /**
     * addDomino() creates and adds a new domino to this hand
     *
     * @param pip1 one side of the domino.
     * @param pip2 other side of the domino
     * @return the domino's toString()
     */
    public String addDomino(int pip1, int pip2) {
        Domino newDomino = new Domino(pip1, pip2);
        newDomino.setOwner(owner);
        dominoes.add(newDomino);
        return newDomino.toString();
    }

    /**
     * removes given domino from hand
     *
     * @param domino domino to be removed from hand
     * */
    public void removeDomino(Domino domino){
        dominoes.remove(domino);
    }

    /**
     * gets a string representation of the dominoes in this hand concatenated by spaces
     *
     * @return string representation of hand
     * */
    @Override
    public String toString() {
        String handStr = "";
        for (int i = 0; i < dominoes.size(); i++) {
            handStr += dominoes.get(i).toString();
            if (i != dominoes.size() - 1) {
                handStr += " ";
            }
        }
        return handStr;
    }

    /**
     * returns whether the hand contains a domino of the given suit
     *
     * @param suit the suit to check for
     * @return boolean indicating whether the hand contains a domino of the given suit
     * */
    private boolean containsSuit(int suit){
        boolean contains = false;
        //look through each domino and check if it contains the suit
        for (Domino domino : dominoes) {
            if (domino.isSuit(suit)) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * gets the possible dominoes that this hand could play given the suit led
     *
     * @param ledSuit the suit led this trick
     * @return ArrayList of Dominoes that could be played from this hand given the suit led
     * */
    public ArrayList<Domino> getPossibleDominoes(int ledSuit){
        ArrayList<Domino> subHand = new ArrayList<Domino>();

        //determine if the hand contains the ledSuit
        boolean containsLedSuit = containsSuit(ledSuit);

        if(containsLedSuit){//if the hand contains the ledSuit
            //copy dominoes over if they are the ledSuit
            for (Domino domino: dominoes) {
                if(domino.isSuit(ledSuit)){
                    subHand.add(domino);
                }
            }
        }
        else{//if the hand doesn't contain the ledSuit, return all dominoes
            for (Domino domino : dominoes){
                subHand.add(domino);
            }
        }

        return subHand;
    }
}
