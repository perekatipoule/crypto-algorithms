package crypto.keysearching.multilevel;

public class Pair implements Comparable<Pair>{
    private int x0;
    private int x1;
    public Pair(int a0, int a1) {
        this.x0 = a0;
        this.x1 = a1;
    }
    public int getX1() {
        return x0;
    }
    public int getX2() {
        return x1;
    }
    public void setX1(int x1) {
        this.x0 = x1;
    }
    public void setX2(int x2) {
        this.x1 = x2;
    }
    @Override
    public int compareTo(Pair o) {
        return this.x0 - o.x0;
    }
    @Override
    public String toString() {
        return x0 + " " + x1;
    }

}
