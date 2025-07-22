package com.example.tic_tac_toe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tic_tac_toe.databinding.ActivityPlayerBinding;

import java.util.Arrays;


public class PlayerAcitivity extends AppCompatActivity {

    ActivityPlayerBinding viewBinding;
    final int M = 3;
    final int N = 3;


    AI_Player aI_Player;
    Player currentPlayer;

    char[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        Player player1;
        Player player2;

        board = boardCreat(); // Khởi tạo bảng

        // Khởi tạo người chơi và AI
        player1 = new Player("player 1", 'x');
        player2 = new Player("player 2", 'o');
        aI_Player = new AI_Player(); // AI dùng 'o'


        viewBinding.ivPlayer1.setAlpha(1f);
        viewBinding.tvPlayer1.setAlpha(1f);

        currentPlayer = player1;

        setListeners(player1,player2);
        onClickpausedGame();
        onClickExit(player1);

    }
    private void onClickExit(Player player1) {
        viewBinding.btExit.setOnClickListener(v -> {
            assert viewBinding.winlostActivity != null;
            startGame(player1);
            Log.d("tuong", Arrays.toString(board));
            Log.d("tuong", String.valueOf(player1.score));
            Intent intent = new Intent();
            setResult(1000, intent);
            finish();
        });
    }
    private void onClickpausedGame() {
        viewBinding.btPaused.setOnClickListener(v -> {
            assert viewBinding.pausedActivity != null;
            viewBinding.pausedActivity.setVisibility(View.VISIBLE);
            disableAllButtons();
            viewBinding.pausedActivity.onClickContinue(viewBinding);
        });

    }

    private char[][] boardCreat() {
        char[][] newBoard = new char[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                newBoard[i][j] = ' ';
        return newBoard;
    }

    private void setListeners(Player player1, Player player2) {
        viewBinding.btn1.setOnClickListener(v -> handleClick(0, 0, viewBinding.btn1,player1,player2));
        viewBinding.btn2.setOnClickListener(v -> handleClick(0, 1, viewBinding.btn2, player1, player2));
        viewBinding.btn3.setOnClickListener(v -> handleClick(0, 2, viewBinding.btn3, player1, player2));
        viewBinding.btn4.setOnClickListener(v -> handleClick(1, 0, viewBinding.btn4, player1, player2));
        viewBinding.btn5.setOnClickListener(v -> handleClick(1, 1, viewBinding.btn5, player1, player2));
        viewBinding.btn6.setOnClickListener(v -> handleClick(1, 2, viewBinding.btn6, player1, player2));
        viewBinding.btn7.setOnClickListener(v -> handleClick(2, 0, viewBinding.btn7, player1, player2));
        viewBinding.btn8.setOnClickListener(v -> handleClick(2, 1, viewBinding.btn8, player1, player2));
        viewBinding.btn9.setOnClickListener(v -> handleClick(2, 2, viewBinding.btn9, player1, player2));
    }

    private void handleClick(int row, int col, ImageButton btn, Player player1, Player player2) {
        if (board[row][col] != ' ') {
            Toast.makeText(this, "Ô này đã được đánh!", Toast.LENGTH_SHORT).show();
            return;
        }

        board[row][col] = currentPlayer.symbol;

        show_XO(currentPlayer, btn);
        showHideAvt(currentPlayer,player1);


        if (checkWin(board, currentPlayer.symbol)) {
            currentPlayer.setWinner(true);
            if(currentPlayer == aI_Player) {
                info_winlost(" DEFEAT! ", false);
            } else {
                info_winlost(currentPlayer.name + " WIN! " + currentPlayer.score, true);
            }
            startGameWithDelay(player1);
            return;
        } else if (checkDraw(board)) {
            currentPlayer.setDraw(true);
            info_winlost("Hòa!", true);
            startGameWithDelay(player1);
            return;
        }

        // Chuyển lượt cho người chơi khác
        currentPlayer = (currentPlayer == player1) ? aI_Player : player1;

        if (currentPlayer == aI_Player) {
            play_AI(player1, player2);
        }
    }
    private void startGameWithDelay(Player player1) {
        disableAllButtons();
        startGame(player1);
        showaAvt1_hideAvt2();
    }

    private void play_AI(Player player1, Player player2) {
        // AI sẽ tự động đánh sau khi người chơi đánh
        aI_Player.updateBoard(board); // Cập nhật bảng cho AI
        new Handler().postDelayed(() -> {
            aI_Player.makeAIMove();
            int aiRow = aI_Player.getRow();
            int aiCol = aI_Player.getCol();
            ImageButton aiBtn = getButtonAt(aiRow, aiCol);
            if (aiBtn != null) handleClick(aiRow, aiCol, aiBtn, player1, player2);
        }, 500);
    }


    // giao diện hiển thị
    private void show_XO(Player player, ImageButton btn) {
        @SuppressLint("DiscouragedApi")
        int resId = getResources().getIdentifier(String.valueOf(player.symbol), "drawable", getPackageName());

        if (resId != 0) {
            btn.setImageDrawable(ContextCompat.getDrawable(this, resId));
            btn.setEnabled(false);
            assert viewBinding.ivPlayer != null;
            viewBinding.ivPlayer.setImageDrawable(ContextCompat.getDrawable(this, resId));
            viewBinding.tvPlayer.setText(player.name);
        }

    }
    private void showHideAvt(Player player, Player player1) {
        if (player == player1) {
            showaAvt2_hideAvt1();
        } else {
            showaAvt1_hideAvt2();
        }
    }
    private void showaAvt1_hideAvt2(){
        viewBinding.ivPlayer1.setAlpha(1f);
        viewBinding.ivPlayer2.setAlpha(0.5f);
        viewBinding.tvPlayer1.setAlpha(1f);
        viewBinding.tvPlayer2.setAlpha(0.5f);
    }
    private void showaAvt2_hideAvt1(){
        viewBinding.ivPlayer1.setAlpha(0.5f);
        viewBinding.ivPlayer2.setAlpha(1f);
        viewBinding.tvPlayer1.setAlpha(0.5f);
        viewBinding.tvPlayer2.setAlpha(1f);
    }

    // Kiểm tra thắng thua và hòa
    private boolean checkWin(char[][] b, char symbol) {
        for (int i = 0; i < M; i++)
            if (b[i][0] == symbol && b[i][1] == symbol && b[i][2] == symbol) return true;

        for (int j = 0; j < N; j++)
            if (b[0][j] == symbol && b[1][j] == symbol && b[2][j] == symbol) return true;

        return (b[0][0] == symbol && b[1][1] == symbol && b[2][2] == symbol) ||
                (b[0][2] == symbol && b[1][1] == symbol && b[2][0] == symbol);
    }
    private boolean checkDraw(char[][] b) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (b[i][j] == ' ') return false;
        return true;
    }

