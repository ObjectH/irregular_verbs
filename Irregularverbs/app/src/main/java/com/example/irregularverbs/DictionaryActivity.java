package com.example.irregularverbs;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irregularverbs.data.DBHelper;

public class DictionaryActivity extends AppCompatActivity {
    public float text_size;
    public int primary_color;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState){
        text_size = getResources().getConfiguration().screenWidthDp/32.5f;
        super.onCreate(savedInstanceState);
        draw();
    }
    @SuppressLint("NewApi")
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        text_size = newConfig.screenWidthDp/32.5f;
        draw();
    }
    public void draw(){
        //Горизонтальная прокрутка
        HorizontalScrollView main = new HorizontalScrollView(this);

        //Общая вертикальная разметка
        LinearLayout in_horizontal_scroll = new LinearLayout(this);
        in_horizontal_scroll.setOrientation(LinearLayout.VERTICAL);
        main.addView(in_horizontal_scroll);

        //Блок, в котором всё, что касается поиска
        LinearLayout search = new LinearLayout(this);
        search.setMinimumWidth(main.getWidth());
        search.setOrientation(LinearLayout.HORIZONTAL);
        in_horizontal_scroll.addView(search);

        //Ввод запроса
        EditText search_input = new EditText(this);
        search_input.setHint("A verb in English or in Russian");
        search_input.setMinimumWidth(search.getMinimumWidth()*4/5);
        search_input.setTextSize(text_size);
        search.addView(search_input);

        //Текстовый блок для сообщения в случае, если введённого глагола нет в приложении
        TextView not_found = new TextView(this);
        not_found.setTextSize(text_size);

        //Текстовые блоки для ответа
        TextView inf = new TextView(this);
        inf.setPadding(5, 5, 5, 5);
        inf.setTextSize(text_size);

        TextView ps = new TextView(this);
        ps.setPadding(5, 5, 5, 5);
        ps.setTextSize(text_size);

        TextView pp = new TextView(this);
        pp.setPadding(5, 5, 5, 5);
        pp.setTextSize(text_size);

        TextView translation = new TextView(this);
        translation.setPadding(5, 5, 5, 5);
        translation.setTextSize(text_size);

        //Кнопка для начала поиска
        Button search_btn = new Button(this);
        search.setMinimumWidth(search.getWidth()/5);
        search_btn.setText("SEARCH");
        search_btn.setTextSize(text_size);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String[] answer = LoadVerbs.find_the_word(search_input.getText().toString(),
                            new DBHelper(DictionaryActivity.this));
                    if(answer[0].equals("")){
                        not_found.setText("NOT FOUND");
                    }else{
                        not_found.setText("");
                        inf.setText(answer[0]);
                        ps.setText(answer[1]);
                        pp.setText(answer[2]);
                        translation.setText(answer[3]);
                    }
                    search_input.setText("");
                }catch(Exception e){//Если пользователь ввёл что-то, что вызвало ошибку,
                    // сообщаем ему об этом
                    inf.setText("");
                    ps.setText("");
                    pp.setText("");
                    translation.setText("");
                    not_found.setText("INCORRECT INPUT");
                }
            }
        });
        //Добавление кнопки и поля для сообщения
        search.addView(search_btn);

        in_horizontal_scroll.addView(not_found);

        //Блок для вывода ответа на запрос
        TableLayout response = new TableLayout(this);
        response.setMinimumWidth(main.getWidth());
        in_horizontal_scroll.addView(response);

        //Костыль подъехал
        //Нужен, чтобы стоблцы в этой таблице и в таблице со словами были одинаковые
        TableRow debug = new TableRow(this);
        response.addView(debug);

        TextView d_1 = new TextView(this);
        d_1.setPadding(5, 5, 5,5);
        d_1.setTextColor(primary_color);
        d_1.setText("misunderstand");
        d_1.setTextSize(text_size);
        debug.addView(d_1);

        TextView d_2 = new TextView(this);
        d_2.setPadding(5, 5, 5,5);
        d_2.setTextColor(primary_color);
        d_2.setText("misunderstood");
        d_2.setTextSize(text_size);
        debug.addView(d_2);

        TextView d_3 = new TextView(this);
        d_3.setPadding(5, 5, 5,5);
        d_3.setTextColor(primary_color);
        d_3.setText("misunderstood");
        d_3.setTextSize(text_size);
        debug.addView(d_3);

        //Заголовки столбцов
        TableRow titles = new TableRow(this);
        titles.setMinimumWidth(main.getWidth());
        response.addView(titles);

        TextView t1 = new TextView(this);
        t1.setMinimumWidth(main.getWidth()/4);
        t1.setText("INFINITIVE");
        t1.setTextSize(text_size);
        t1.setPadding(5, 5, 5, 5);
        titles.addView(t1);

        TextView t2 = new TextView(this);
        t2.setMinimumWidth(main.getWidth()/4);
        t2.setText("PAST SIMPLE");
        t2.setTextSize(text_size);
        t2.setPadding(5, 5, 5, 5);
        titles.addView(t2);

        TextView t3 = new TextView(this);
        t3.setMinimumWidth(main.getWidth()/4);
        t3.setText("PAST PARTICIPLE");
        t3.setTextSize(text_size);
        t3.setPadding(5, 5, 5, 5);
        titles.addView(t3);

        TextView t4 = new TextView(this);
        t4.setMinimumWidth(main.getWidth()/4);
        t4.setText("TRANSLATION");
        t4.setTextSize(text_size);
        t4.setPadding(5, 5, 5, 5);
        titles.addView(t4);

        //Добавление полей для вывода самих ответов
        TableRow results = new TableRow(this);
        results.setMinimumWidth(main.getWidth()/4);
        response.addView(results);

        results.addView(inf);

        results.addView(ps);

        results.addView(pp);

        results.addView(translation);

        // Вертикальная прокрутка только для глаголов
        ScrollView vertical_scroll = new ScrollView(this);
        in_horizontal_scroll.addView(vertical_scroll);

        //Блок, внутри которого они написаны
        TableLayout dictionary = new TableLayout(this);
        dictionary.setMinimumWidth(main.getWidth());
        vertical_scroll.addView(dictionary);

        //Добавление глаголов
        String[][] all_the_verbs = LoadVerbs.get_all_the_verbs(new DBHelper(this));
        for(int i = 0;i < 294; i++){
            TableRow verb = new TableRow(this);
            dictionary.addView(verb);

            TextView infinitive = new TextView(this);
            infinitive.setText(all_the_verbs[i][0]);
            infinitive.setMinimumWidth(inf.getWidth());
            infinitive.setPadding(5, 5, 5, 5);
            infinitive.setTextSize(text_size);
            verb.addView(infinitive);

            TextView second_form = new TextView(this);
            second_form.setText(all_the_verbs[i][1]);
            second_form.setMinimumWidth(ps.getWidth());
            second_form.setPadding(5, 5, 5 ,5);
            second_form.setTextSize(text_size);
            verb.addView(second_form);

            TextView third_form = new TextView(this);
            third_form.setText(all_the_verbs[i][2]);
            third_form.setMinimumWidth(pp.getWidth());
            third_form.setPadding(5, 5, 5 ,5);
            third_form.setTextSize(text_size);
            verb.addView(third_form);

            TextView translate = new TextView(this);
            translate.setText(all_the_verbs[i][3]);
            translate.setMinimumWidth(translation.getWidth());
            translate.setPadding(5, 5, 5 ,5);
            translate.setTextSize(text_size);
            verb.addView(translate);
        }
        //Продолжение костыля
        TableRow debug_d = new TableRow(this);
        dictionary.addView(debug_d);

        TextView debug_r_1 = new TextView(this);
        debug_r_1.setTextColor(primary_color);
        debug_r_1.setText("misunderstand");
        debug_r_1.setPadding(5,5,5,5);
        debug_r_1.setTextSize(text_size);
        debug_d.addView(debug_r_1);

        TextView debug_r_2 = new TextView(this);
        debug_r_2.setTextColor(primary_color);
        debug_r_2.setText("misunderstood");
        debug_r_2.setPadding(5,5,5,5);
        debug_r_2.setTextSize(text_size);
        debug_d.addView(debug_r_2);

        TextView debug_r_3 = new TextView(this);
        debug_r_3.setTextColor(primary_color);
        debug_r_3.setText("PAST PARTICIPLE");
        debug_r_3.setPadding(5,5,5,5);
        debug_r_3.setTextSize(text_size);
        debug_d.addView(debug_r_3);

        setContentView(main);
    }
}
