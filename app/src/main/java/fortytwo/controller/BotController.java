package fortytwo.controller;

import fortytwo.model.*;

import java.util.ArrayList;

public class BotController {

    public BotController(){

    }

    public int getBid(Round round, Player player, boolean dumped){
        Hand hand = player.getHand();
        if(dumped){
            int commonSuit = 0;
            int numInSuit = 0;
            for (int i = 0; i < 7; i++) {
                if(hand.getNumInSuit(i) > numInSuit){
                    numInSuit = hand.getNumInSuit(i);
                    commonSuit = i;
                }
            }
            player.setTrumpWanted(commonSuit);
            return 30;
        }


        int commonSuit = 0;
        int numInSuit = 0;
        for (int i = 0; i < 7; i++) {
            if(hand.getNumInSuit(i) > numInSuit){
                numInSuit = hand.getNumInSuit(i);
                commonSuit = i;
            }
        }
        ArrayList<Integer> suits = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            if(hand.getNumInSuit(i) == numInSuit){
                suits.add(i);
            }
        }

        Boolean containsDouble = false;
        int suit = -1;
        for (int i = 0; i < suits.size(); i++) {
            int currentSuit = suits.get(i);
            for (int j = 0; j < hand.getSize(); j++) {
                if (hand.getDomino(j).isSuit(currentSuit) && hand.getDomino(j).isDouble()){
                    containsDouble = true;
                    suit = currentSuit;
                    break;
                }
            }
            if(containsDouble){
                break;
            }
        }

        if(numInSuit >= 3 && containsDouble){
            player.setTrumpWanted(suit);
            return (round.getBid() + 1);
        }

        return 0;
    }

    public Domino getBestLead(Round round, Player player){
        Hand hand = player.getHand();

        Domino chosen = hand.getDomino(0);
        for (int i = 0; i < hand.getSize(); i++) {
            if(isCertainWinner(hand.getDomino(i), round)){
                chosen = hand.getDomino(i);
                break;
            }
        }
        return chosen;
    }

//    private double getDominoScoreLead(Domino domino, Trick trick){
//        int trump = trick.getTrump();
//        if(domino.isSuit(trump)){
//            if(isCertainWinner())
//        }
//    }

    public Domino getBestDomino(Trick trick, Player player){
        Hand fullHand = player.getHand();
        ArrayList<Domino> possibleDominoes = fullHand.getPossibleDominoes(trick.getLedSuit());
        Team winningTeam = trick.getWinner().getTeam();
        boolean playCount = false;

        if(trick.getSize() == 2 && winningTeam == player.getTeam() && isCertainWinner(trick.getWinningDomino(), trick)){
            playCount = true;
        }
        if(trick.getSize() == 3 && winningTeam == player.getTeam()){
            playCount = true;
        }

        Domino chosen = possibleDominoes.get(0);
        double maxScore = getDominoScore(possibleDominoes.get(0), trick, playCount);

        for (Domino d: possibleDominoes) {
            double currentScore = getDominoScore(d, trick, playCount);
            if(currentScore > maxScore){
                maxScore = currentScore;
                chosen = d;
            }
        }
        return chosen;
    }

    private double getDominoScore(Domino domino, Trick trick, boolean playCount){

        int trump = trick.getTrump();
        int ledSuit = trick.getLedSuit();
        boolean isCount = domino.getPoints() > 0;
        boolean isTrump = domino.isSuit(trump);
        boolean isLedSuit = domino.isSuit(ledSuit);



        double score = 1.0;

        if(playCount && isCount){
            score *= 1000;
            return score;
        }

        if(trick.isTrumped()){
            score *= domino.getRanking(trump);
            if(!isTrump){
                score *= .5;
            }
        }
        else {
            score *= domino.getRanking(ledSuit);
            if(isTrump){
                score *= 3;
                if(domino.isDouble()){
                    score *= 1;
                }
                else{
                    score *= (8-domino.otherSide(trump));
                }
            }
        }
        return score;
    }

    private boolean isCertainWinner(Domino domino, Trick trick){
        Round round = trick.getRound();
        ArrayList<Domino> dominoesLeft = round.getDominoesLeft();

        int ledSuit = trick.getLedSuit();
        int trump = trick.getTrump();

        for (Domino d: dominoesLeft) {
            if(domino.isBeatBy(d, ledSuit, trump)){
                return false;
            }
        }
        return true;
    }

    private boolean isCertainWinner(Domino domino, Round round){
        ArrayList<Domino> dominoesLeft = round.getDominoesLeft();

        int trump = round.getTrump();
        int ledSuit = domino.highestPip();
        if(domino.isSuit(trump)){
            ledSuit = trump;
        }

        for (Domino d: dominoesLeft) {
            if(domino.isBeatBy(d, ledSuit, trump)){
                return false;
            }
        }
        return true;
    }
}
