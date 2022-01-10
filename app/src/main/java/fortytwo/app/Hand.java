package fortytwo.app;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Domino> Dominoes;
    private int owner;

    public Hand(int owner){
        Dominoes = new ArrayList<>();
        this.owner = owner;
    }

    /**
     * addDomino() creates and adds a new domino to this hand
     * @param pip1 one side of the domino.
     * @param pip2 other side of the domino
     * @return the domino's toString()
     * */
    public String addDomino(int pip1, int pip2){
        Domino newDomino = new Domino(pip1, pip2);
        newDomino.setOwner(owner);
        Dominoes.add(newDomino);
        return newDomino.toString();
    }

    /**
     * addDomino() adds a new domino to this hand
     * @param d domino to be added
     * @return the domino's toString()
     * */
    public String addDomino(Domino d){
        d.setOwner(owner);
        Dominoes.add(d);
        return d.toString();
    }


    @Override
    public String toString(){
        String handStr = "";
        for (int i = 0; i < Dominoes.size(); i++) {
            handStr += Dominoes.get(i).toString();
            if (i != Dominoes.size() - 1){
                handStr += " ";
            }
        }
        return handStr;
    }
}
