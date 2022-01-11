package fortytwo.app;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Domino> dominoes;
    private Player owner;

    public Hand(Player owner) {
        dominoes = new ArrayList<>();
        this.owner = owner;
    }

    public int getSize() {
        return dominoes.size();
    }

    /**
     * returns the domino at index i
     *
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
        for (int i = 0; i < dominoes.size(); i++) {
            if(dominoes.get(i) == domino){
                dominoes.remove(i);
            }
        }
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
}
