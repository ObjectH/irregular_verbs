package com.example.irregularverbs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Mistakes extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawActivity(getResources().getConfiguration().screenWidthDp/31f, Test.mistakes);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration){
        drawActivity(configuration.screenWidthDp / 32.5f, Test.mistakes);
    }

    public void drawActivity(float text_size, ArrayList<String[]> mistakes){
        ScrollView scrollView = new ScrollView(getApplicationContext());

        FrameLayout container = new FrameLayout(getApplicationContext());
        container.setForegroundGravity(Gravity.CENTER);
        scrollView.addView(container);

        TableLayout main = new TableLayout(getApplicationContext());
        container.addView(main);

        TableRow headings = new TableRow(getApplicationContext());

        TextView h1 = new TextView(getApplicationContext());
        h1.setText("INFINITIVE");
        h1.setTextSize(text_size);
        h1.setPadding(5,5,5,5);
        headings.addView(h1);

        TextView h2 = new TextView(getApplicationContext());
        h2.setText("PAST SIMPLE");
        h2.setTextSize(text_size);
        h2.setPadding(5,5,5,5);
        headings.addView(h2);

        TextView h3 = new TextView(getApplicationContext());
        h3.setText("PAST PARTICIPLE");
        h3.setTextSize(text_size);
        h3.setPadding(5,5,5,5);
        headings.addView(h3);

        TextView h4 = new TextView(getApplicationContext());
        h4.setText("TRANSLATION");
        h4.setTextSize(text_size);
        h4.setPadding(5,5,5,5);
        headings.addView(h4);

        main.addView(headings);

        for(int j = 0; j < mistakes.size(); j++){
            TableRow right = new TableRow(getApplicationContext());

            TextView inf = new TextView(getApplicationContext());
            inf.setTextSize(text_size);
            inf.setText(mistakes.get(j)[0]);
            inf.setPadding(5,5,5,5);
            inf.setBackgroundColor(getResources().getColor(R.color.additional_color_b));
            right.addView(inf);

            TextView ps = new TextView(getApplicationContext());
            ps.setTextSize(text_size);
            ps.setText(mistakes.get(j)[4]);
            ps.setPadding(5,5,5,5);
            ps.setBackgroundColor(getResources().getColor(R.color.additional_color_b));
            right.addView(ps);

            TextView pp = new TextView(getApplicationContext());
            pp.setTextSize(text_size);
            pp.setText(mistakes.get(j)[5]);
            pp.setPadding(5,5,5,5);
            pp.setBackgroundColor(getResources().getColor(R.color.additional_color_b));
            right.addView(pp);

            TextView tr = new TextView(getApplicationContext());
            tr.setTextSize(text_size);
            tr.setText(mistakes.get(j)[6]);
            tr.setPadding(5,5,5,5);
            tr.setBackgroundColor(getResources().getColor(R.color.additional_color_b));
            right.addView(tr);

            main.addView(right);

            TableRow wrong = new TableRow(getApplicationContext());



            TextView inf_w = new TextView(getApplicationContext());
            inf_w.setTextSize(text_size);
            inf_w.setText(LoadVerbs.beautify(mistakes.get(j)[0]));
            inf_w.setPadding(5,5,5,5);
            wrong.addView(inf_w);

            TextView ps_w = new TextView(getApplicationContext());
            ps_w.setTextSize(text_size);
            ps_w.setText(LoadVerbs.beautify(mistakes.get(j)[1]));
            ps_w.setPadding(5,5,5,5);
            wrong.addView(ps_w);

            TextView pp_w = new TextView(getApplicationContext());
            pp_w.setTextSize(text_size);
            pp_w.setText(LoadVerbs.beautify(mistakes.get(j)[2]));
            pp_w.setPadding(5,5,5,5);
            wrong.addView(pp_w);

            TextView tr_w = new TextView(getApplicationContext());
            tr_w.setTextSize(text_size);
            tr_w.setText(LoadVerbs.beautify(mistakes.get(j)[3]));
            tr_w.setPadding(5,5,5,5);
            wrong.addView(tr_w);

            main.addView(wrong);
        }
        setContentView(scrollView);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Test.mistakes = new ArrayList<>();
    }
}
