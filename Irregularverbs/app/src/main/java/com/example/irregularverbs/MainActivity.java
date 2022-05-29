package com.example.irregularverbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnTest = findViewById(R.id.testbtn);
        btnTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BeforeTestActivity.class);
                startActivity(i);
            }
        });
        Button btnDict = findViewById(R.id.dictbtn);
        btnDict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
        });
    }
}