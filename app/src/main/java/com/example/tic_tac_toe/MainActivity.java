package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tic_tac_toe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding viewBinding;
    private ActivityResultLauncher<Intent> playerActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        playerActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 1000) {
                        Log.d("tuong", "co ");
                    }
                }
        );

        setupClickListeners();
    }

    private void openPlayerActivity(int mode) {
        Intent intent = new Intent(MainActivity.this, PlayerAcitivity.class);
        intent.putExtra("mode", mode);
        playerActivityLauncher.launch(intent);
    }

    private void setupClickListeners() {
        viewBinding.tvPlayWithAF.setOnClickListener(v -> openPlayerActivity(2));
        viewBinding.tvSingle.setOnClickListener(v -> openPlayerActivity(1));
    }
}