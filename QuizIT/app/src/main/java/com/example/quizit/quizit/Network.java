package com.example.quizit.quizit;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lucas Dilan on 24/03/2018.
 */

public class Network {

    public static Boolean sendPost(JSONObject jsonObject, String url){

        HttpURLConnection conn;
        //StringBuilder stringBuilder;
        OutputStreamWriter out;
        try {
            URL postUrl = new URL (url);
            conn = (HttpURLConnection) postUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("Host", "apitccapp.azurewebsites.net");

            conn.connect();

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(String.valueOf(jsonObject.toString().getBytes("UTF-8")));
            out.close();

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static String getEndereco (String url){
        InputStream inputStream;
        String resultado = "nada";

        try {
            HttpURLConnection conn;
            URL endPoint = new URL(url);
            conn = (HttpURLConnection) endPoint.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            inputStream = conn.getInputStream();
            resultado = Network.parseToString(inputStream);

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public static String parseToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }
}
