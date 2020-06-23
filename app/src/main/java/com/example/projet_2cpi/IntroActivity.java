package com.example.projet_2cpi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {
    private static int TIME = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();

            }
        },TIME);
    }
    /*******************************FOR SEARCH*********************************/

}
