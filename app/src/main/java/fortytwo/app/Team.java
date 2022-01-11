package fortytwo.app;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players;
    private int points;
    private int num;

    public Team(Player p1, Player p2, int num){
        players = new ArrayList<Player>();
        players.add(p1);
        p1.setTeam(this);
        players.add(p2);
        p2.setTeam(this);
        points = 0;
        this.num = num;
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
