package fortytwo.app;

import java.util.Hashtable;

public class Domino {
    private final int pip1;
    private final int pip2;
    private int owner;
    private final boolean count;
    private final int points;
    private Hashtable<Integer, String> emojis;
    private boolean useEmojis = false;

    public Domino(int pip1, int pip2) {
        owner = 0;
        //pip1 should always be greater.
        if(pip2 > pip1){
            this.pip1 = pip2;
            this.pip2 = pip1;
        }
        else {
            this.pip1 = pip1;
            this.pip2 = pip2;
        }

        if((pip1 + pip2)%5 == 0){
            count = true;
            points = pip1 + pip2;
        }
        else{
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
        if(useEmojis){
            pip1str = emojis.get(pip1);
            pip2str = emojis.get(pip2);
            separator = "";
        }
        else{
            pip1str = "" + pip1;
            pip2str = "" + pip2;
            separator = ":";
        }

        if (pip1 > pip2){
            return pip1str + separator + pip2str;
        }
        else {
            return pip2str + separator + pip1str;
        }
    }

    @Override
    public boolean equals(Object rhsObject) {
        Domino rhs = (Domino) rhsObject;
        return rhs.toString().equals(toString());
    }

    public void setOwner(int playerNumber){
        owner = playerNumber;
    }

    public int getPoints() {
        return points;
    }
}
