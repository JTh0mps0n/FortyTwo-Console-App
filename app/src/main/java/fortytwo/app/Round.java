package fortytwo.app;

import java.util.*;

public class Round {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player[] players;
    private Team team1;
    private Team team2;
    private int trump;
    private Player bidWinner;
    private int bid;
    private Hashtable<Domino, Integer> allDominoesTable; //integer represents what player has the domino (0 = played)
    private ArrayList<Domino> allDominoes;
    private Player currentPlayer;
    private Trick currentTrick;
    private ArrayList<Trick> tricks;

    public Round(Player p1, Player p2, Player p3, Player p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        players = new Player[4];
        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[3] = p4;
        team1 = new Team(p1, p3, 1);
        team2 = new Team(p2, p4, 2);
        //initialize dominoes
        allDominoesTable = new Hashtable<Domino, Integer>();
        allDominoes = new ArrayList<Domino>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Domino current = new Domino(i, j);
                allDominoesTable.put(current, -1);
                allDominoes.add(current);
            }
        }

        currentTrick = null;
        tricks = new ArrayList<Trick>();
    }

    /**
     * playRound() plays a full round of forty-two
     */
    public String playRound() {
        generateHands();
        currentPlayer = doBidding();
        trump = getTrumpsConsole(bidWinner);
        for (int i = 0; i < 7; i++) {
            currentPlayer = playTrick();//update current player to winner of this trick
            tricks.add(currentTrick);
        }

        return "";
    }


    /**
     * generateHands() shuffles the dominoes and 'deals' them out to each player.
     */
    private void generateHands() {
        Collections.shuffle(allDominoes);
        Hand hand1 = new Hand(p1);
        Hand hand2 = new Hand(p2);
        Hand hand3 = new Hand(p3);
        Hand hand4 = new Hand(p4);
        for (int i = 0; i < 7; i++) {
            hand1.addDomino(allDominoes.get(i));
            allDominoesTable.put(allDominoes.get(i), 1);

            hand2.addDomino(allDominoes.get(i + 7));
            allDominoesTable.put(allDominoes.get(i + 7), 2);

            hand3.addDomino(allDominoes.get(i + 14));
            allDominoesTable.put(allDominoes.get(i + 14), 3);

            hand4.addDomino(allDominoes.get(i + 21));
            allDominoesTable.put(allDominoes.get(i + 21), 4);
        }
        p1.setHand(hand1);
        p2.setHand(hand2);
        p3.setHand(hand3);
        p4.setHand(hand4);
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
            currentPlayer = players[i];

            boolean dumped = false;//check for last player needing to bid
            if (i == 3 && winningBid == 29) {
                dumped = true;
            }

            bids[i] = getPlayersBidConsole(currentPlayer, i + 1, bids, winningBid, dumped);//get current players bid through console

            if (bids[i] > winningBid) {//check for new winner
                winningBid = bids[i];
                bidWinner = players[i];
            }
        }

        displayBidWinnerConsole(bidWinner, winningBid);//display winner to console
        bid = winningBid;
        return bidWinner;
    }

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
    private int getPlayersBidConsole(Player player, int playerNum, int[] bids, int winningBid, boolean dumped) {
        Scanner input = new Scanner(System.in);

        clear();
        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println("Player " + playerNum + ", " + player.getName() + "'s hand: ");
        printLines(2);
        System.out.println("Press enter to view hand...");
        input.nextLine();
        clear();

        System.out.println(player.getName() + "'s turn to bid.");
        printLines(1);
        System.out.println("Player " + playerNum + ", " + player.getName() + "'s hand: ");
        printHand(player);
        printLines(1);

        printBids(bids);
        printLines(1);

        boolean acceptableInput = false; // to validate input
        int currentBid = 0; //bid given

        while (!acceptableInput) {
            System.out.println("Minimum Bid: " + (winningBid + 1));
            if (dumped) {
                System.out.println("** You MUST bid. **");
                System.out.println("What would you like to bid?");
            } else {
                System.out.println("What would you like to bid? (0 to pass)");
            }
            System.out.print("ENTER BID >> ");
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER\n\n");
                input.next();
                continue;
            }
            currentBid = input.nextInt();
            if (currentBid == 0 && !dumped) {
                acceptableInput = true;
                continue;
            }
            if (currentBid <= winningBid) {
                System.out.println("MUST BE GREATER THAN MINIMUM BID (" + (winningBid + 1) + ")\n\n");
                continue;
            }
            if (currentBid > 42) {
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
    private void displayBidWinnerConsole(Player winner, int winningBid) {
        clear();
        System.out.println(winner.getName() + " won the bid for " + winningBid + "!\n");

        Scanner input = new Scanner(System.in);

        System.out.println("Press enter to continue...");
        input.nextLine();
        clear();
    }

    /**
     * printBids() prints the previous players bids for convenient reading.
     * Helper function for getPlayersBidConsole()
     *
     * @param bids current bids of all players
     */
    private void printBids(int[] bids) {
        System.out.println("BIDS:");
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] == -1) {
                System.out.println(players[i].getName() + "'s bid: YOUR TURN TO BID");
                break;
            }
            if (bids[i] == 0) {
                System.out.println(players[i].getName() + "'s bid: passed");
            } else {
                System.out.println(players[i].getName() + "'s bid: " + bids[i]);
            }
        }
    }

    /**
     * printHand() prints the hand of a given player
     *
     * @param player player whose hand you want to print
     */
    private void printHand(Player player) {
        System.out.println(player.getHand());
    }

    /**
     * getTrumpsConsole() gets the trump of the winners choice
     *
     * @param winner the winner of the bid
     */
    private int getTrumpsConsole(Player winner) {
        Scanner input = new Scanner(System.in);

        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printLines(2);
        System.out.println("Press enter to view hand...");
        input.nextLine();
        clear();

        System.out.println(winner.getName() + "'s turn to pick trumps:");
        printLines(1);
        System.out.println(winner.getName() + "'s Hand:");
        printHand(winner);
        printLines(1);
        boolean acceptableInput = false; // to validate input

        while (!acceptableInput) {
            System.out.println(winner.getName() + ", what would you like to be trumps?");
            System.out.print("ENTER TRUMP >> ");
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER\n\n");
                input.next();
                continue;
            }
            trump = input.nextInt();
            if (trump < 0 || trump > 6) {
                System.out.println("MUST BE BETWEEN 0 and 6\n\n");
                continue;
            }
            acceptableInput = true;
        }
        printLines(1);
        System.out.println(winner.getName() + " has selected " + trump + "'s as trumps!");
        return trump;
    }

    /**
     * plays through the trick part of the round and then returns the winning player
     *
     * @return the winning player
     */
    private Player playTrick() {

        int i = 0;//find index of current player
        for (i = 0; i < 4; i++) {
            if (currentPlayer == players[i]) {
                break;
            }
        }
        currentTrick = new Trick(leadDominoConsole(), trump);

        currentPlayer = players[(i + 1) % 4];
        currentTrick.playDomino(playTurnConsole());
        currentPlayer = players[(i + 2) % 4];
        currentTrick.playDomino(playTurnConsole());
        currentPlayer = players[(i + 3) % 4];
        currentTrick.playDomino(playTurnConsole());

        if (currentTrick.getWinner().getTeam() == team1) {
            team1.addPoints(currentTrick.getPoints() + 1);
            return currentTrick.getWinner();
        } else {
            team2.addPoints(currentTrick.getPoints() + 1);
            return currentTrick.getWinner();
        }
    }

    /**
     * asks given player which domino they would like to play and returns that domino
     *
     * @return the domino selected by the current player
     */
    private Domino playTurnConsole() {
        Scanner input = new Scanner(System.in);

        clear();
        printGameInfo();
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(2);
        System.out.println(currentTrick.toString());
        printLines(3);
        System.out.println("Press enter to show hand...");
        input.nextLine();//wait for input to show hand

        clear();
        printGameInfo();
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(2);
        System.out.println("Led Suit: " + currentTrick.getLedSuit());
        printLines(1);
        System.out.println(currentTrick.toString());
        printLines(3);
        printHand(currentPlayer);

        boolean acceptableInput = false;
        Domino chosenDomino = null;

        while (!acceptableInput) {
            printLines(1);
            if (currentPlayer.getHand().getSize() == 1) {
                System.out.print("Which domino would you like to play? (1, or enter 0 to see round details)");
            } else {
                System.out.println("Which domino would you like to play? (1 - " + currentPlayer.getHand().getSize() + ", or 0 to see round details)");
            }
            printLines(1);
            System.out.print("ENTER NUMBER >> ");
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER");
                input.next();
                continue;
            }
            int i = input.nextInt();
            //input.nextLine();
            if (i == 0) {
                displayRoundHistory();
                printGameInfo();
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                System.out.println(currentTrick.toString());
                printLines(1);
                printHand(currentPlayer);
                continue;
            }
            try {
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
     * asks a player to lead a domino and returns that domino
     *
     * @return the domino they choose.
     */
    private Domino leadDominoConsole() {
        Scanner input = new Scanner(System.in);

        clear();
        printGameInfo();
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Press enter to show hand...");
        input.nextLine();//wait for input to show hand

        clear();
        printGameInfo();
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        printHand(currentPlayer);

        boolean acceptableInput = false;
        Domino chosenDomino = null;

        while (!acceptableInput) {
            printLines(1);
            if (currentPlayer.getHand().getSize() == 1) {
                System.out.println("Which domino would you like to lead? (1, or 0 to see round details)");
            } else {
                System.out.println("Which domino would you like to lead? (1 - " + currentPlayer.getHand().getSize() + ", or 0 to see round details)");
            }
            printLines(1);
            System.out.print("ENTER NUMBER >> ");
            if (!input.hasNextInt()) {
                System.out.println("MUST ENTER AN INTEGER");
                input.next();
                continue;
            }
            int i = input.nextInt();
            //input.nextLine();
            if (i == 0) {
                displayRoundHistory();
                printHand(currentPlayer);
                System.out.println(currentPlayer.getName() + "'s turn.");
                printLines(1);
                printHand(currentPlayer);
                continue;
            }
            input.nextLine();
            try {
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
    private void displayRoundHistory() {
        clear();
        printGameInfo();

        System.out.println("Trick History:");
        System.out.println("Led  Trick              Winner");
        for (int i = 0; i < tricks.size(); i++) {
            Trick trick = tricks.get(i);
            System.out.println(trick.getLedSuit() + "    " + trick.toString() + "   " + trick.getWinner().getTeam().toString() + " " + trick.getWinner().toString());
        }
        printLines(3);
        System.out.println("Team 1 | Team 2");
        System.out.println("   " + team1.getPoints() + "   |   " + team2.getPoints());
        printLines(2);
        Scanner input = new Scanner(System.in);
        System.out.println("Press enter to go back...");
        input.nextLine();
        clear();

    }

    /**
     * print out game info
     * */
    private void printGameInfo(){
        System.out.println("Team 1: " + team1.getPlayers().get(0).getName() + " " + team1.getPlayers().get(1).getName());
        System.out.println("Team 1: " + team2.getPlayers().get(0).getName() + " " + team2.getPlayers().get(1).getName());
        System.out.println("Turn order: " + p1.getName() + " " + p2.getName() + " " + p3.getName() + " " + p4.getName());
        printLines(1);
        System.out.println("Team " + bidWinner.getTeam().toString() + " (" + bidWinner.toString() + ") won the bid for " + bid);
        System.out.println("Trump: " + trump);
        printLines(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printLines(3);
    }

    /**
     * Helper function to print lines to console
     *
     * @param numLines number of lines to print
     */
    private void printLines(int numLines) {
        for (int i = 0; i < numLines; i++) {
            System.out.println();
        }
    }

    /**
     * clears the console
     */
    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        ;
    }


}
