package fortytwo.app;

import java.util.Hashtable;

public class Domino {
    private final int pip1;
    private final int pip2;
    private Player owner;
    private final boolean count;
    private final int points;
    private Hashtable<Integer, String> emojis;
    private boolean useEmojis = false;

    public Domino(int pip1, int pip2) {
        //pip1 should always be greater.
        if (pip2 > pip1) {
            this.pip1 = pip2;
            this.pip2 = pip1;
        } else {
            this.pip1 = pip1;
            this.pip2 = pip2;
        }

        if ((pip1 + pip2) % 5 == 0) {
            count = true;
            points = pip1 + pip2;
        } else {
            count = false;
            points = 0;
        }

        emojis = new Hashtable<Integer, String>();
        emojis.put(0, "□");
        emojis.put(1, "⚀");
        emojis.put(2, "⚁");
        emojis.put(3, "⚂");
        emojis.put(4, "⚃");
        emojis.put(5, "⚄");
        emojis.put(6, "⚅");
    }

    @Override
    public String toString() {
        String pip1str;
        String pip2str;
        String separator;
        if (useEmojis) {
            pip1str = emojis.get(pip1);
            pip2str = emojis.get(pip2);
            separator = "";
        } else {
            pip1str = "" + pip1;
            pip2str = "" + pip2;
            separator = ":";
        }

        if (pip1 > pip2) {
            return pip1str + separator + pip2str;
        } else {
            return pip2str + separator + pip1str;
        }
    }

    @Override
    public boolean equals(Object rhsObject) {
        Domino rhs = (Domino) rhsObject;
        return rhs.toString().equals(toString());
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Returns whether the domino is of the given suit
     *
     * @param suit the suit to be checked
     * @return boolean value indicating if the domino is of the given suit
     */
    public boolean isSuit(int suit) {
        return pip1 == suit || pip2 == suit;
    }

    /**
     * Returns highest side of the domino
     *
     * @return highest side
     */
    public int highestPip() {
        if (pip1 > pip2) {
            return pip1;
        }
        return pip2;
    }

    /**
     * Returns if the domino is a double
     *
     * @return boolean value indicating if the domino is a double
     */
    public boolean isDouble() {
        return pip1 == pip2;
    }

    /**
     * Returns the other side of the domino given a suit
     *
     * @param suit the suit with which you don't want
     * @return the other side of the domino. Returns -1 if neither side is suit. Returns -2 if it's a double
     */
    private int otherSide(int suit) {
        if (!isSuit(suit)) {
            return -1;
        }
        if (isDouble()) {
            return -2;
        }
        if (pip1 == suit) {
            return pip2;
        }
        return pip1;

    }

    /**
     * Returns if this domino is beat by the given domino
     *
     * @param domino the domino to be checked against
     * @param led    the led suit in the trick
     * @param trump  the trump in the round
     * @return boolean value indicating if this domino is beat by the given domino
     */
    public boolean isBeatBy(Domino domino, int led, int trump) {
        if (!isSuit(trump) && !isSuit(led)) {//if this domino is 'junk'
            return true;
        }
        if (!domino.isSuit(trump) && !domino.isSuit(led)) {//if the given domino is 'junk'
            return false;
        }
        if (isSuit(trump) && !domino.isSuit(trump)) {//if the current domino is a trump and the given one isn't
            return false;
        }
        if (!isSuit(trump) && domino.isSuit(trump)) {//if current domino isn't a trump and the given one is
            return true;
        }
        if (isSuit(trump) && domino.isSuit(trump)) {//if both are trumps
            if (isDouble()) {//if this domino is a double
                return false;
            }
            if (domino.isDouble()) {//if the given domino is a double
                return true;
            }
            if (domino.otherSide(trump) > otherSide(trump)) {//if the given domino is a higher trump than this domino
                return true;
            } else {//if this domino is a higher trump than the given domino
                return false;
            }
        }
        //neither are trumps
        if (!isSuit(led)) {//if this domino is not of the led suit
            return true;
        }
        if (!domino.isSuit(led)) {//if the given domino is not of the led suit
            return false;
        }
        //both dominoes are of led suit
        if (isDouble()) {//if this domino is a double
            return false;
        }
        if (domino.isDouble()){//if given domino is a double
            return true;
        }
        //both dominos are of led suit and neither is double
        return domino.otherSide(led) > otherSide(led); //true if the given domino is higher value than this domino
    }
}
