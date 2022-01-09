package fortytwo.app;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Domino> Dominoes;

    public Hand(){
        Dominoes = new ArrayList<>();
    }

    //add domino to hand and return string format of new domino
    public String addDomino(int pip1, int pip2){
        Domino newDomino = new Domino(pip1, pip2);
        Dominoes.add(newDomino);
        return newDomino.toString();
    }
    public String addDomino(Domino d){
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
