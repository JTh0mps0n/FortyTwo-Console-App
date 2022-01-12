package fortytwo.app;

import java.util.*;

public class Round {
    private Player p1;                       //player 1 (team 1)
    private Player p2;                       //player 2 (team 2)
    private Player p3;                       //player 3 (team 1)
    private Player p4;                       //player 4 (team 2)
    private Player[] players;                //array of players
    private Team team1;                      //team 2
    private Team team2;                      //team 1
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
     * */
    public Round(Player p1, Player p2, Player p3, Player p4) {
        //set players and add them to player array
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        players = new Player[4];
        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[3] = p4;

        //create teams
        team1 = new Team(p1, p3, 1);
        team2 = new Team(p2, p4, 2);

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
        trump = getTrumpsConsole(bidWinner);

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
            currentPlayer = players[i];

            //check for last player needing to bid
            boolean dumped = false;
            if (i == 3 && winningBid == 29) {
                dumped = true;
            }

            //get bid
            bids[i] = getPlayersBidConsole(currentPlayer, i + 1, bids, winningBid, dumped);//get current players bid through console

            //check for new winner
            if (bids[i] > winningBid) {
                winningBid = bids[i];
                bidWinner = players[i];
            }
        }

        //display winner to console
        displayBidWinnerConsole(bidWinner, winningBid);

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
        printBids(bids);
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
                System.out.println("MUST ENTER AN INTEGER\n\n");
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
    private void displayBidWinnerConsole(Player winner, int winningBid) {
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

        //create new trick
        currentTrick = new Trick(leadDominoConsole(), trump);

        //take 3 subsequent turns
        currentPlayer = players[(i + 1) % 4];
        currentTrick.playDomino(playTurnConsole());
        currentPlayer = players[(i + 2) % 4];
        currentTrick.playDomino(playTurnConsole());
        currentPlayer = players[(i + 3) % 4];
        currentTrick.playDomino(playTurnConsole());

        //add points for trick to the winners points for the round
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

        //print whos turn it is and then wait for input to show hand
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

        //print whos turn it is and show hand
        clear();
        printGameInfo();
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
                displayRoundHistory();
                printGameInfo();
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
    private Domino leadDominoConsole() {
        Scanner input = new Scanner(System.in);

        //print whos turn it is and then wait for input to show hand
        clear();
        printGameInfo();
        System.out.println(currentPlayer.getName() + "'s turn.");
        printLines(1);
        System.out.println("Press enter to show hand...");
        input.nextLine();//wait for input to show hand

        //print whos turn it is and show hand
        clear();
        printGameInfo();
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
                displayRoundHistory();
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
    private void printGameInfo() {
        System.out.println("Team 1: " + team1.getPlayers().get(0).getName() + " " + team1.getPlayers().get(1).getName());
        System.out.println("Team 1: " + team2.getPlayers().get(0).getName() + " " + team2.getPlayers().get(1).getName());
        System.out.println("Turn order: " + p1.getName() + " " + p2.getName() + " " + p3.getName() + " " + p4.getName());
        printLines(1);
        printWinnerStatement();
        System.out.println("Trump: " + trump);
        printLines(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printLines(3);
    }


    /**
     * prints out a statement regarding the winner of the bid
     */
    public void printWinnerStatement() {
        System.out.println("Team " + bidWinner.getTeam().toString() + " (" + bidWinner.toString() + ") won the bid for " + bid);
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
