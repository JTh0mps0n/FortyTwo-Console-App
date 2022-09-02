package fortytwo.model;

import java.util.*;
import fortytwo.view.*;
import fortytwo.controller.*;

public class Round {
    private Player p1;                       //player 1 (team 1)
    private Player p2;                       //player 2 (team 2)
    private Player p3;                       //player 3 (team 1)
    private Player p4;                       //player 4 (team 2)
    private ArrayList<Player> players;       //ArrayList of players
    private Team team1;                      //team 2
    private Team team2;                      //team 1
    private ArrayList<Team> teams;           //ArrayList of teams
    private int trump;                       //the trump of this round
    private Player bidWinner;                //winner of the bid
    private int bid;                         //the bid that won the bidding process
    private ArrayList<Domino> allDominoes;   //all dominoes in a list used to generate hands
    private Player currentPlayer;            //current player who should be playing through console
    private Trick currentTrick;              //current trick being played
    private ArrayList<Trick> tricks;         //list of tricks played throughout this round
    private Game game;                       //game this round belongs to


    /**
     * Round Constructor taking in the 4 players playing
     *
     * @param game game to be used to create this round
     */
    public Round(Game game) {
        this.game = game;

        //set players and add them to player array
        this.p1 = game.getPlayers().get(0);
        this.p2 = game.getPlayers().get(1);
        this.p3 = game.getPlayers().get(2);
        this.p4 = game.getPlayers().get(3);
        this.players = new ArrayList<Player>();
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
        this.players.add(p4);

        //create teams
        team1 = game.getTeam1();
        team2 = game.getTeam2();
        this.teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);
        team1.resetTeamForRound();
        team2.resetTeamForRound();

        //initialize dominoes
        allDominoes = new ArrayList<Domino>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Domino current = new Domino(i, j);
                allDominoes.add(current);
            }
        }

        //setup for tricks
        currentTrick = null;
        tricks = new ArrayList<Trick>();
    }

    public ArrayList<Domino> getAllDominoes() {
        return allDominoes;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Trick> getTricks() {
        return tricks;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTrump() {
        return trump;
    }

    public void setTrump(int trump) {
        this.trump = trump;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Player getBidWinner() {
        return bidWinner;
    }

    public void setBidWinner(Player bidWinner) {
        this.bidWinner = bidWinner;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public Trick getCurrentTrick() {
        return currentTrick;
    }

    public Game getGame() {
        return game;
    }

    public Team getWinner(){
        if(bidWinner.getTeam().getPoints() >= bid){
            return bidWinner.getTeam();
        }
        else{
            if(bidWinner.getTeam() == team1){
                return team2;
            }
            return team1;
        }
    }

    public ArrayList<Domino> getDominoesLeft(){
        ArrayList<Domino> dominoesLeft = new ArrayList<Domino>();
        for (int i = 0; i < players.size(); i++) {
            Hand currentHand = players.get(i).getHand();
            for (int j = 0; j < currentHand.getSize(); j++) {
                dominoesLeft.add(currentHand.getDomino(j));
            }
        }
        return dominoesLeft;
    }

    public void setCurrentTrick(Trick currentTrick) {
        this.currentTrick = currentTrick;
    }

    public void reorderHands(){
        p1.getHand().reorder(trump);
        p2.getHand().reorder(trump);
        p3.getHand().reorder(trump);
        p4.getHand().reorder(trump);
    }
}
