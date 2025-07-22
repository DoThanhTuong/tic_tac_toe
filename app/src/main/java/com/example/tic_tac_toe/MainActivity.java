package com.example.tic_tac_toe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tic_tac_toe.databinding.ActivityMainBinding;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());



        onClickMode(mode -> {
            if (mode == 1) {
                onClickWithFen(mode);
            } else if (mode == 2) {
                onClickWithFen(mode);
            }
        });





    }
    public void onClickMode(Consumer<Integer> callback) {
        viewBinding.btMode.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Mode")
                    .setMessage("Chọn chế độ chơi:")
                    .setPositiveButton("chơi với máy", (dialog, which) -> callback.accept(1))
                    .setNegativeButton("chơi với người", (dialog, which) -> callback.accept(2))
                    .setNeutralButton("Hủy", null)
                    .show();
        });
    }
    public void onClickWithFen (Integer mode) {
        viewBinding.tvPlayWithAF.setOnClickListener(v-> {;
            Intent intentMain = new Intent(MainActivity.this, PlayerAcitivity.class);
            intentMain.putExtra("mode", mode);
            startActivityForResult(intentMain, 123);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == 1000) {
            Log.d("tuong", "co ");
        }
    }


}