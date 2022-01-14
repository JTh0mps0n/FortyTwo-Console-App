/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package fortytwo.app;


import java.util.ArrayList;
import fortytwo.model.*;
import fortytwo.controller.*;
import fortytwo.view.*;


public class App {
    public static void main(String[] args) {

        Player p1 = new Player("Justin");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Greg");
        Player p4 = new Player("Aimee");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        GameController.playRound(players);
    }
}
