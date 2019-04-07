package com.example.a3buttons.InternerPack;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class ConnectionClass extends AsyncTask<String, Void, String> {

    public ConnectivityInterface connectivityInterface = null;
    public ConnectionClass(ConnectivityInterface interfaces){
        this.connectivityInterface = interfaces;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String response="";
        try{
            url = new URL(strings[0]);

        }catch(MalformedURLException e){
            Log.d("URL","URL EXCEPTION");
        }
        try{

            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(35000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                response = readResponse(inputStream);
            }

        }catch(IOException e){Log.d("HTTPURL","Http Url Connection exception");}

        return response;
    }


    private String readResponse(InputStream is){
        StringBuilder sb = new StringBuilder();
        if(is == null){
            return "{}";
        }else{
            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try{
                String line = bufferedReader.readLine();
                while(line!=null){sb.append(line);line = bufferedReader.readLine();}
            }catch(IOException e){}
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        connectivityInterface.onResultComplete(s);
    }
}
