package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.SuportePojo;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.com.quizit.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

public class SuporteActivity extends Activity implements View.OnClickListener {

    private EditText edtMensagem;
    private Button btnEnviar;
    private Button btnSair;

    private int idAluno;
    private String url = "http://apitccapp.azurewebsites.net/api/Suporte";
    private JSONTaskPost jsonTaskPost;
    private SuportePojo suporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

        edtMensagem = findViewById(R.id.edtMsgSuporte);
        btnEnviar = findViewById(R.id.btnEnviarSuporte);
        btnSair = findViewById(R.id.btnSairSuporte);

        idAluno = getIntent().getIntExtra("idAluno", -1);

        btnEnviar.setOnClickListener(this);
        btnSair.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEnviarSuporte:
                Validator validator = new Validator();
                if(validator.isCampoVazio(edtMensagem.getText().toString())){
                    Toast.makeText(this, "Digite algo para poder enviar.", Toast.LENGTH_SHORT).show();
                    btnEnviar.requestFocus();
                }else{

                    //Mudar para POST
                    jsonTaskPost = new JSONTaskPost();

                    jsonTaskPost.execute(url);
                    btnEnviar.setEnabled(false);
                }
                break;
            case R.id.btnSairSuporte:
                finish();
                break;
        }
    }

    private JSONObject getJsonObject(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idAluno", idAluno);
            jsonObject.put("mensagem", edtMensagem.getText().toString());

            Log.e("params", jsonObject.toString());

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    private class JSONTaskPost extends AsyncTask<String, Void, String>{



        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject = getJsonObject();
            return Network.httpPost(jsonObject, strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            
            if(s != null){
                if(Boolean.valueOf(s)){
                    Toast.makeText(SuporteActivity.this, "Tudo certo!! Obrigado por entrar em contato. :)", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(SuporteActivity.this, "Erro na requisição de dados", Toast.LENGTH_SHORT).show();
                    btnEnviar.setEnabled(true);
                }

            }else{
                Toast.makeText(SuporteActivity.this, "ERRO AO SINCRONIZAR DADOS COM O SERVIDOR", Toast.LENGTH_SHORT).show();
                btnEnviar.setEnabled(true);
            }
        }
    }
}
