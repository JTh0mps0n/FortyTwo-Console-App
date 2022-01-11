package fortytwo.app;

import java.util.ArrayList;

public class Trick {
    private int trump; //trump of the round
    private boolean trumped; // if the trick has been trumped
    private int ledSuit; //suit led
    private Domino ledDomino; //domino led
    private ArrayList<Domino> dominoes; //dominoes in the trick
    private Domino winningDomino; //winning domino
    private Player winner; //winning player
    private int points;


    /**
     * Trick constructor
     * <p>
     * defines ledDomino, trump, led, dominos
     *
     * @param ledDomino domino led
     * @param trump     trump of the round
     */
    public Trick(Domino ledDomino, int trump) {
        this.ledDomino = ledDomino;
        this.trump = trump;

        ledDomino.getOwner().getHand().removeDomino(ledDomino);
        if (ledDomino.isSuit(trump)) {
            trumped = true;
            ledSuit = trump;
        } else {
            trumped = false;
            ledSuit = ledDomino.highestPip();
        }
        points += ledDomino.getPoints();
        winningDomino = ledDomino;
        winner = winningDomino.getOwner();
        dominoes = new ArrayList<Domino>();
        dominoes.add(ledDomino);
    }

    public int getPoints() {
        return points;
    }

    public Player getWinner() {
        return winner;
    }

    public int getSize(){
        return dominoes.size();
    }

    public int getLedSuit() {
        return ledSuit;
    }

    @Override
    public String toString(){
        String str = "";
        for (int i = 0; i < dominoes.size(); i++) {
            str += dominoes.get(i).toString() + " ";
        }
        return str;
    }

    /**
     * adds domino to trick and checks for new winner and returns true if the trick is over
     * @param domino the domino that is to be added to the trick
     * @return boolean value indicating if the trick is over
     * */
    public boolean playDomino(Domino domino){
        domino.getOwner().getHand().removeDomino(domino);
        dominoes.add(domino);
        if(domino.isSuit(trump)){//if trump
            trumped = true;
        }
        updateWinner();
        points += domino.getPoints();
        if(dominoes.size() == 4){
            return true;
        }
        return false;
    }

    /**
     * Updates the winner and winningDomino variables appropriately
     */
    private void updateWinner() {
        for (int i = 0; i < dominoes.size(); i++) {
            Domino currentDomino = dominoes.get(i);
            if (winningDomino == currentDomino) {//if the current domino is the winning domino
                continue;
            }
            if (winningDomino.isBeatBy(currentDomino, ledSuit, trump)){//if the current domino beats the winning domino
                winningDomino = currentDomino;
                winner = currentDomino.getOwner();
            }
        }
    }

}
