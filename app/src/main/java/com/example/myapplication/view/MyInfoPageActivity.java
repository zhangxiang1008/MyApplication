package com.example.myapplication.view;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.myapplication.R;

public class MyInfoPageActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("🚀------MyInfoPageActivity");
        setContentView(R.layout.my_info_card_view);
        CardView cardView = findViewById(R.id.my_card);
        cardView.setRadius(16);
    }

}
