package model;

public class Tabuleiro {
    private int[][] tabuleiro = {
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1}
    };
    
    private int countPecasEmJogo;

    public Tabuleiro() {
        this.countPecasEmJogo = 32;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }
    public void setPosition(int x, int y, int value){
        tabuleiro[x][y] = value;
    }
    public int getPosition(int x, int y){
        return tabuleiro[x][y];
    }
    public int getCountPecasEmJogo(){
        return this.countPecasEmJogo;
    }
    
    public void decrementaPecasEmJogo(){
        this.countPecasEmJogo--;
    }
}
