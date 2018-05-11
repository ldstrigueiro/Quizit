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
    private int modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_random);

        btnPlay = (Button) findViewById(R.id.btnPlayRand);
        btnPlay.setOnClickListener(this);

        aluno = getIntent().getParcelableExtra("ObjAluno");
        modo = getIntent().getIntExtra("Modo", -1);


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



    private class JSONTaskGet extends AsyncTask<String, String, String>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(JogarRandomActivity.this, "Aguarde", "Gerando pergunta.");
        }

        @Override
        protected String doInBackground(String... strings) {
            return Network.getDados(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null) {
                pergunta = Pergunta.getPerguntaJSON(s);
                intent = new Intent(JogarRandomActivity.this, PerguntaActivity.class);
                intent.putExtra("ObjPergunta", pergunta);
                intent.putExtra("ObjAluno", aluno);
                intent.putExtra("Modo", modo);
                intent.putExtra("Vidas", -1);
                progressDialog.dismiss();
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(JogarRandomActivity.this, "teste", Toast.LENGTH_LONG).show();
            }

        }
    }
}
