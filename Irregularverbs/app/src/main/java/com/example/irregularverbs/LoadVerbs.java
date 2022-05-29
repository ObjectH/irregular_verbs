package com.example.irregularverbs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.irregularverbs.data.DBHelper;
import com.example.irregularverbs.data.VerbsContract;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoadVerbs{
    public static int[] generate_random_numbers(int number){
        int[] numbers = new int[number];
        int new_el;
        int one_sign;
        if(number > 5){
            one_sign = 5;
        }else{
            one_sign = number/3;
        }
        int two_sign = (number - one_sign)/2;
        for(int i = 0; i < number; i++) {
            if (i < one_sign) {
                new_el = (int) (Math.random() * 10);
            } else if (i < two_sign + one_sign) {
                new_el = (int) (Math.random() * 100);
            } else {
                new_el = (int) (int)((Math.random()/3.4)*1000);
            }
            for (int j = 0; j < numbers.length; j++) {
                if (numbers[j] == new_el) {
                    new_el--;
                    if(new_el < 0){
                        new_el = 294;
                        for(int k = 0; k < numbers.length; k++){
                            if(numbers[k] == new_el){
                                new_el--;
                            }
                        }
                        if(new_el == -1){
                            new_el++;
                        }
                    }
                }
            }
            numbers[i] = new_el;
            for (int k = 0; k < numbers.length; k++) {
                int max = numbers[0];
                int index = 0;
                int[] after = new int[numbers.length];
                for (int l = 0; l < (numbers.length - k); l++) {
                    if (numbers[l] > max) {
                        max = numbers[l];
                        index = l;
                    }
                }
                System.arraycopy(numbers, index + 1, after, 0, numbers.length - index - 1);
                System.arraycopy(after, 0, numbers, index, numbers.length - index);
                numbers[numbers.length - 1] = max;
            }
        }
        return numbers;
    }
    public static String[] choose_task_writing(int[] indexes, DBHelper dbHelper){
        String[] response = new String[indexes.length];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        for(int i=0; i < indexes.length; i++){
            Cursor cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.FORM_1_COLUMN},
                    "_id = ?", new String[]{(indexes[i]+"")}, null, null, null);
            if (cursor.moveToFirst()){
                int f1ColIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_1_COLUMN);
                response[i] = cursor.getString(f1ColIndex);
            }
        }
        return response;
    }

    public static String[][] choose_answers_writing(int[] indexes, DBHelper dbHelper){
        String[][] response = new String[indexes.length][5];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        for(int i=0; i < indexes.length; i++){
            Cursor cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.FORM_1_COLUMN,
                            VerbsContract.VerbsEntry.FORM_2_COLUMN, VerbsContract.VerbsEntry.FORM_3_COLUMN, VerbsContract.VerbsEntry.TRANSLATION,
                            VerbsContract.VerbsEntry.LINK}, "_id = ?", new String[]{(indexes[i]+"")}, null, null, null);
            if (cursor.moveToFirst()){
                int f1ColIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_1_COLUMN);
                int f2ColIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_2_COLUMN);
                int f3ColIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_3_COLUMN);
                int translation = cursor.getColumnIndex(VerbsContract.VerbsEntry.TRANSLATION);
                int lnColIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.LINK);
                response[i][0] = LoadVerbs.validation(cursor.getString(f1ColIndex));
                response[i][1] = LoadVerbs.validation(cursor.getString(f2ColIndex));
                response[i][2] = LoadVerbs.validation(cursor.getString(f3ColIndex));
                response[i][3] = cursor.getString(translation);
                response[i][4] = cursor.getString(lnColIndex);
            }
        }
        return response;
    }

    public static String[] choose_task_choice_questions(int[] indexes, DBHelper dbHelper){
        String[] task = new String[indexes.length];
        int form;
        String row;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        for(int i = 0; i < indexes.length; i++){
            form = (int)(Math.random()/0.5);
            switch(form){
                case 0:
                    row = VerbsContract.VerbsEntry.FORM_2_COLUMN;
                    break;
                case 1:
                    row = VerbsContract.VerbsEntry.FORM_3_COLUMN;
                    break;
                default:
                    row = " ";
            }
            Cursor cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{row}, "_id = ?",
                    new String[]{indexes[i]+""}, null, null, null);
            if(cursor.moveToFirst()){
                int fIndex = cursor.getColumnIndex(row);
                task[i] = cursor.getString(fIndex);
            }
        }
        return task;
    }
    public static String[][] choose_task_choice_variants(int[] indexes, DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[][] translations = new String[indexes.length][4];
        for(int i = 0; i < indexes.length; i++) {
            int right_answer_index = (int) ((Math.random() / 3.0) * 10);
            Cursor cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.TRANSLATION},
                    "_id = ?", new String[]{indexes[i] + ""}, null, null, null);
            if (cursor.moveToFirst()) {
                int rtIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.TRANSLATION);
                translations[i][right_answer_index] = cursor.getString(rtIndex);
                for (int j = 0; j < 4; j++) {
                    if (j != right_answer_index) {
                        int wrong_answer;
                        int exponent = (int) ((Math.random() / 4.0) * 10);
                        switch (exponent) {
                            case 1:
                                wrong_answer = (int) ((Math.random()) * 100);
                                break;
                            case 2:
                                wrong_answer = (int) ((Math.random()) * 10);
                                break;
                            default:
                                wrong_answer = (int) ((Math.random() / 3.41) * 1000);
                                break;
                        }
                        wrong_answer++;

                        cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.TRANSLATION},
                                "_id = ?", new String[]{wrong_answer + ""}, null, null, null);
                        if (cursor.moveToFirst()) {
                            int wtIndex = cursor.getColumnIndex(VerbsContract.VerbsEntry.TRANSLATION);
                            translations[i][j] = cursor.getString(wtIndex);
                        }else{
                            Log.e("empty", wrong_answer+"");
                        }
                    }
                }
            }
        }
        return translations;
    }
    public static String[] choose_right_translations(int[] indexes, DBHelper dbHelper){
        String[] right_translation = new String[indexes.length];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        for(int i = 0; i < indexes.length; i++){
            cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.TRANSLATION}, "_id = ?",
                    new String[]{indexes[i]+""}, null, null, null);
            if(cursor.moveToFirst()) {
                right_translation[i] = cursor.getString(0);
            }
        }
        return right_translation;
    }
    public static String validation(String s){
        s = s.toLowerCase();
        String res = "";
            for(int j = 0; j < s.length(); j++) {
                if ((s.charAt(j) >= 1072 && s.charAt(j) <= 1103) || s.charAt(j) == 1105 || (s.charAt(j) >= 97 && s.charAt(j) <= 122)) {
                    res += s.charAt(j);
                }
            }
        return res;
    }
    public static String[][] get_all_the_verbs(DBHelper dbHelper){
        String[][] res = new String[294][4];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(VerbsContract.VerbsEntry.TABLE_NAME, new String[]{VerbsContract.VerbsEntry.FORM_1_COLUMN,
                        VerbsContract.VerbsEntry.FORM_2_COLUMN, VerbsContract.VerbsEntry.FORM_3_COLUMN, VerbsContract.VerbsEntry.TRANSLATION},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            for(int i = 0; i < 294; i++){
                int index1 = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_1_COLUMN);
                int index2 = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_2_COLUMN);
                int index3 = cursor.getColumnIndex(VerbsContract.VerbsEntry.FORM_3_COLUMN);
                int indext = cursor.getColumnIndex(VerbsContract.VerbsEntry.TRANSLATION);
                res[i][0] = cursor.getString(index1);
                res[i][1] = cursor.getString(index2);
                res[i][2] = cursor.getString(index3);
                res[i][3] = cursor.getString(indext);
                cursor.moveToNext();
            }
        }
        return res;
    }
    public static String[] find_the_word(String word, DBHelper dbHelper){
        word = validation(word);
        String[] res = {"", "", "", "", ""};
        String[][] all_the_verbs = LoadVerbs.get_all_the_verbs(dbHelper);
        for(int i = 0; i < all_the_verbs.length; i++){
            if(validation(all_the_verbs[i][0]).equals(word) || validation(all_the_verbs[i][1]).equals(word)
                    || validation(all_the_verbs[i][2]).equals(word) || validation(all_the_verbs[i][3]).equals(word)){
                res[0] = all_the_verbs[i][0];
                res[1] = all_the_verbs[i][1];
                res[2] = all_the_verbs[i][2];
                res[3] = all_the_verbs[i][3];
            }
        }
        if (res[0].equals("")) {
            for (int i = 0; i < all_the_verbs.length; i++) {
                if (validation(all_the_verbs[i][0]).contains(word) || validation(all_the_verbs[i][1]).contains(word)
                        || validation(all_the_verbs[i][2]).contains(word) || validation(all_the_verbs[i][3]).contains(word)) {
                    res[0] = all_the_verbs[i][0];
                    res[1] = all_the_verbs[i][1];
                    res[2] = all_the_verbs[i][2];
                    res[3] = all_the_verbs[i][3];
                }
            }
        }
        return res;
    }
    public static String beautify(String s){
        String res = "";
        for(int j = 0; j < s.length(); j++){
            if((s.charAt(j) >= 1072 && s.charAt(j) <= 1103) || s.charAt(j) == 1105 || (s.charAt(j) >= 97 && s.charAt(j) <= 122) ||
                    (s.charAt(j) >= 65 && s.charAt(j) <= 90) || (s.charAt(j) >= 1040 && s.charAt(j) <= 1071)) {
                res += s.charAt(j);
            }else{
                res += '\n';
            }
        }
        return res;
    }
}