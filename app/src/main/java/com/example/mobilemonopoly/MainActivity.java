package com.example.mobilemonopoly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnToOldMonopoly = findViewById(R.id.oldMonopoly);
        btnToOldMonopoly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Monopoly.class);
                startActivity(intent);
            }
        });
        Button btnToShenKeng = findViewById(R.id.shenkengMonopoly);
        btnToShenKeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShenkengMonopoly.class);
                startActivity(intent);
            }
        });


    }

}