package fortytwo.app;

import java.util.*;

public class Round {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player[] players;
    private int trump;
    private int bidWinners; //1 or 2 for team 1 or team 2
    private int bid;
    private int t1Points;
    private int t2Points;
    private Hashtable<Domino, Integer> allDominoesTable; //integer represents what player has the domino (0 = played)
    private ArrayList<Domino> allDominoes;
    private int currentPlayerNum;
    private Player currentPlayer;

    public Round(Player p1, Player p2, Player p3, Player p4){
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

    public String playRound(){
        generateHands();
        doBidding();


        return "";
    }

    public void generateHands(){
        Collections.shuffle(allDominoes);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        Hand hand4 = new Hand();
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

    public void doBidding(){
        Scanner input = new Scanner(System.in);
        int[] bids = {-1, -1, -1, -1};
        int maxBid = 29;
        Player winner = p1;

        for (currentPlayerNum = 1; currentPlayerNum <= 4; currentPlayerNum++) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            currentPlayer = players[currentPlayerNum - 1];


            System.out.println("Player " + currentPlayerNum + ", " + currentPlayer.getName() + "'s hand: ");
            printHand(currentPlayer);
            System.out.println();
            printBids(bids);
            System.out.println();


            boolean acceptableInput = false;
            int currentBid = 0;
            while(!acceptableInput){
                System.out.println("Minimum Bid: " + (maxBid + 1));
                System.out.println("What would you like to bid? (0 to pass)");
                System.out.print("ENTER BID >> ");
                if (!input.hasNextInt()){
                    System.out.println("MUST ENTER AN INTEGER\n\n");
                    input.next();
                    continue;
                }
                currentBid = input.nextInt();
                if(currentBid == 0){
                    acceptableInput = true;
                    continue;
                }
                if(currentBid <= maxBid){
                    System.out.println("MUST BE GREATER THAN MINIMUM BID (" + (maxBid + 1) + ")\n\n");
                    continue;
                }
                if(currentBid > 42){
                    System.out.println("CANNOT BE GREATER THAN 42\n\n");
                    continue;
                }
                acceptableInput = true;
            }

            bids[currentPlayerNum-1] = currentBid;

            if(currentBid > maxBid){
                maxBid = currentBid;
                winner = players[currentPlayerNum-1];
            }


        }

        System.out.println();
        System.out.println();
        System.out.println(winner.getName() + " won the bid for " + maxBid + "!");

    }
    private void printBids(int[] bids){
        System.out.println("BIDS:");
        for (int i = 0; i < bids.length; i++) {
            if(bids[i] == -1){
                System.out.println("Player " + (i+1) + ", " + players[i].getName() + "'s bid: YOUR TURN TO BID");
                break;
            }
            if(bids[i] == 0){
                System.out.println("Player " + (i+1) + ", " + players[i].getName() + "'s bid: passed");
            }
            else {
                System.out.println("Player " + (i + 1) + ", " + players[i].getName() + "'s bid: " + bids[i]);
            }
        }
    }
    private void printHand(Player player){
        System.out.println(player.getHand());
    }
}
