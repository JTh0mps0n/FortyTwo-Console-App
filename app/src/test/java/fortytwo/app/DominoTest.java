package fortytwo.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import fortytwo.Model.*;
import fortytwo.Controller.*;
import fortytwo.View.*;

class DominoTest {

    @Test
    void testEqualsDoubleZero() {
        // arrange
        Domino d1 = new Domino(0,0);
        Domino d2 = new Domino(0,0);

        // act
        boolean areEqual = d1.equals(d2);

        // assert
        assertTrue(areEqual);
    }

    @Test
    void testEqualsBlankAce() {
        // arrange
        Domino d1 = new Domino(0,1);
        Domino d2 = new Domino(1,0);

        // act
        boolean areEqual = d1.equals(d2);

        // assert
        assertTrue(areEqual);
    }

    @Test
    void testNotEqualsBlankAceDoubleBlank() {
        // arrange
        Domino d1 = new Domino(0,0);
        Domino d2 = new Domino(1,0);

        // act
        boolean notEqual = d1.equals(d2);

        // assert
        assertFalse(notEqual);
    }

    @Test
    void testToString() {
        // arrange
        Domino d1 = new Domino(0,0);
        Domino d2 = new Domino(0, 1);

        // act
        String toStr1 = d1.toString();
        String toStr2 = d2.toString();

        // assert
        assertTrue(toStr1.equals("0:0"));
        assertTrue(toStr2.equals("1:0"));
    }

    @Test
    void testIsBeatBy(){
        // arrange
        Domino doubleSix = new Domino (6, 6);
        Domino sixFour = new Domino(6, 4);
        Domino doubleFour = new Domino(4, 4);
        Domino aceDuece = new Domino(1, 2);
        Domino threeAce = new Domino(3, 1);
        Domino doubleTwo = new Domino(2, 2);
        Domino aceBlank = new Domino(1, 0);
        // act
        // assert
        assertFalse(doubleSix.isBeatBy(sixFour, 1, 6));
        assertTrue(doubleSix.isBeatBy(sixFour, 1, 4));
        assertFalse(doubleFour.isBeatBy(sixFour, 1, 4));
        assertTrue(doubleSix.isBeatBy(aceDuece, 2, 6));
        assertTrue(doubleFour.isBeatBy(threeAce, 1, 4));
        assertTrue(doubleFour.isBeatBy(doubleTwo, 1, 2));

    }
}