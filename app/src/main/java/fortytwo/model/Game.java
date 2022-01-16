package fortytwo.model;

import fortytwo.controller.*;
import fortytwo.view.*;

import java.util.ArrayList;

public class Game {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private ArrayList<Player> players;
    private Team team1;
    private Team team2;
    private ArrayList<Team> teams;
    private int roundsPlayed;
    private int playTo = 2;                 //play to this amount of marks

    public Game(ArrayList<Player> players, ArrayList<Team> teams){
        p1 = players.get(0);
        p2 = players.get(1);
        p3 = players.get(2);
        p4 = players.get(3);
        this.players = new ArrayList<Player>();
        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);
        this.players.add(p4);

        team1 = teams.get(0);
        team2 = teams.get(1);
        this.teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);

        roundsPlayed = 0;
    }

    /**
     * add a winner of a round to the game
     *
     * @return true if the game is over, false otherwise
     * */
    public boolean addWinner(Team team, int numMarks){
        roundsPlayed += 1;
        team.addMarks(numMarks);

        if(team.getMarks() >= playTo){
            return true;
        }
        return false;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayTo() {
        return playTo;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int getTeam1Score() {
        return team1.getMarks();
    }

    public int getTeam2Score(){
        return team2.getMarks();
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public Team getWinner(){
        if(getTeam1Score() > getTeam2Score()){
            return team1;
        }
        return team2;
    }
}