    // Hiển thị thông tin thắng thua
    private void info_winlost(String resultText, boolean isWin) {
        assert viewBinding.winlostActivity != null;
        viewBinding.winlostActivity.setVisibility(View.VISIBLE);
        viewBinding.winlostActivity.setResult(resultText, isWin);
        viewBinding.winlostActivity.startNewGame(viewBinding);
    }
    private void startGame(Player player1) {
        board = boardCreat();
        aI_Player.updateBoard(board);
        assert viewBinding.ivPlayer != null;
        viewBinding.ivPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.x));
        viewBinding.tvPlayer.setText(player1.name);
        player1.setWinner(false);
        aI_Player.setWinner(false);
        player1.setDraw(false);
        aI_Player.setDraw(false);
        currentPlayer = player1;

    }

    // Vô hiệu hóa tất cả các nút
    private void disableAllButtons() {
        viewBinding.btn1.setEnabled(false);
        viewBinding.btn2.setEnabled(false);
        viewBinding.btn3.setEnabled(false);
        viewBinding.btn4.setEnabled(false);
        viewBinding.btn5.setEnabled(false);
        viewBinding.btn6.setEnabled(false);
        viewBinding.btn7.setEnabled(false);
        viewBinding.btn8.setEnabled(false);
        viewBinding.btn9.setEnabled(false);
    }
    // Lấy nút tương ứng với hàng và cột cho AI
    private ImageButton getButtonAt(int row, int col) {
        if (row == 0 && col == 0) return viewBinding.btn1;
        if (row == 0 && col == 1) return viewBinding.btn2;
        if (row == 0 && col == 2) return viewBinding.btn3;
        if (row == 1 && col == 0) return viewBinding.btn4;
        if (row == 1 && col == 1) return viewBinding.btn5;
        if (row == 1 && col == 2) return viewBinding.btn6;
        if (row == 2 && col == 0) return viewBinding.btn7;
        if (row == 2 && col == 1) return viewBinding.btn8;
        if (row == 2 && col == 2) return viewBinding.btn9;
        return null;
    }
}