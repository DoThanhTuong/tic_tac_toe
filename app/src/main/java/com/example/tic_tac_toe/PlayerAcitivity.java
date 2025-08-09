package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tic_tac_toe.databinding.ActivityPlayerBinding;

import java.util.Arrays;

public class PlayerAcitivity extends AppCompatActivity {

    ActivityPlayerBinding viewBinding;

    final int M = 3, N = 3;
    char[][] board;

    Human_Player player1, player2;
    AI_Player aI_Player;
    Player currentPlayer, currentPlayerAI0rHuman;

    ImageButton[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        // Khởi tạo người chơi
        player1 = new Human_Player("Tuong", 'x');

        player2 = new Human_Player("Khang", 'o');
        aI_Player = new AI_Player();

        int mode = getIntent().getIntExtra("mode", 0);
        setUpMode(mode);

        // Khởi tạo board và nút
        board = createBoard();
        setupButtonGrid();

        currentPlayer = player1;
        updateCurrentPlayerUI();

        setListeners();
        onClickPausedGame();
        onClickExit();

    }
    private void setUpMode(int mode) {
        viewBinding.tvPlayer1.setText(player1.name);
        if (mode == 1) {
            currentPlayerAI0rHuman = aI_Player; // AI sẽ chơi với người
            viewBinding.tvPlayer2.setText("AI Player");
        } else if (mode == 2) {
            currentPlayerAI0rHuman = player2;
            viewBinding.tvPlayer2.setText(player2.name);
        } else {
            Toast.makeText(this, "Chế độ không hợp lệ!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupButtonGrid() {
        buttons = new ImageButton[][]{
                {viewBinding.btn1, viewBinding.btn2, viewBinding.btn3},
                {viewBinding.btn4, viewBinding.btn5, viewBinding.btn6},
                {viewBinding.btn7, viewBinding.btn8, viewBinding.btn9}
        };
    }

    private void setListeners() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int finalI = i, finalJ = j;
                buttons[i][j].setOnClickListener(v -> handleClick(finalI, finalJ, buttons[finalI][finalJ]));
            }
        }
    }

    private void onClickExit() {
        viewBinding.btExit.setOnClickListener(v -> {
            startGame();
            setResult(1000, new Intent());
            finish();
        });
    }

    private void onClickPausedGame() {
        viewBinding.btPaused.setOnClickListener(v -> {
            assert viewBinding.pausedActivity != null;
            viewBinding.pausedActivity.setVisibility(View.VISIBLE);
            disableAllButtons();
            viewBinding.pausedActivity.onClickContinue(viewBinding);
        });
    }

    private char[][] createBoard() {
        char[][] newBoard = new char[M][N];
        for (int i = 0; i < M; i++)
            Arrays.fill(newBoard[i], ' ');
        return newBoard;
    }

    private void handleClick(int row, int col, ImageButton btn) {
        if (board[row][col] != ' ') {
            Toast.makeText(this, "Ô này đã được đánh!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đánh dấu nước đi
        board[row][col] = currentPlayer.symbol;
        showSymbol(currentPlayer, btn);
        updateAvatars();

        // Kiểm tra trạng thái game
        boolean win = checkWin(board, currentPlayer.symbol);
        boolean draw = checkDraw(board);

        currentPlayer.setWinner(win);
        currentPlayer.setDraw(draw);

        // Xác định chế độ chơi (vs AI hay vs người)
        boolean vsAI = (currentPlayerAI0rHuman == aI_Player);

        if (win || draw) {
            checkWinLoss(vsAI, win, draw);
            return;
        }


        // Đổi lượt chơi
        currentPlayer = (currentPlayer == player1) ? currentPlayerAI0rHuman : player1;

        // Nếu AI thì cho AI đánh
        if (currentPlayer == aI_Player) {
            playAI();
        }
    }
    private void checkWinLoss(boolean vsAI, boolean win, boolean draw) {
        Player opponent = getOpponent(currentPlayer);

        if (win) {
            if (vsAI) {
                // Người thắng AI
                if (!currentPlayer.equals(aI_Player)) {
                    ((Human_Player) currentPlayer).addScore(3);
                    infoWinLoss(currentPlayer.name + " WIN! Điểm: " + ((Human_Player) currentPlayer).getScore(), true);
                }
                // AI thắng → trừ điểm người chơi
                else if (opponent instanceof Human_Player) {
                    ((Human_Player) opponent).addScore(-1);
                    infoWinLoss(opponent.name + " LOSE! Điểm: " + ((Human_Player) opponent).getScore(), false);
                }
            } else {
                // Người thắng người → thắng +3, thua -1
                ((Human_Player) currentPlayer).addScore(3);
                ((Human_Player) opponent).addScore(-1);
                infoWinLoss(currentPlayer.name + " WIN! Điểm: " + ((Human_Player) currentPlayer).getScore(), true);
            }
            startGameWithDelay();
        }

        if (draw) {
            if (vsAI) {
                // Hòa với AI → chỉ người chơi được +1
                if (!currentPlayer.equals(aI_Player)) {
                    ((Human_Player) currentPlayer).addScore(1);
                    infoWinLoss("Hòa! Điểm: " + ((Human_Player) currentPlayer).getScore(), false);
                } else if (opponent instanceof Human_Player) {
                    ((Human_Player) opponent).addScore(1);
                    infoWinLoss("Hòa! Điểm: " + ((Human_Player) opponent).getScore(), false);
                }
            } else {
                // Hòa người với người → cả hai +1
                ((Human_Player) currentPlayer).addScore(1);
                ((Human_Player) opponent).addScore(1);
                infoWinLoss("Hòa! " + currentPlayer.name + ": " + ((Human_Player) currentPlayer).getScore() +
                        " | " + opponent.name + ": " + ((Human_Player) opponent).getScore(), false);
            }
            startGameWithDelay();
        }
    }


    // Hàm lấy đối thủ
    private Player getOpponent(Player p) {
        return (p == player1) ? currentPlayerAI0rHuman : player1;
    }


    private void playAI() {
        aI_Player.updateBoard(board);
        new Handler().postDelayed(() -> {
            aI_Player.makeAIMove();
            int row = aI_Player.getRow();
            int col = aI_Player.getCol();
            handleClick(row, col, buttons[row][col]);
        }, 500);
    }

    private void startGameWithDelay() {
        disableAllButtons();
        startGame();
        updateCurrentPlayerUI();
    }

    private void startGame() {
        board = createBoard();
        aI_Player.updateBoard(board);
        currentPlayer = player1;

    }


    private void disableAllButtons() {
        for (ImageButton[] row : buttons)
            for (ImageButton btn : row)
                btn.setEnabled(false);
    }

    private void showSymbol(Player player, ImageButton btn) {
        int resId = getResources().getIdentifier(String.valueOf(player.symbol), "drawable", getPackageName());
        if (resId != 0) {
            btn.setImageDrawable(ContextCompat.getDrawable(this, resId));
            btn.setEnabled(false);
            viewBinding.ivPlayer.setImageDrawable(ContextCompat.getDrawable(this, resId));
            viewBinding.tvPlayer.setText(player.name);
        }
    }

    private void updateAvatars() {
        boolean isPlayer1 = currentPlayer == player1;
        viewBinding.ivPlayer1.setAlpha(isPlayer1 ? 0.5f : 1f);
        viewBinding.ivPlayer2.setAlpha(isPlayer1 ? 1f : 0.5f);
        viewBinding.tvPlayer1.setAlpha(isPlayer1 ? 0.5f : 1f);
        viewBinding.tvPlayer2.setAlpha(isPlayer1 ? 1f : 0.5f);
    }

    private void updateCurrentPlayerUI() {
        viewBinding.ivPlayer1.setAlpha(1f);
        viewBinding.tvPlayer1.setAlpha(1f);
        viewBinding.ivPlayer2.setAlpha(0.5f);
        viewBinding.tvPlayer2.setAlpha(0.5f);
        viewBinding.ivPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x));
        viewBinding.tvPlayer.setText(player1.name);
    }

    private boolean checkWin(char[][] b, char s) {
        for (int i = 0; i < M; i++)
            if (b[i][0] == s && b[i][1] == s && b[i][2] == s) return true;
        for (int j = 0; j < N; j++)
            if (b[0][j] == s && b[1][j] == s && b[2][j] == s) return true;
        return (b[0][0] == s && b[1][1] == s && b[2][2] == s) || (b[0][2] == s && b[1][1] == s && b[2][0] == s);
    }

    private boolean checkDraw(char[][] b) {
        for (char[] row : b)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    private void infoWinLoss(String text, boolean isWin) {
        assert viewBinding.winlostActivity != null;
        viewBinding.winlostActivity.setVisibility(View.VISIBLE);
        viewBinding.winlostActivity.setResult(text, isWin);
        viewBinding.winlostActivity.startNewGame(viewBinding);
    }
}
