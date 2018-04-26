package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class JogarRandomActivity extends Activity implements View.OnClickListener {
    private Button btnPlay;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_random);

        btnPlay = (Button) findViewById(R.id.btnPlayRand);
        btnPlay.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayRand:
                intent = new Intent(this, PerguntaActivity.class);


                startActivity(intent);
                break;
        }
    }

    private Pergunta getPerguntaJSON(String json){
        Pergunta pergunta = new Pergunta();
        try {
            JSONObject jsonObject = new JSONObject(json);
            pergunta.setId(jsonObject.getInt("idPergunta"));
            pergunta.setArea(jsonObject.getString("nomeArea"));
            pergunta.setIdArea(jsonObject.getInt("idArea"));
            pergunta.setEnunciado(jsonObject.getString("pergunta"));
            //Terminar qnd arrumar a api
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return pergunta;
    }

    private class JSONTaskGets extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            return Network.getEndereco(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
