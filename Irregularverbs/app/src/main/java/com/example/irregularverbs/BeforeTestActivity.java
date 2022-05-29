package com.example.irregularverbs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;

public class BeforeTestActivity extends AppCompatActivity {
    public int user_choice;
    View.OnClickListener choice = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            RadioButton button = (RadioButton) view;
            switch(button.getId()){
                case R.id.radioButton:
                    user_choice = 0;
                    break;
                case R.id.radioButton2:
                    user_choice = 1;
                    break;
                default:
                    user_choice = 2;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_test);
        EditText enter = findViewById(R.id.howmanyverbs);
        TextView disclaimer = findViewById(R.id.disclaimer);
        TextView disclaimer_2 = findViewById(R.id.textView2);
        final int[] n = new int[1];
        Button start = findViewById(R.id.ok1);
        RadioButton choice = findViewById(R.id.radioButton);
        choice.setOnClickListener(this.choice);
        RadioButton writing = findViewById(R.id.radioButton2);
        writing.setOnClickListener(this.choice);
        final Class[] test = new Class[1];
        final boolean[] chosen = {true};
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   String pre_n = enter.getText().toString();
                   Scanner in = new Scanner(pre_n);
                   n[0] = in.nextInt();
                   if(n[0] <= 0 || n[0] > 121){
                       enter.setText("");
                       disclaimer.setText(R.string.incorrect_number);
                   }else{
                       if(user_choice == 0){
                           test[0] = AnotherTest.class;
                       }else if(user_choice == 1){
                           test[0] = Test.class;
                       }else{
                           disclaimer_2.setText(R.string.nothing_chosen);
                           chosen[0] = false;
                       }
                       if(chosen[0]){
                           Intent i = new Intent(BeforeTestActivity.this, test[0]);
                           i.putExtra("n", n[0]);
                           choice.setChecked(false);
                           writing.setChecked(false);
                           disclaimer.setText(R.string.question);
                           enter.setText("");
                           startActivity(i);

                       }
                   }
                }catch(Exception e){
                    enter.setText("");
                    disclaimer.setText(R.string.not_number);
                }
            }
        });
    }
}
