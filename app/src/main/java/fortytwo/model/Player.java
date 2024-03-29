package fortytwo.model;


public class Player {
    private String name;        //players name
    private Hand hand;          //this players hand
    private Team team;          //team this player is on
    private boolean isBot;
    private int trumpWanted;    //used by bots

    /**
     * Constructor for Player
     *
     * @param name the players name
     */
    public Player(String name, boolean isBot) {
        if(isBot){
            this.name = "bot x";
        }
        else {
            this.name = name;
        }
        this.hand = null;
        this.team = null;
        this.isBot = isBot;
    }

    public boolean isBot() {
        return isBot;
    }

    /**
     * set the players hand
     *
     * @param hand hand to be assigned to player
     * @return string representation of hand
     */
    public String setHand(Hand hand) {
        this.hand = hand;
        return hand.toString();
    }

    /**
     * gets this players hand
     *
     * @return this players hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * get this players team
     *
     * @return this players team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * sets this players team
     *
     * @param team team this player is to be assigned to
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    public int getTrumpWanted() {
        return trumpWanted;
    }

    public void setTrumpWanted(int trumpWanted) {
        this.trumpWanted = trumpWanted;
    }

    /**
     * gets this players name
     *
     * @return this players name
     */
    public String getName() {
        return name;
    }

    /**
     * gets a string representation of this player
     *
     * @return this players name
     */
    @Override
    public String toString() {
        return name;
    }
}
