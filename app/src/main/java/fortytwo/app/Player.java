package fortytwo.app;


public class Player {
    private String name;
    private int num;
    private Hand hand;
    private Team team; //either 1 or 2

    public Player(String name){
        this.name = name;
        this.hand = null;
        this.team = null;
        num = 0;
    }


    public String setHand(Hand hand){
        this.hand = hand;
        return hand.toString();
    }

    public Hand getHand() {
        return hand;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
