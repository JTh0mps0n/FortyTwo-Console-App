package fortytwo.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void testToString() {
        // arrange
        Domino d1 = new Domino(0,0);
        Domino d2 = new Domino(1,0);
        Domino d3 = new Domino(2,0);
        Domino d4 = new Domino(3,0);
        Domino d5 = new Domino(4,0);
        Domino d6 = new Domino(5,0);
        Domino d7 = new Domino(6,0);
        Hand hand = new Hand();
        hand.addDomino(d1);
        hand.addDomino(d2);
        hand.addDomino(d3);
        hand.addDomino(d4);
        hand.addDomino(d5);
        hand.addDomino(d6);
        hand.addDomino(d7);

        // act
        String handStr = hand.toString();
        String correct = "0:0 1:0 2:0 3:0 4:0 5:0 6:0";
        boolean isSame = handStr.equals(correct);

        // assert
        assertEquals(correct, handStr);
    }

}