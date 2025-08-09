package com.example.tic_tac_toe;

public class Human_Player extends Player{
    private int score;


    public Human_Player(String name, char symbol) {
        super(name, symbol);
        this.score = 0;

    }

    public void addScore(int value) {
        this.score += value;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return super.hashCode();

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Human_Player)) return false;
        Human_Player other = (Human_Player) obj;
        return this.getName().equals(other.getName()) && this.getSymbol() == other.getSymbol();
    }

}
