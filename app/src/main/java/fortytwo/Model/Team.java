package fortytwo.model;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players;      //players in the team
    private int points;                     //points the team has
    private int num;                        //team number(1 or 2)

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

        //set team number
        this.num = num;
    }

    /**
     * get players in a team
     *
     * @return ArrayList of players
     * */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * get points this team has
     *
     * @return the number of points this team has
     * */
    public int getPoints() {
        return points;
    }

    /**
     * add points to this teams total points
     *
     * @param points points to add
     * */
    public void addPoints(int points){
        this.points += points;
    }

    /**
     * toString for team
     *
     * @return team number as a string
     * */
    @Override
    public String toString() {
        return num + "";
    }
}
