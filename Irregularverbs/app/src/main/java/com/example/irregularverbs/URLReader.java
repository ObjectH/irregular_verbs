package com.example.irregularverbs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader extends Thread{
    public String url;
    public String res = "";
    public URLReader(String site){
        url = site;
    }
    @Override
    public void start(){
        try {
            URL site = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(site.openStream()));
            res = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                res += inputLine;
            in.close();
            Log.d("ISTHRESOMETHING", res);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
