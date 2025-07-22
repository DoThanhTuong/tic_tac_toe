package com.example.tic_tac_toe;

import android.widget.ImageButton;

import java.util.ArrayList;

public class AI_Player extends Player {

    int row, col;

    private final int M = 3;
    private final int N = 3;
    private  char AI = 'o';
    private final char HUMAN = 'x';

    private char[][] board;

    public AI_Player() {
        super("AI Player", 'o');
    }

    public void updateBoard(char[][] newBoard) {
        this.board = newBoard;
    }

    public static class Move {
        int row, col;
        public Move(int r, int c) {
            row = r;
            col = c;
        }
    }

    private boolean checkWin(char[][] b, char player) {
        for (int i = 0; i < M; i++) {
            if (b[i][0] == player && b[i][1] == player && b[i][2] == player) return true;
        }
        for (int j = 0; j < N; j++) {
            if (b[0][j] == player && b[1][j] == player && b[2][j] == player) return true;
        }
        return (b[0][0] == player && b[1][1] == player && b[2][2] == player) ||
                (b[0][2] == player && b[1][1] == player && b[2][0] == player);
    }

    private boolean checkDraw(char[][] b) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (b[i][j] == ' ') return false;
        return true;
    }

    private ArrayList<Move> getEmptyCells(char[][] b) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (b[i][j] == ' ') {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    private int minimax(char[][] b, int depth, boolean isMaximizing, int alpha, int beta) {
        if (checkWin(b, AI)) return 10 - depth;
        if (checkWin(b, HUMAN)) return depth - 10;
        if (checkDraw(b)) return 0;

        ArrayList<Move> emptyCells = getEmptyCells(b);

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Move move : emptyCells) {
                b[move.row][move.col] = AI;
                int score = minimax(b, depth + 1, false, alpha, beta);
                b[move.row][move.col] = ' ';
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) break;
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move move : emptyCells) {
                b[move.row][move.col] = HUMAN;
                int score = minimax(b, depth + 1, true, alpha, beta);
                b[move.row][move.col] = ' ';
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) break;
            }
            return bestScore;
        }
    }

    public Move getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        ArrayList<Move> emptyCells = getEmptyCells(board);

        for (Move move : emptyCells) {
            board[move.row][move.col] = AI;
            int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board[move.row][move.col] = ' ';

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public void makeAIMove() {
        Move move = getBestMove();
        if (move != null) {
            row = move.row;
            col = move.col;
        }

    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

}
