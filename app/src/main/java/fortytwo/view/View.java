package fortytwo.view;

import fortytwo.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public View() {
    }

    public void printStartScreen() {
        clear();
        printGameTitle();
        printLines(3);
        waitForInput("Press enter to play...");
    }

    public ArrayList<Player> getNewPlayers() {
        Scanner input = new Scanner(System.in);
        clear();
        printGameTitle();
        printLines(3);
        System.out.print("Enter Team 1, Player 1 Name: ");
        String name1 = input.nextLine();
        System.out.print("Enter Team 1, Player 2 Name: ");
        String name3 = input.nextLine();
        System.out.print("Enter Team 2, Player 1 Name: ");
        String name2 = input.nextLine();
        System.out.print("Enter Team 2, Player 2 Name: ");
        String name4 = input.nextLine();

        Player p1;
        if(name1.equalsIgnoreCase("bot")){
            p1 = new Player(name1, true);
        }
        else{
            p1 = new Player(name1, false);
        }
        Player p2;
        if(name2.equalsIgnoreCase("bot")){
            p2 = new Player(name2, true);
        }
        else{
            p2 = new Player(name2, false);
        }
        Player p3;
        if(name3.equalsIgnoreCase("bot")){
            p3 = new Player(name3, true);
        }
        else{
            p3 = new Player(name3, false);
        }
        Player p4;
        if(name4.equalsIgnoreCase("bot")){
            p4 = new Player(name4, true);
        }
        else{
            p4 = new Player(name4, false);
        }

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        return players;
    }

    public void displayRoundEnding(Round round){
        clear();
        printGameInfo(round.getGame());
        displayRoundHistory(round, false);
        printLines(3);
        Team winner = round.getWinner();
        System.out.println("Team " + winner.toString() + "(" + winner.getPlayers().get(0).toString() + " " +
                winner.getPlayers().get(1).toString() + ") won the round with " + winner.getPoints() + " points!");
        printLines(3);
        waitForInput("Press enter to continue...");
    }

    public void displayGameEnding(Game game){
        clear();
        printGameInfo(game);
        printLines(3);
        System.out.println("Team " + game.getWinner().toString() + "(" + game.getWinner().getPlayers().get(0) + " and " +
                game.getWinner().getPlayers().get(1) + ") won!");
        printLines(3);
    }

    /**
     * getPlayersBidConsole() gets the players bid through console input.
     * Helper function for doBidding()
     *
     * @param player     current player bidding
     * @param bids       current bids of all players
     * @param winningBid bid currently winning
     * @param dumped     boolean representing if the current player is forced to bid
     * @param round      the round for which this is being called for
     * @return the bid made by the player
     */
    public int getPlayersBidConsole(Player player, int[] bids, int winningBid, boolean dumped, Round round) {
        Scanner input = new Scanner(System.in);

        //show whos turn it is to bid and hide hand
        clear();
        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println(player.getName() + "'s hand: ");
        printLines(2);
        waitForInput();

        //show whos turn it is to bid and show hand
        clear();
        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println(player.getName() + "'s hand: ");
        printHand(player, false);
        printLines(1);

        //display bids from all players
        printBids(bids, round);
        printLines(1);

        boolean acceptableInput = false; // to validate input
        int currentBid = 0; //bid given

        //keep prompting for bid until acceptable input given
        while (!acceptableInput) {

            //prompt user to input bid
            System.out.println("Minimum Bid: " + (winningBid + 1));
            if (dumped) {//if they are forced to bid
                System.out.println("** You MUST bid. **");
                System.out.println("What would you like to bid?");
            } else {
                System.out.println("What would you like to bid? (0 to pass)");
            }
            System.out.print("ENTER BID >> ");

            //if they entered a non integer character
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER");
                printLines(2);
                input.next();
                continue;
            }

            //get input
            currentBid = input.nextInt();

            if (currentBid == 0 && !dumped) { //if they passed and are allowed to pass
                acceptableInput = true;
                continue;
            }

            if (currentBid <= winningBid) {//if the current bid is too low
                System.out.println("MUST BE GREATER THAN MINIMUM BID (" + (winningBid + 1) + ")\n\n");
                continue;
            }

            if (currentBid > 42) {//if the current bid too high
                System.out.println("CANNOT BE GREATER THAN 42\n\n");
                continue;
            }
            acceptableInput = true;
        }

        return currentBid;
    }

    /**
     * displayWinnerConsole() prints out the winner of the bid to the console
     *
     * @param winner     winner of the bid
     * @param winningBid winning bid
     */
    public void displayBidWinnerConsole(Player winner, int winningBid) {
        clear();
        System.out.println(winner.getName() + " won the bid for " + winningBid + "!\n");

        Scanner input = new Scanner(System.in);

        waitForInput("Press enter to continue...");
    }

    /**
     * printBids() prints the previous players bids for convenient reading.
     * Helper function for getPlayersBidConsole()
     *
     * @param bids  current bids of all players
     * @param round the round for which this is being called for
     */
    public void printBids(int[] bids, Round round) {
        ArrayList<Player> players = round.getPlayers();

        System.out.println("BIDS:");
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] == -1) {
                System.out.println(players.get((i + round.getGame().getRoundsPlayed()) % 4).getName() + "'s bid: YOUR TURN TO BID");
                break;
            }
            if (bids[i] == 0) {
                System.out.println(players.get((i + round.getGame().getRoundsPlayed()) % 4).getName() + "'s bid: passed");
            } else {
                System.out.println(players.get((i + round.getGame().getRoundsPlayed()) % 4).getName() + "'s bid: " + bids[i]);
            }
        }
    }

    /**
     * getTrumpsConsole() gets the trump of the winners choice
     *
     * @param winner the winner of the bid
     */
    public int getTrumpsConsole(Player winner) {
        int trump = -1;

        Scanner input = new Scanner(System.in);

        //show whos turn it is to pick trumps and hide hand
        clear();
        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printLines(2);
        waitForInput();

        //show whos turn it is and show hand
        clear();
        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printHand(winner, false);
        printLines(1);

        boolean acceptableInput = false; // to validate input

        //keep prompting for input until they enter a valid input
        while (!acceptableInput) {
            System.out.println(winner.getName() + ", what would you like to be trumps?");
            System.out.print("ENTER TRUMP >> ");

            //if they enter a non integer character
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER");
                printLines(2);
                input.next();
                continue;
            }

            //get input
            trump = input.nextInt();

            //if their integer is not a valid suit
            if (trump < 0 || trump > 6) {
                System.out.println("MUST BE BETWEEN 0 and 6");
                printLines(2);
                continue;
            }
            acceptableInput = true;
        }

        printLines(1);
        System.out.println(winner.getName() + " has selected " + trump + "'s as trumps!");

        return trump;
    }

    /**
     * asks given player which domino they would like to play and returns that domino
     *
     * @param round the round for which this is being called for
     * @return the domino selected by the current player
     */
    public Domino playTurnConsole(Round round) {
        Trick currentTrick = round.getCurrentTrick();

        Player currentPlayer = round.getCurrentPlayer();

        Scanner input = new Scanner(System.in);

        //print whos turn it is and then wait for input to show hand
        clear();
        printRoundInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(2);
        System.out.println(currentTrick.toString());
        printLines(3);
        waitForInput();

        //print whos turn it is and show hand
        clear();
        printRoundInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(2);
        System.out.println(currentTrick.toString());
        printLines(3);
        printHand(currentPlayer, true);

        boolean acceptableInput = false;
        Domino chosenDomino = null;

        //keep prompting for input until they give acceptable input
        while (!acceptableInput) {
            printLines(1);

            //ask for which domino they want to play
            if (currentPlayer.getHand().getSize() == 1) {
                System.out.print("Which domino would you like to play? (1, or enter 0 to see round details)");
            } else {
                System.out.println("Which domino would you like to play? (1 - " + currentPlayer.getHand().getSize() + ", or 0 to see round details)");
            }
            printLines(1);
            System.out.print("ENTER NUMBER >> ");

            if (!input.hasNextInt()) {//if they didn't enter an integer
                System.out.println("MUST ENTER AN INTEGER");
                input.next();
                continue;
            }
            int i = input.nextInt();

            if (i == 0) {//if they want to show round history
                displayRoundHistory(round, true);
                clear();
                printRoundInfo(round);
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                System.out.println(currentTrick.toString());
                printLines(1);
                printHand(currentPlayer, true);
                continue;
            }


            try {//attempt to choose the domino at index i-1
                chosenDomino = currentPlayer.getHand().getDomino(i - 1);
            } catch (Exception e) {
                System.out.println("MUST ENTER AN INTEGER BETWEEN 1 AND " + currentPlayer.getHand().getSize());
                continue;
            }

            //get the dominoes they're allowed to play
            ArrayList<Domino> dominoesAllowed = currentPlayer.getHand().getPossibleDominoes(currentTrick.getLedSuit());
            if (!dominoesAllowed.contains(chosenDomino)) {//if the domino they chose isn't allowed
                System.out.println("CANNOT PLAY THAT DOMINO. MUST FOLLOW SUIT.");
                continue;
            }
            acceptableInput = true;
        }

        return chosenDomino;
    }

    /**
     * asks a player to lead a domino and returns that domino
     *
     * @param round the round for which this is being called for
     * @return the domino they choose.
     */
    public Domino leadDominoConsole(Round round) {
        Player currentPlayer = round.getCurrentPlayer();

        Scanner input = new Scanner(System.in);

        //print whos turn it is and then wait for input to show hand
        clear();
        printRoundInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        waitForInput();

        //print whos turn it is and show hand
        clear();
        printRoundInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(5);
        printHand(currentPlayer, true);


        boolean acceptableInput = false;
        Domino chosenDomino = null;

        //keep prompting for input until they give acceptable input
        while (!acceptableInput) {
            printLines(1);

            //ask for which domino they want to lead
            if (currentPlayer.getHand().getSize() == 1) {
                System.out.println("Which domino would you like to lead? (1, or 0 to see round details)");
            } else {
                System.out.println("Which domino would you like to lead? (1 - " + currentPlayer.getHand().getSize() + ", or 0 to see round details)");
            }
            printLines(1);
            System.out.print("ENTER NUMBER >> ");

            if (!input.hasNextInt()) {//if didn't enter an integer
                System.out.println("MUST ENTER AN INTEGER");
                input.next();
                continue;
            }

            //get input
            int i = input.nextInt();
            input.nextLine();
            if (i == 0) {//if they want to show round history
                displayRoundHistory(round, true);
                clear();
                printRoundInfo(round);
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                printHand(currentPlayer, true);
                continue;
            }

            try {//attempt to choose the domino at index i-1
                chosenDomino = currentPlayer.getHand().getDomino(i - 1);
            } catch (Exception e) {
                System.out.println("MUST ENTER AN INTEGER BETWEEN 1 AND " + currentPlayer.getHand().getSize());
                continue;
            }
            acceptableInput = true;
        }
        return chosenDomino;
    }

    /**
     * print out round details and await response
     *
     * @param round the round for which this is being called for
     */
    public void displayRoundHistory(Round round, boolean stallGame) {
        clear();
        printGameInfo(round.getGame());
        printLines(1);
        printRoundInfo(round);

        ArrayList<Trick> tricks = round.getTricks();
        Team team1 = round.getTeams().get(0);
        Team team2 = round.getTeams().get(1);

        System.out.println("Trick History:");
        System.out.println("Led  Trick              Winner");
        for (int i = 0; i < tricks.size(); i++) {
            Trick trick = tricks.get(i);
            System.out.println(trick.getLedSuit() + "    " + trick.toString() + "   " + trick.getWinner().getTeam().toString() + " " + trick.getWinner().toString());
        }
        printLines(3);
        System.out.println("Team 1 | Team 2");
        System.out.println("   " + team1.getPoints() + "       " + team2.getPoints());
        printLines(2);
        if(stallGame) {
            waitForInput("Press enter to go back...");
        }
    }

    public void printGameInfo(Game game) {
        printGameTitle();
        printLines(1);
        System.out.println("Team 1 - " + game.getTeam1().getPlayers().get(0) + " " + game.getTeam1().getPlayers().get(1) + ": " + game.getTeam1Score());
        System.out.println("Team 2 - " + game.getTeam2().getPlayers().get(0) + " " + game.getTeam2().getPlayers().get(1) + ": " + game.getTeam2Score());
        printLines(1);
        System.out.println("First team to " + game.getPlayTo() + " marks wins!");
    }

    /**
     * print out game info
     *
     * @param round the round for which this is being called for
     */
    public void printRoundInfo(Round round) {
        ArrayList<Player> players = round.getPlayers();
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        Player p3 = players.get(2);
        Player p4 = players.get(3);

        ArrayList<Team> teams = round.getTeams();
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);

        int trump = round.getTrump();

        System.out.println("Team 1: " + team1.getPlayers().get(0).getName() + " " + team1.getPlayers().get(1).getName());
        System.out.println("Team 1: " + team2.getPlayers().get(0).getName() + " " + team2.getPlayers().get(1).getName());
        System.out.println("Turn order: " + p1.getName() + " " + p2.getName() + " " + p3.getName() + " " + p4.getName());
        printLines(1);
        printBidWinnerStatement(round);
        System.out.println("Trump: " + trump);
        printLines(1);
        printTildes();
        printLines(3);
    }

    private void printTildes() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public void waitForInput() {
        Scanner input = new Scanner(System.in);
        System.out.print("Press enter to show hand...");
        input.nextLine();//wait for input to show hand
    }

    public void waitForInput(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);
        input.nextLine();//wait for input to show hand
    }

    /**
     * prints out a statement regarding the winner of the bid
     *
     * @param round the round for which this is being called for
     */
    public void printBidWinnerStatement(Round round) {
        Player bidWinner = round.getBidWinner();
        int bid = round.getBid();
        System.out.println("Team " + bidWinner.getTeam().toString() + " (" + bidWinner.toString() + ") won the bid for " + bid);
    }

    /**
     * printHand() prints the hand of a given player
     *
     * @param player player whose hand you want to print
     */
    public void printHand(Player player, boolean numbered) {
        if(numbered){
            System.out.println(player.getHand().toStringNumbered());
            return;
        }
        System.out.println(player.getHand());
    }

    /**
     * Helper function to print lines to console
     *
     * @param numLines number of lines to print
     */
    public void printLines(int numLines) {
        for (int i = 0; i < numLines; i++) {
            System.out.println();
        }
    }

    private void printGameTitle() {
        printTildes();
        printLines(1);
        System.out.println("                                FORTY TWO");
        printLines(1);
        printTildes();
    }

    /**
     * clears the console
     */
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
