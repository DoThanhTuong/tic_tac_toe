package com.example.tic_tac_toe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.tic_tac_toe.databinding.ActivityPlayerBinding;

public class PausedAcitivity extends LinearLayout {
    public PausedAcitivity(Context context) {
        super(context);
        init();
    }

    public PausedAcitivity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PausedAcitivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PausedAcitivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {

        inflate(getContext(), R.layout.activity_paused, this);
    }
    void onClickContinue(ActivityPlayerBinding viewBinding) {
        ImageButton btnCon = findViewById(R.id.bt_stop);
        btnCon.setOnClickListener(v -> {
            // Reset UI
            ImageButton[] buttons = {
                    viewBinding.btn1, viewBinding.btn2, viewBinding.btn3,
                    viewBinding.btn4, viewBinding.btn5, viewBinding.btn6,
                    viewBinding.btn7, viewBinding.btn8, viewBinding.btn9
            };

            for (ImageButton btn : buttons) {
                if(btn.getDrawable() == null) {
                    btn.setEnabled(true);
                }
            }
            setVisibility(View.GONE);

        });

    }

}
