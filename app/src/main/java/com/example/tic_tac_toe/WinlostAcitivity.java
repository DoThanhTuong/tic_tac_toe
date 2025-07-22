package com.example.tic_tac_toe;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tic_tac_toe.databinding.ActivityPlayerBinding;

import java.util.ArrayList;

public class WinlostAcitivity extends LinearLayout {
//    tạo một layout để hiển thị kết quả thắng thua của người chơi

    public WinlostAcitivity(Context context) {
        super(context);
        init();
    }

    public WinlostAcitivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WinlostAcitivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        inflate(getContext(), R.layout.activity_winlost, this);
    }

    public void setResult(String resultText, boolean isWin) {
        TextView tv = findViewById(R.id.tv_winLost);
        tv.setText(resultText);
        tv.setTextColor(isWin ? Color.BLACK : Color.RED);
    }
    public void startNewGame(ActivityPlayerBinding viewBinding) {
        ImageButton btnReset = findViewById(R.id.bt_replay);
        btnReset.setOnClickListener(v -> {

            // Reset UI
           deleteAllButtons(viewBinding);
            setVisibility(View.GONE);

        });
    }
    public void deleteAllButtons(ActivityPlayerBinding viewBinding) {
        ImageButton[] buttons = {
                viewBinding.btn1, viewBinding.btn2, viewBinding.btn3,
                viewBinding.btn4, viewBinding.btn5, viewBinding.btn6,
                viewBinding.btn7, viewBinding.btn8, viewBinding.btn9
        };

        for (ImageButton btn : buttons) {
            btn.setImageDrawable(null);
            btn.setEnabled(true);
        }
    }

}
