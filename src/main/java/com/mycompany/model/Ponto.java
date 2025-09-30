package model;

public class Ponto {
    private int x;
    private int y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public boolean equals(Ponto ponto){
        return this.getX() == ponto.getX() && this.getY() == ponto.getY();
    }
    
    public String toString(){
        return " x: "+this.x+" | y: "+this.y;
    }
}
