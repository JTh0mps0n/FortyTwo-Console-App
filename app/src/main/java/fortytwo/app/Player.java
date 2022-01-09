package fortytwo.app;


public class Player {
    private String name;
    private int num;
    private Hand hand;
    private int team; //either 1 or 2

    public Player(String name, int team){
        this.name = name;
        this.hand = null;
        this.team = team;
        num = 0;
    }

    public Player(String name, int id, int team){
        this.name = name;
        this.hand = null;
        this.team = team;
        this.num = id;
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

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }
}
