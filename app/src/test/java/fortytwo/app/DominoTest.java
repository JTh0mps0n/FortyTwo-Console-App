package fortytwo.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}