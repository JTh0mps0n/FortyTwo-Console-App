package fortytwo.app;

import java.util.*;

public class Round {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player[] players;
    private int trump;
    private Player bidWinner; //1 or 2 for team 1 or team 2
    private int bid;
    private int t1Points;
    private int t2Points;
    private Hashtable<Domino, Integer> allDominoesTable; //integer represents what player has the domino (0 = played)
    private ArrayList<Domino> allDominoes;
    private Player currentPlayer;

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
        t1Points = 0;
        t2Points = 0;

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
    }

    /**
     * playRound() plays a full round of forty-two
     */
    public String playRound() {
        generateHands();
        doBidding();
        getTrumpsConsole(bidWinner);

        return "";
    }


    /**
     * generateHands() shuffles the dominoes and 'deals' them out to each player.
     */
    private void generateHands() {
        Collections.shuffle(allDominoes);
        Hand hand1 = new Hand(1);
        Hand hand2 = new Hand(2);
        Hand hand3 = new Hand(3);
        Hand hand4 = new Hand(4);
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
     */
    private void doBidding() {


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

        displayWinnerConsole(bidWinner, winningBid);//display winner to console

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

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Player " + playerNum + ", " + player.getName() + "'s hand: ");
        printHand(player);
        System.out.println();

        printBids(bids);
        System.out.println();

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
    private void displayWinnerConsole(Player winner, int winningBid) {
        System.out.println();
        System.out.println();
        System.out.println(winner.getName() + " won the bid for " + winningBid + "!");
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
                System.out.println("Player " + (i + 1) + ", " + players[i].getName() + "'s bid: YOUR TURN TO BID");
                break;
            }
            if (bids[i] == 0) {
                System.out.println("Player " + (i + 1) + ", " + players[i].getName() + "'s bid: passed");
            } else {
                System.out.println("Player " + (i + 1) + ", " + players[i].getName() + "'s bid: " + bids[i]);
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
     * @param winner the winner of the bid
     * */
    private void getTrumpsConsole(Player winner){
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println();
        System.out.println(winner.getName() + "'s Hand:");
        printHand(winner);
        System.out.println();
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
        System.out.println();
        System.out.println(winner.getName() + " has selected " + trump + "'s as trumps!");
    }
}
