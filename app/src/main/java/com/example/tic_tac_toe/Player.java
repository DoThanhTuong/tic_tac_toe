package com.example.tic_tac_toe;

public class Player {
    protected String name;
    protected char symbol; // X or O

    protected final int M = 3; // Number of rows
    protected final int N = 3; // Number of columns

    protected boolean isWinner;
    protected boolean isDraw;

    protected boolean isLose;


    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.isWinner = false;
        this.isDraw = false;
        this.isLose = false;

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

    public boolean checkWin(char[][] b, char player) {
        for (int i = 0; i < M; i++) {
            if (b[i][0] == player && b[i][1] == player && b[i][2] == player) return true;
        }
        for (int j = 0; j < N; j++) {
            if (b[0][j] == player && b[1][j] == player && b[2][j] == player) return true;
        }
        return (b[0][0] == player && b[1][1] == player && b[2][2] == player) ||
                (b[0][2] == player && b[1][1] == player && b[2][0] == player);
    }

    public boolean checkDraw(char[][] b) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (b[i][j] == ' ') return false;
        return true;
    }

    public void setWinner (boolean winner) {
        isWinner = winner;
    }
    public boolean isWinner() {
        return isWinner;
    }
    public void setDraw (boolean draw) {
        isDraw = draw;
    }
    public boolean isDraw() {
        return isDraw;
    }
    public void setLose (boolean lose) {
        isLose = lose;
    }
    public boolean isLose() {
        return isLose;
    }





}
