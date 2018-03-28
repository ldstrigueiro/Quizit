package com.example.quizit.quizit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

;import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView txtForgot;
    private EditText edtMatricula;
    private EditText edtSenha;
    private TextView txtCadastrar;
    private Intent intent;
    private Button btnLogar;
    private String endereco;
    JSONTask jsonTask;
    Aluno aluno = null;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCadastrar = (TextView) findViewById(R.id.txtCadastrar);
        edtMatricula = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        txtForgot = (TextView) findViewById(R.id.txtForgot);

        txtCadastrar.setOnClickListener(this);
        btnLogar.setOnClickListener(this);

    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            return Network.getEndereco(params[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getAlunoJson(s);
            intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("ObjAluno", aluno);
            startActivity(intent);
        }
    }
    //Sintese: Popula o aluno a partir do JSON
    //Entrada:  Json
    //Saída: Aluno populado
    public void getAlunoJson (String json){


        try {
            JSONObject jsonObj = new JSONObject(json);

            aluno.setNome(jsonObj.getString("nome"));
            aluno.setMatricula(jsonObj.getString("matricula"));
            aluno.setEmail(jsonObj.getString("email"));
            aluno.setIdAluno(jsonObj.getInt("idAluno"));
            aluno.setSemestre(jsonObj.getInt("semestre"));
            aluno.setSexo(jsonObj.getString("sexo"));
            aluno.setPontuacao(jsonObj.getDouble("pontuacao"));
            aluno.setSenha(jsonObj.getString("senha"));
            aluno.setCurso(jsonObj.getString("curso"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    //@Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.txtCadastrar:
                intent = new Intent(this, Act_Cadastro.class);
                startActivity(intent);
                break;
            case R.id.btnLogar:
                endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/UC1400729/tchecao";
                jsonTask = new JSONTask();
                jsonTask.execute(endereco);

                break;
        }
    }
}
