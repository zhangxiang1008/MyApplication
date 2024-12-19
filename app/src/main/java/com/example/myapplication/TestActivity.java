package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.myapplication.component.PieChart.PieChart;
import com.example.myapplication.view.MyDialog;

class TestActivity extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog dialog = new AlertDialog.Builder(TestActivity.this).create();
                MyDialog myDialog = new MyDialog(TestActivity.this);
                myDialog.setCallback(new MyDialog.onDismissCallback() {
                    @Override
                    public void dismiss() {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setView(myDialog);
                dialog.show();
            }
        });
        Button button2 = findViewById(R.id.btnSecond);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TestActivity.this, CargoInfoActivity.class);
//                    Intent intent = new Intent("android.intent.action.VIEW",Uri.parse("tel:123"));
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "zx");
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            });

        }

        Button button3 = findViewById(R.id.btnThird);
        if (button3 != null) {
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialog dialog = new AlertDialog.Builder(TestActivity.this).create();
//                    PieChart pieChart = new PieChart(TestActivity.this);
//                    dialog.setView(pieChart);
//                    dialog.show();
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "zx");
                    intent.setType("text/plain");
                    String title = getResources().getString(R.string.title);
                    Intent chooser = Intent.createChooser(intent,title);
                    try {
                        startActivity(chooser);
                    } catch (ActivityNotFoundException e) {
//
                    }
                }
            });

        }
    }
}
