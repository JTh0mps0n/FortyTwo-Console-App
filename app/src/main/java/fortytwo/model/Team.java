package fortytwo.model;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players;      //players in the team
    private int points;                     //points the team has in the round
    private int num;                        //team number(1 or 2)
    private int marks;

    /**
     * Team Constructor
     *
     * @param p1 player 1
     * @param p2 player 2
     * @param num team number (1 or 2)
     * */
    public Team(Player p1, Player p2, int num){
        //create new team of players and add p1 and p2
        players = new ArrayList<Player>();
        players.add(p1);
        p1.setTeam(this);
        players.add(p2);
        p2.setTeam(this);

        //set points
        points = 0;
        marks = 0;

        //set team number
        this.num = num;
    }

    public void resetTeamForRound(){
        points = 0;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void addMarks(int marks){
        this.marks += marks;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    @Override
    public String toString() {
        return num + "";
    }
}
