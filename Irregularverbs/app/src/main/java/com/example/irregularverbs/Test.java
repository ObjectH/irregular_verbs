package com.example.irregularverbs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class Test extends FragmentActivity {
    public static ArrayList<String[]> mistakes = new ArrayList<>();
    int mark = 0;
    int current_question = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent i = getIntent();
        ArrayList<String[]> task = LoadVerbs.choose_task_writing(LoadVerbs.generate_random_numbers(i.getIntExtra("n", 5)));
        TextView question = findViewById(R.id.textView6);
        question.setText(task.get(0)[0]);
        Button next = findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ps = findViewById(R.id.pastsimpleinput);
                EditText pp = findViewById(R.id.pastparticipleinput);
                EditText tr = findViewById(R.id.translationinput);
                boolean correct = true;
                if(ps.getText().toString().equals(task.get(current_question)[1])){
                    mark++;
                }else{
                    correct = false;
                }if(pp.getText().toString().equals(task.get(current_question)[2])){
                    mark++;
                }else{
                    correct = false;
                }if(tr.getText().toString().equals(task.get(current_question)[3])){
                    mark++;
                }else {
                    for(int i = 0; i < 292; i++){
                        if(Arrays.binarySearch(MainActivity.verbs[i].translations, tr.getText().toString()) >= 0){
                            mark++;
                            break;
                        }
                    }
                }

                if(current_question < i.getIntExtra("n", 5)-1){
                    current_question++;
                    if(!correct){
                        mistakes.add(new String[]{question.getText().toString(), ps.getText().toString(),
                                pp.getText().toString(), tr.getText().toString(), task.get(current_question)[1],
                                task.get(current_question)[2], task.get(current_question)[3]});
                    }
                    question.setText(task.get(current_question)[0]);
                    ps.setText("");
                    pp.setText("");
                    tr.setText("");
                }else{
                    if(!correct){
                        mistakes.add(new String[]{question.getText().toString(), ps.getText().toString(),
                                pp.getText().toString(), tr.getText().toString(), task.get(current_question)[1],
                                task.get(current_question)[2], task.get(current_question)[3]});
                    }
                    setContentView(R.layout.result);
                    TextView result = findViewById(R.id.res);
                    result.setText("Your result: "+mark+" of "+(i.getIntExtra("n", 5)*3));
                    if(!correct){
                        Button seemistakes = findViewById(R.id.button3);
                        seemistakes.setVisibility(View.VISIBLE);
                        seemistakes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent k = new Intent(Test.this, Mistakes.class);
                                mark = 0;
                                current_question = 0;
                                startActivity(k);
                                finish();
                            }
                        });
                        Button back = findViewById(R.id.button2);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }
}
