package playingGame;

public class Pair<A, B> {
    private A data1;
    private B data2;

    public Pair(A dat1, B dat2) {
        data1 = dat1;
        data2 = dat2;
    }

    public A getData1() {
        return data1;
    }

    public B getData2() {
        return data2;
    }
}
