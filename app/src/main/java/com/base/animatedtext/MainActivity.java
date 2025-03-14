package com.base.animatedtext;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.base.risingtext.AnimationListener;
import com.base.risingtext.HTextView;
import com.base.risingtext.RisingTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RisingTextView textView = findViewById(R.id.rsview);

        findViewById(R.id.loadText).setOnClickListener(v -> {
            textView.animateText("20",false);
        });

        findViewById(R.id.changeText).setOnClickListener(v -> {
            textView.animateText((String.valueOf(Integer.parseInt(textView.getText().toString())*2)),true);
        });

    }
}