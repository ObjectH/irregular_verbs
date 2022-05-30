package com.example.irregularverbs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.example.irregularverbs.data.DBHelper;

import java.util.ArrayList;

public class Test extends FragmentActivity {
    public static int number = 1;
    public static int current_question = 0;
    public static int mark = 0;
    public static String[] task;
    public static int[] indexes;
    public static String[][] answers;
    public float text_size;
    public static ArrayList<String[]> mistakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        text_size = getResources().getConfiguration().screenWidthDp/28.0f;

        Intent i = getIntent();
        number = i.getIntExtra("n", 5)+1;

        indexes = LoadVerbs.generate_random_numbers(number);
        task = LoadVerbs.choose_task_writing(indexes, new DBHelper(this));
        answers = LoadVerbs.choose_answers_writing(indexes, new DBHelper(this));
        mistakes = new ArrayList<String[]>();

        TextView question = (TextView) findViewById(R.id.textView6);
        question.setText(task[0]);
        current_question++;

        Button next = (Button) findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView question = (TextView) findViewById(R.id.textView6);
                EditText form2 = (EditText) findViewById(R.id.pastsimpleinput);
                EditText form3 = (EditText) findViewById(R.id.pastparticipleinput);
                EditText translation = (EditText) findViewById(R.id.translationinput);
                String site_content;
                if(current_question < number){
                    question.setText(task[current_question]);
                }
                if (current_question <= number) {
                    try {
                        String f2 = form2.getText().toString();
                        String f3 = form3.getText().toString();
                        String tr = translation.getText().toString();
                        String status = "RIGHT";
                        boolean correct = true;
                        Log.e("Values", answers[current_question-1][1]);
                        if (LoadVerbs.validation(f2).equals(answers[current_question-1][1])) {
                            mark += 2;
                        } else if ((answers[current_question-1][1]).contains(LoadVerbs.validation(f2))) {
                            mark++;
                            correct = false;
                            status = "NEARLY:";
                        }else{
                            correct = false;
                            status = "WRONG:";
                        }
                        if (LoadVerbs.validation(f3).equals(answers[current_question-1][2])) {
                            mark += 2;
                            if(status.equals("WRONG:")){
                                status = "NEARLY:";
                            }
                        } else if ((answers[current_question-1][2]).contains(LoadVerbs.validation(f3))) {
                            mark++;
                            correct = false;
                            status = "NEARLY:";
                        }else{
                            correct = false;
                            if(status.equals("RIGHT") || status.equals("NEARLY:")){
                                status = "NEARLY:";
                            }else{
                                status = "WRONG:";
                            }
                        }
                        URLReader url = new URLReader(answers[current_question-1][4]);
                        url.start();
                        site_content = url.res;
                        Log.d("translation", site_content);
                        if (site_content.contains(tr) || LoadVerbs.validation(answers[current_question-1][3]).contains(LoadVerbs.validation(tr))) {
                            mark++;
                            if(status.equals("WRONG:")){
                                status = "NEARLY: ";
                            }
                        }else{
                            if(status.equals("RIGHT") || status.equals("NEARLY:")){
                                status = "NEARLY:";
                            }else{
                                status = "WRONG:";
                            }
                            correct = false;
                        }
                        if(!correct){
                            String[] right = LoadVerbs.find_the_word(task[current_question-1], new DBHelper(getApplicationContext()));
                            mistakes.add(new String[]{task[current_question-1], f2, f3, tr, right[1], right[2], right[3], status});
                        }
                        current_question++;
                        form2.setText("");
                        form3.setText("");
                        translation.setText("");
                        if(current_question == number){
                            setContentView(R.layout.result);
                            TextView result = (TextView) findViewById(R.id.res);
                            result.setText("YOR RESULT:" + mark + " OF " + (number-1) * 5);
                            Button see_mistakes = (Button) findViewById(R.id.button3);
                            Button back = (Button) findViewById(R.id.button2);
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                            if(mark < (number-1) * 5) {
                                see_mistakes.setVisibility(View.VISIBLE);
                            }
                            see_mistakes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(Test.this, Mistakes.class);
                                    startActivity(i);
                                    number = 1;
                                    current_question = 0;
                                    mark = 0;
                                }
                            });
                        }
                    }
                    catch (Exception e) {
                        Toast.makeText(Test.this, "ERROR", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
