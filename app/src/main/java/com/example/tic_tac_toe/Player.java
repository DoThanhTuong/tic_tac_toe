package com.example.tic_tac_toe;

public class Player {
    protected String name;
    protected char symbol; // X or O
    int score;
    final int M = 3; // Number of rows
    final int N = 3; // Number of columns
    boolean isTurn; // true if it's this player's turn
    boolean isAI; // true if this player is an AI
    boolean isWinner; // true if this player has won the game
    boolean isDraw; // true if the game is a draw for this player
    boolean isGameOver; // true if the game is over for this player

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
        this.isTurn = false;
        this.isAI = false;
        this.isWinner = false;
        this.isDraw = false;
        this.isGameOver = false;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public boolean isTurn() {
        return isTurn;
    }
    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public boolean isAI() {
        return isAI;
    }
    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }

    public boolean isWinner(boolean b) {

        return isWinner;
    }
    public void setWinner(boolean isWinner) {
        if (isWinner) {
            this.score++;
        }
        this.isWinner = isWinner;
    }

    public boolean isDraw(boolean b) {
        return isDraw;
    }
    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public boolean isGameOver(boolean b) {
        return isGameOver;
    }
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }



}
