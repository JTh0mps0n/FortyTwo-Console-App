package fortytwo.app;

import java.util.Hashtable;

public class Domino {
    private final int pip1;
    private final int pip2;
    private int owner;
    private Hashtable<Integer, String> emojis;

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
        String pip1str = emojis.get(pip1);
        String pip2str = emojis.get(pip2);

        if (pip1 > pip2){
            return pip1str + "" + pip2str;
        }
        else {
            return pip2str + "" + pip1str;
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
}
