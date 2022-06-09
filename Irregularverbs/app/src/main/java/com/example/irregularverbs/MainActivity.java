package com.example.irregularverbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public static Verb[] verbs;
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
        try {
            verbs = new ObjectMapper().readValue(readFromFile(), Verb[].class);
            for(int i = 0; i < verbs.length; i++){
                Arrays.sort(verbs[i].translations);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private String readFromFile() {
        StringBuilder buf = new StringBuilder();
        InputStream json = null;
        String str;
        try {
            json = getAssets().open("db.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "Windows-1251"));

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}