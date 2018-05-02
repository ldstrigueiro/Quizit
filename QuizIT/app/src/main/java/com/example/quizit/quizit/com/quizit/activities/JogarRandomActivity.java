package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class JogarRandomActivity extends Activity implements View.OnClickListener {
    private Button btnPlay;
    private Intent intent;
    private Pergunta pergunta;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String urlPergunta = "http://apitccapp.azurewebsites.net/Pergunta/getPerguntaRandom/";
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_random);

        btnPlay = (Button) findViewById(R.id.btnPlayRand);
        btnPlay.setOnClickListener(this);

        aluno = getIntent().getParcelableExtra("ObjAluno");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayRand:
                jsonTaskGet.execute(urlPergunta+aluno.getSemestre());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Pergunta getPerguntaJSON(String json){
       Pergunta pergunta = new Pergunta();
        try {
            JSONObject jsonObject = new JSONObject(json);

            pergunta.setId(jsonObject.getInt("idPergunta"));
            pergunta.setEnunciado(jsonObject.getString("pergunta"));
            pergunta.setResposta(jsonObject.getString("resposta"));
            pergunta.setOpcao1(jsonObject.getString("opcao1"));
            pergunta.setOpcao2(jsonObject.getString("opcao2"));
            pergunta.setOpcao3(jsonObject.getString("opcao3"));
            pergunta.setIdArea(jsonObject.getInt("idArea"));
            pergunta.setArea(jsonObject.getString("nomeArea"));

            return pergunta;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private class JSONTaskGet extends AsyncTask<String, String, String>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(JogarRandomActivity.this, "Aguarde", "Gerando pergunta.");
        }

        @Override
        protected String doInBackground(String... strings) {
            return Network.getEndereco(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null) {
                pergunta = getPerguntaJSON(s);
                intent = new Intent(JogarRandomActivity.this, PerguntaActivity.class);
                intent.putExtra("ObjPergunta", pergunta);
                intent.putExtra("ObjAluno", aluno);
                progressDialog.dismiss();
                startActivity(intent);
                finish();
            }else{

                Toast.makeText(JogarRandomActivity.this, "teste", Toast.LENGTH_LONG).show();
            }

        }
    }
}
