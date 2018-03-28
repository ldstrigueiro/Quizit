package com.example.quizit.quizit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lucas Dilan on 24/03/2018.
 */

public class Network {
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
