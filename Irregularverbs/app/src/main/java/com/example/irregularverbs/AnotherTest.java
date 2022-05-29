package com.example.irregularverbs;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irregularverbs.data.DBHelper;

import java.text.MessageFormat;

public class AnotherTest extends AppCompatActivity{
    double k = 0.0;
    int mainColor;
    int n;
    int[] indexes;
    int current_question = 0;
    String[] answers;
    String[] task;
    double mark = 0;
    TextView question;
    String[][] variants;
    Button v1;
    Button v2;
    Button v3;
    Button v4;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_test);
        Intent i = getIntent();
        n = i.getIntExtra("n",5);
        indexes = LoadVerbs.generate_random_numbers(n);
        answers = LoadVerbs.choose_right_translations(indexes, new DBHelper(this));
        task = LoadVerbs.choose_task_choice_questions(indexes, new DBHelper(this));
        variants = LoadVerbs.choose_task_choice_variants(indexes, new DBHelper(this));
        question = findViewById(R.id.textView);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v1.setText(variants[current_question][0]);
        v1.setOnClickListener(onClickListener);
        v2.setText(variants[current_question][1]);
        v2.setOnClickListener(onClickListener);
        v3.setText(variants[current_question][2]);
        v3.setOnClickListener(onClickListener);
        v4.setText(variants[current_question][3]);
        v4.setOnClickListener(onClickListener);
        question.setText(task[current_question]);
        if(getResources().getConfiguration().colorMode == Configuration.UI_MODE_NIGHT_YES){
            mainColor = getResources().getColor(R.color.teal_200);
        }else{
            mainColor = getResources().getColor(R.color.main_color);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            if(current_question < answers.length) {
                if (button.getText().toString().equals(answers[current_question])) {
                    current_question++;
                    mark++;
                    if (current_question < variants.length) {
                        v1.setText(variants[current_question][0]);
                        v1.setBackgroundColor(mainColor);
                        v2.setText(variants[current_question][1]);
                        v2.setBackgroundColor(mainColor);
                        v3.setText(variants[current_question][2]);
                        v3.setBackgroundColor(mainColor);
                        v4.setText(variants[current_question][3]);
                        v4.setBackgroundColor(mainColor);
                        question.setText(task[current_question]);
                    }else{
                        setContentView(R.layout.result);
                        TextView answer = findViewById(R.id.res);
                        mark-=k;
                        answer.setText(MessageFormat.format("Yor result: {0} OF {1}", mark, n));
                    }
                } else {
                    k += 0.33;
                    button.setBackgroundColor(getResources().getColor(R.color.wrong_answer));
                }
            }
        }
    };
}
