package com.example.irregularverbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BeforeTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_test);
        EditText enter = findViewById(R.id.howmanyverbs);
        TextView disclaimer_2 = findViewById(R.id.textView2);
        Button choice = findViewById(R.id.button4);
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number_of_questions;
                try{
                    number_of_questions = Integer.parseInt(enter.getText().toString());
                    if(number_of_questions <= 0 || number_of_questions > 293){
                        disclaimer_2.setText("Are you sure?");
                    }else{
                        Intent i = new Intent(BeforeTestActivity.this, AnotherTest.class);
                        i.putExtra("n", number_of_questions);
                        startActivity(i);
                    }
                }catch (Exception e){
                    disclaimer_2.setText("Input a natural \nnumber, please");
                }
            }
        });
        Button writing = findViewById(R.id.button5);
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number_of_questions;
                try{
                    number_of_questions = Integer.parseInt(enter.getText().toString());
                    if(number_of_questions <= 0 || number_of_questions > 293){
                        disclaimer_2.setText("Are you sure?");
                    }else{
                        Intent i = new Intent(BeforeTestActivity.this, Test.class);
                        i.putExtra("n", number_of_questions);
                        startActivity(i);
                        finish();
                    }
                }catch (Exception e){
                    disclaimer_2.setText("Input a natural \nnumber, please");
                }
            }
        });
    }
}
