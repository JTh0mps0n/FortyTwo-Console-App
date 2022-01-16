package fortytwo.controller;

import fortytwo.view.View;
import fortytwo.model.*;

import java.util.ArrayList;
import java.util.Collections;

public class GameController {

    private View view;

    public GameController(){
        view = new View();
    }

    public Game createGame(){
        view.printStartScreen();

        ArrayList<Player> players = view.getNewPlayers();
        ArrayList<Team> teams = new ArrayList<Team>();
        Team team1 = new Team(players.get(0), players.get(2), 1);
        Team team2 = new Team(players.get(1), players.get(3), 2);
        teams.add(team1);
        teams.add(team2);

        Game game = new Game(players, teams);

        view.clear();
        view.printGameInfo(game);
        view.printLines(3);
        view.waitForInput("Press enter to continue...");

        return game;
    }

    public void playGame(Game game){
        while(game.getTeam1Score() < game.getPlayTo() && game.getTeam2Score() < game.getPlayTo()){
            Team winner = playRound(game);
            game.addWinner(winner, 1);
        }
        view.displayGameEnding(game);
    }

    /**
     * playRound() plays a full round of forty-two
     *
     * @param game the game this round is being played in
     */
    public Team playRound(Game game) {
        Round round = new Round(game);
        //generate hands
        generateHands(round);

        //do bidding and set current player to winner of the bid
        round.setCurrentPlayer(doBidding(round));

        //ask user for trumps and assign the value to trump
        round.setTrump(view.getTrumpsConsole(round.getCurrentPlayer()));

        //play 7 tricks
        for (int i = 0; i < 7; i++) {
            round.setCurrentPlayer(playTrick(round));//update current player to winner of this trick
            round.getTricks().add(round.getCurrentTrick());//add trick to history of tricks
        }
        view.displayRoundEnding(round);
        return round.getWinner();
    }

    /**
     * generateHands() shuffles the dominoes and 'deals' them out to each player.
     *
     * @param round the round for which this is being called for
     */
    private void generateHands(Round round) {
        //shuffle all dominoes
        Collections.shuffle(round.getAllDominoes());

        //create new hands
        Hand hand1 = new Hand(round.getPlayers().get(0));
        Hand hand2 = new Hand(round.getPlayers().get(1));
        Hand hand3 = new Hand(round.getPlayers().get(2));
        Hand hand4 = new Hand(round.getPlayers().get(3));

        //add dominoes to hands
        for (int i = 0; i < 7; i++) {
            hand1.addDomino(round.getAllDominoes().get(i));

            hand2.addDomino(round.getAllDominoes().get(i + 7));

            hand3.addDomino(round.getAllDominoes().get(i + 14));

            hand4.addDomino(round.getAllDominoes().get(i + 21));
        }
    }

    /**
     * doBidding() performs the bidding section of the round and changes the class variables appropriately
     *
     * @param round the round for which this is being called for
     * @return winner of the bid
     */
    private Player doBidding(Round round) {
        int[] bids = {-1, -1, -1, -1};   //players bids in player order 1,2,3,4
        int winningBid = 29; //winning bid number
        round.setBidWinner(round.getPlayers().get(0));//winner of the bid

        Player currentPlayer;
        for (int i = 0; i < 4; i++) {
            currentPlayer = round.getPlayers().get((i + round.getGame().getRoundsPlayed()) % 4);

            //check for last player needing to bid
            boolean dumped = false;
            if (i == 3 && winningBid == 29) {
                dumped = true;
            }

            //get bid
            bids[i] = view.getPlayersBidConsole(currentPlayer, bids, winningBid, dumped, round);//get current players bid through console

            //check for new winner
            if (bids[i] > winningBid) {
                winningBid = bids[i];
                round.setBidWinner(round.getPlayers().get((i + round.getGame().getRoundsPlayed()) % 4));
            }
        }

        //display winner to console
        view.displayBidWinnerConsole(round.getBidWinner(), winningBid);

        round.setBid(winningBid);
        return round.getBidWinner();
    }

    /**
     * plays through the trick part of the round and then returns the winning player
     *
     * @param round the round for which this is being called for
     * @return the winning player
     */
    private Player playTrick(Round round) {

        int i = 0;//find index of current player
        for (i = 0; i < 4; i++) {
            if (round.getCurrentPlayer() == round.getPlayers().get(i)) {
                break;
            }
        }

        //create new trick
        round.setCurrentTrick(new Trick(view.leadDominoConsole(round), round.getTrump()));

        //take 3 subsequent turns
        round.setCurrentPlayer(round.getPlayers().get((i + 1) % 4));
        round.getCurrentTrick().playDomino(view.playTurnConsole(round));
        round.setCurrentPlayer(round.getPlayers().get((i + 2) % 4));
        round.getCurrentTrick().playDomino(view.playTurnConsole(round));
        round.setCurrentPlayer(round.getPlayers().get((i + 3) % 4));
        round.getCurrentTrick().playDomino(view.playTurnConsole(round));

        //add points for trick to the winners points for the round
        if (round.getCurrentTrick().getWinner().getTeam() == round.getTeams().get(0)) {
            round.getTeams().get(0).addPoints(round.getCurrentTrick().getPoints() + 1);
            return round.getCurrentTrick().getWinner();
        } else {
            round.getTeams().get(1).addPoints(round.getCurrentTrick().getPoints() + 1);
            return round.getCurrentTrick().getWinner();
        }
    }
}
