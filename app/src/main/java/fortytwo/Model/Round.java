package fortytwo.Model;

import java.util.*;
import fortytwo.View.*;
import fortytwo.Controller.*;

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
     * playRound() plays a full round of forty-two
     */
    public String playRound() {
        //generate hands
        generateHands();

        //do bidding and set current player to winner of the bid
        currentPlayer = doBidding();

        //ask user for trumps and assign the value to trump
        trump = View.getTrumpsConsole(currentPlayer);

        //play 7 tricks
        for (int i = 0; i < 7; i++) {
            currentPlayer = playTrick();//update current player to winner of this trick
            tricks.add(currentTrick);//add trick to history of tricks
        }

        return "";
    }


    /**
     * generateHands() shuffles the dominoes and 'deals' them out to each player.
     */
    private void generateHands() {
        //shuffle all dominoes
        Collections.shuffle(allDominoes);

        //create new hands
        Hand hand1 = new Hand(p1);
        Hand hand2 = new Hand(p2);
        Hand hand3 = new Hand(p3);
        Hand hand4 = new Hand(p4);

        //add dominoes to hands
        for (int i = 0; i < 7; i++) {
            hand1.addDomino(allDominoes.get(i));

            hand2.addDomino(allDominoes.get(i + 7));

            hand3.addDomino(allDominoes.get(i + 14));

            hand4.addDomino(allDominoes.get(i + 21));
        }
    }


    /**
     * doBidding() performs the bidding section of the round and changes the class variables appropriately
     *
     * @return winner of the bid
     */
    private Player doBidding() {


        int[] bids = {-1, -1, -1, -1};   //players bids in player order 1,2,3,4
        int winningBid = 29; //winning bid number
        bidWinner = p1;//winner of the bid

        for (int i = 0; i < 4; i++) {
            currentPlayer = players.get(i);

            //check for last player needing to bid
            boolean dumped = false;
            if (i == 3 && winningBid == 29) {
                dumped = true;
            }

            //get bid
            bids[i] = View.getPlayersBidConsole(currentPlayer, i + 1, bids, winningBid, dumped, this);//get current players bid through console

            //check for new winner
            if (bids[i] > winningBid) {
                winningBid = bids[i];
                bidWinner = players.get(i);
            }
        }

        //display winner to console
        View.displayBidWinnerConsole(bidWinner, winningBid);

        bid = winningBid;
        return bidWinner;
    }

    /**
     * plays through the trick part of the round and then returns the winning player
     *
     * @return the winning player
     */
    private Player playTrick() {

        int i = 0;//find index of current player
        for (i = 0; i < 4; i++) {
            if (currentPlayer == players.get(i)) {
                break;
            }
        }

        //create new trick
        currentTrick = new Trick(View.leadDominoConsole(this), trump);

        //take 3 subsequent turns
        currentPlayer = players.get((i + 1) % 4);
        currentTrick.playDomino(View.playTurnConsole(this));
        currentPlayer = players.get((i + 2) % 4);
        currentTrick.playDomino(View.playTurnConsole(this));
        currentPlayer = players.get((i + 3) % 4);
        currentTrick.playDomino(View.playTurnConsole(this));

        //add points for trick to the winners points for the round
        if (currentTrick.getWinner().getTeam() == team1) {
            team1.addPoints(currentTrick.getPoints() + 1);
            return currentTrick.getWinner();
        } else {
            team2.addPoints(currentTrick.getPoints() + 1);
            return currentTrick.getWinner();
        }
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
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

    public int getBid() {
        return bid;
    }

    public Player getBidWinner() {
        return bidWinner;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public Trick getCurrentTrick() {
        return currentTrick;
    }
}
