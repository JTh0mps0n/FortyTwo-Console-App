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

    /**
     * Round Constructor taking in the 4 players playing
     *
     * @param p1 player 1 (team 1)
     * @param p2 player 2 (team 2)
     * @param p3 player 3 (team 1)
     * @param p4 player 4 (team 2)
     */
    public Round(Player p1, Player p2, Player p3, Player p4) {
        //set players and add them to player array
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        //create teams
        team1 = new Team(p1, p3, 1);
        team2 = new Team(p2, p4, 2);
        teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);

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

    /**
     * Round Constructor taking in the 4 players playing
     *
     * @param players ArrayList of players
     */
    public Round(ArrayList<Player> players) {
        //set players and add them to player array
        this.p1 = players.get(0);
        this.p2 = players.get(1);
        this.p3 = players.get(2);
        this.p4 = players.get(3);
        this.players = new ArrayList<Player>();
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
        this.players.add(p4);

        //create teams
        team1 = new Team(p1, p3, 1);
        team2 = new Team(p2, p4, 2);
        teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);

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

    public void setCurrentTrick(Trick currentTrick) {
        this.currentTrick = currentTrick;
    }
}
