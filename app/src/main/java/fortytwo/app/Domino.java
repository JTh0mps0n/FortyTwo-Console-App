package fortytwo.app;

public class Domino {
    private final int pip1;
    private final int pip2;
    private int owner;

    public Domino(int pip1, int pip2) {
        owner = 0;
        //pip1 should always be greater.
        if(pip2 > pip1){
            this.pip1 = pip2;
            this.pip2 = pip1;
            return;
        }
        this.pip1 = pip1;
        this.pip2 = pip2;
    }

    @Override
    public String toString() {
        if (pip1 > pip2){
            return pip1 + ":" + pip2;
        }
        else {
            return pip2 + ":" + pip1;
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
