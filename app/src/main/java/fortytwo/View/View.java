package fortytwo.View;

import fortytwo.Model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    /**
     * getPlayersBidConsole() gets the players bid through console input.
     * Helper function for doBidding()
     *
     * @param player     current player bidding
     * @param playerNum  number of player bidding
     * @param bids       current bids of all players
     * @param winningBid bid currently winning
     * @param dumped     boolean representing if the current player is forced to bid
     * @return the bid made by the player
     */
    public static int getPlayersBidConsole(Player player, int playerNum, int[] bids, int winningBid, boolean dumped, Round round) {
        Scanner input = new Scanner(System.in);

        //show whos turn it is to bid and hide hand
        clear();
        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println("Player " + playerNum + ", " + player.getName() + "'s hand: ");
        printLines(2);
        System.out.println("Press enter to view hand...");
        input.nextLine();//wait for input to show hand

        //show whos turn it is to bid and show hand
        clear();
        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println("Player " + playerNum + ", " + player.getName() + "'s hand: ");
        printHand(player);
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
    public static void displayBidWinnerConsole(Player winner, int winningBid) {
        clear();
        System.out.println(winner.getName() + " won the bid for " + winningBid + "!\n");

        Scanner input = new Scanner(System.in);

        System.out.println("Press enter to continue...");
        input.nextLine();//wait for input to continue
    }

    /**
     * printBids() prints the previous players bids for convenient reading.
     * Helper function for getPlayersBidConsole()
     *
     * @param bids current bids of all players
     */
    public static void printBids(int[] bids, Round round) {
        ArrayList<Player> players = round.getPlayers();

        System.out.println("BIDS:");
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] == -1) {
                System.out.println(players.get(i).getName() + "'s bid: YOUR TURN TO BID");
                break;
            }
            if (bids[i] == 0) {
                System.out.println(players.get(i).getName() + "'s bid: passed");
            } else {
                System.out.println(players.get(i).getName() + "'s bid: " + bids[i]);
            }
        }
    }

    /**
     * getTrumpsConsole() gets the trump of the winners choice
     *
     * @param winner the winner of the bid
     */
    public static int getTrumpsConsole(Player winner) {
        int trump = -1;

        Scanner input = new Scanner(System.in);

        //show whos turn it is to pick trumps and hide hand
        clear();
        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printLines(2);
        System.out.println("Press enter to view hand...");
        input.nextLine();//wait for input to show hand

        //show whos turn it is and show hand
        clear();
        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printHand(winner);
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
     * @return the domino selected by the current player
     */
    public static Domino playTurnConsole(Round round) {
        Trick currentTrick = round.getCurrentTrick();

        Player currentPlayer = round.getCurrentPlayer();

        Scanner input = new Scanner(System.in);

        //print whos turn it is and then wait for input to show hand
        clear();
        printGameInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(2);
        System.out.println(currentTrick.toString());
        printLines(3);
        System.out.println("Press enter to show hand...");
        input.nextLine();//wait for input to show hand

        //print whos turn it is and show hand
        clear();
        printGameInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(2);
        System.out.println(currentTrick.toString());
        printLines(3);
        printHand(currentPlayer);

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
                displayRoundHistory(round);
                printGameInfo(round);
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                System.out.println(currentTrick.toString());
                printLines(1);
                printHand(currentPlayer);
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
     * @return the domino they choose.
     */
    public static Domino leadDominoConsole(Round round) {
        Player currentPlayer = round.getCurrentPlayer();

        Scanner input = new Scanner(System.in);

        //print whos turn it is and then wait for input to show hand
        clear();
        printGameInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Press enter to show hand...");
        input.nextLine();//wait for input to show hand

        //print whos turn it is and show hand
        clear();
        printGameInfo(round);
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(5);
        printHand(currentPlayer);


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
                displayRoundHistory(round);
                printGameInfo(round);
                printHand(currentPlayer);
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                printHand(currentPlayer);
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
     */
    public static void displayRoundHistory(Round round) {
        clear();
        printGameInfo(round);

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
        Scanner input = new Scanner(System.in);
        System.out.println("Press enter to go back...");
        input.nextLine();
        clear();
    }

    /**
     * print out game info
     */
    public static void printGameInfo(Round round) {
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
        printWinnerStatement(round);
        System.out.println("Trump: " + trump);
        printLines(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printLines(3);
    }

    /**
     * prints out a statement regarding the winner of the bid
     */
    public static void printWinnerStatement(Round round) {
        Player bidWinner = round.getBidWinner();
        int bid = round.getBid();
        System.out.println("Team " + bidWinner.getTeam().toString() + " (" + bidWinner.toString() + ") won the bid for " + bid);
    }

    /**
     * printHand() prints the hand of a given player
     *
     * @param player player whose hand you want to print
     */
    public static void printHand(Player player) {
        System.out.println(player.getHand());
    }

    /**
     * Helper function to print lines to console
     *
     * @param numLines number of lines to print
     */
    public static void printLines(int numLines) {
        for (int i = 0; i < numLines; i++) {
            System.out.println();
        }
    }

    /**
     * clears the console
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        ;
    }
}
