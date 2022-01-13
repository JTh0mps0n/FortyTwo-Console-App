package fortytwo.Model;

import java.util.ArrayList;

public class Trick {
    private int trump;                      //trump of the round
    private boolean trumped;                //if the trick has been trumped
    private int ledSuit;                    //suit led
    private Domino ledDomino;               //domino led
    private ArrayList<Domino> dominoes;     //dominoes in the trick
    private Domino winningDomino;           //winning domino
    private Player winner;                  //winning player
    private int points;                     //points this trick contains


    /**
     * Trick constructor
     * <p>
     * defines ledDomino, trump, led, dominos
     *
     * @param ledDomino domino led
     * @param trump     trump of the round
     */
    public Trick(Domino ledDomino, int trump) {
        //set parameters to variables
        this.ledDomino = ledDomino;
        this.trump = trump;

        //remove ledDomino from players hand
        ledDomino.getOwner().getHand().removeDomino(ledDomino);

        if (ledDomino.isSuit(trump)) {//if the domino led is a trump
            trumped = true;
            ledSuit = trump;//led suit is the trump
        } else {
            trumped = false;
            ledSuit = ledDomino.highestPip();//get led suit
        }

        //add points from domino led to total
        points += ledDomino.getPoints();

        //assign winner and winning domino
        winningDomino = ledDomino;
        winner = winningDomino.getOwner();

        //add domino led to list of dominoes in the trick
        dominoes = new ArrayList<Domino>();
        dominoes.add(ledDomino);
    }

    /**
     * get the points this trick is worth
     *
     * @return points this trick is worth
     * */
    public int getPoints() {
        return points;
    }

    /**
     * get the winner of the trick
     *
     * @return the winner of the trick
     * */
    public Player getWinner() {
        return winner;
    }

    /**
     * get the size of this trick
     *
     * @return the size of this trick
     * */
    public int getSize(){
        return dominoes.size();
    }

    /**
     * get the suit led in this trick
     *
     * @return integer representing the suit led in this trick
     * */
    public int getLedSuit() {
        return ledSuit;
    }

    /**
     * toString() for trick with the dominoes concatenated by spaces
     *
     * @return string representation of this trick
     * */
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
        //remove this domino from the players hand and add it to this tricks list of dominos
        domino.getOwner().getHand().removeDomino(domino);
        dominoes.add(domino);
        if(domino.isSuit(trump)){//if the domino is a trump
            trumped = true;
        }

        //update the winner of this trick and add points from the domino to the trick
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
        //loop through dominoes checking to find the current winning domino
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
