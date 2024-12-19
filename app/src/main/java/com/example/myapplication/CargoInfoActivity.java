package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recycle_list.CustomAdapter;
import com.example.myapplication.view.MyInfoPageActivity;

public class CargoInfoActivity extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_info);
        Button button = (Button) findViewById(R.id.button_click);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CargoInfoActivity.this, MyInfoPageActivity.class);
                    startActivity(intent);
                }
            });
        }
        // 列表
        String[] listData = new String[2];
        listData[0] = "13123";
        listData[1] = "77777";
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new CustomAdapter(listData));
    }
}
