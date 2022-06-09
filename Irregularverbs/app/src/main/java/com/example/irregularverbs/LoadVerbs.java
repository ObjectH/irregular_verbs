package com.example.irregularverbs;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadVerbs{
    public static Integer[] generate_random_numbers(int number) {
        Integer[] numbers = new Integer[number];
        ArrayList<Integer> n = new ArrayList<>();
        while (n.size() < number){
            Integer new_el = (int) (Math.random()*292);
            while(n.contains(new_el) && new_el < 292){
                new_el++;
            }
            while(n.contains(new_el) && new_el > 0){
                new_el--;
            }
            n.add(new_el);
        }
        numbers = n.toArray(numbers);
        Arrays.sort(numbers);
        return numbers;
    }
    public static ArrayList<String[]> choose_task_writing(Integer[] indexes){
        ArrayList<String[]> response = new ArrayList<>();
        for(int i = 0; i < indexes.length; i++){
            response.add(new String[]{MainActivity.verbs[indexes[i]].f1, MainActivity.verbs[indexes[i]].f2, MainActivity.verbs[indexes[i]].f3, MainActivity.verbs[indexes[i]].t_main});
        }
        return response;
    }

    public static String[] choose_task_choice_questions(Integer[] indexes){
        String[] task = new String[indexes.length];
        int form;
        for(int i = 0; i < indexes.length; i++){
            form = (int)(Math.random()/0.5);
            switch(form){
                case 0:
                    task[i] = MainActivity.verbs[indexes[i]].f2;
                    break;
                case 1:
                    task[i] = MainActivity.verbs[indexes[i]].f3;
                    break;
                default:
                    task[i] = ":(";
            }
        }
        return task;
    }
    public static String[][] choose_task_choice_variants(Integer[] indexes){
        String[][] translations = new String[indexes.length][4];
        for(int i = 0; i < indexes.length; i++) {
            int right_answer_index = (int) ((Math.random() / 3.0) * 10);
            translations[i][right_answer_index] = MainActivity.verbs[indexes[i]].t_main;
                for (int j = 0; j < 4; j++) {
                    if (j != right_answer_index) {
                        int wrong_answer = (int) (Math.random()*293);
                        translations[i][j] = MainActivity.verbs[wrong_answer].t_main;
                    }
                }
            }
        return translations;
    }
    public static String[] choose_right_translations(Integer[] indexes){
        String[] right_translation = new String[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            right_translation[i] = MainActivity.verbs[indexes[i]].t_main;
        }
        return right_translation;
    }
    public static String validation(String s){
        s = s.toLowerCase();
        String res = "";
        int iterations;
        iterations = Math.min(s.length(), 35);
        for(int j = 0; j < iterations; j++) {
                if ((s.charAt(j) >= 1072 && s.charAt(j) <= 1103) || s.charAt(j) == 1105 || (s.charAt(j) >= 97 && s.charAt(j) <= 122)) {
                    if(s.charAt(j) == 1105){
                        res+=(char) 1077;
                    }else {
                        res += s.charAt(j);
                    }
                }
            }
        return res;
    }
    public static String[][] get_all_the_verbs() {
        String[][] res = new String[293][4];
        for(int i = 0; i < 293; i++){
            res[i] = new String[]{MainActivity.verbs[i].f1, MainActivity.verbs[i].f2, MainActivity.verbs[i].f3, MainActivity.verbs[i].t_main};
        }
        return res;
    }
    public static String[] find_the_word(String word) {
        word = validation(word);
        if (word.charAt(0) < 1072) {
            for (int i = 0; i < MainActivity.verbs.length; i++) {
                if (MainActivity.verbs[i].f1.equals(word) || MainActivity.verbs[i].f2.equals(word) || MainActivity.verbs[i].f3.equals(word)) {
                    return new String[]{MainActivity.verbs[i].f1, MainActivity.verbs[i].f2, MainActivity.verbs[i].f3, MainActivity.verbs[i].t_main};
                }
            }
            for (int i = 0; i < MainActivity.verbs.length; i++) {
                if (MainActivity.verbs[i].f1.contains(word) || MainActivity.verbs[i].f2.contains(word) || MainActivity.verbs[i].f3.contains(word)) {
                    return new String[]{MainActivity.verbs[i].f1, MainActivity.verbs[i].f2, MainActivity.verbs[i].f3, MainActivity.verbs[i].t_main};
                }
            }
        } else {
            for (int i = 0; i < MainActivity.verbs.length; i++) {
                if (MainActivity.verbs[i].t_main.equals(word) || Arrays.binarySearch(MainActivity.verbs[i].translations, word) >= 0){
                    return new String[]{MainActivity.verbs[i].f1, MainActivity.verbs[i].f2, MainActivity.verbs[i].f3, MainActivity.verbs[i].t_main};
                }
            }
        }
        return new String[]{"", "", "", ""};
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