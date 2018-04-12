package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroSucessoActivity extends Activity {

    private Intent intent = getIntent();
    private TextView txtCadastroSucess;
    private String json;
    private JSONTaskPost jsonTaskPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_sucesso);

        txtCadastroSucess = findViewById(R.id.txtCadastroSucess);

        json = intent.getStringExtra("json");

        jsonTaskPost.execute(json);

    }

    public class JSONTaskPost extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        protected void onPostExecute(String result) {
            txtCadastroSucess.setText("Cadastro realizado com sucesso!");
            finish();
        }
    }

}
