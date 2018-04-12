package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.util.Util;
import com.example.quizit.quizit.com.quizit.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements View.OnClickListener {

    //=============== Variáveis Globais =================
    private TextView txtForgot;
    private EditText edtMatricula;
    private EditText edtSenha;
    private TextView txtCadastrar;
    private Intent intent;
    private Button btnLogar;
    private String endereco;

<<<<<<< HEAD:QuizIT/app/src/main/java/com/example/quizit/quizit/MainActivity.java
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private Aluno aluno;
    private Validator validator = new Validator();
    private AlertDialog.Builder dlg;
=======
    JSONTaskGet jsonTaskGet;
    Aluno aluno;
    Validator validator = new Validator();
    Util util = new Util();
    AlertDialog.Builder dlg;
>>>>>>> 8139282299bdc64ea3616798dcfff58f455fff75:QuizIT/app/src/main/java/com/example/quizit/quizit/com/quizit/activities/MainActivity.java


    //============ onCreate & onClick ===============
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCadastrar = (TextView) findViewById(R.id.txtCadastrar);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        txtForgot = (TextView) findViewById(R.id.txtForgot);

        txtCadastrar.setOnClickListener(this);
        btnLogar.setOnClickListener(this);
        txtForgot.setOnClickListener(this);

    }

    //@Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.txtCadastrar:
                intent = new Intent(this, CadastroActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogar:
                edtMatricula = (EditText) findViewById(R.id.edtLogin);
                edtSenha = (EditText) findViewById(R.id.edtSenha);

                //se os campos não estiveres vazios ele entra...
                if(!validaCampo(edtMatricula.getText().toString(), edtSenha.getText().toString())){
<<<<<<< HEAD:QuizIT/app/src/main/java/com/example/quizit/quizit/MainActivity.java
                    endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/"+edtMatricula.getText().toString().toUpperCase()+"/"+edtSenha.getText().toString();
                    //endereco = "http://apitccapp.azurewebsites.net/Aluno/getbymatricula/UC14100729";
                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(endereco);


=======
                    //endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/"+edtMatricula.getText().toString().toUpperCase()+"/"+edtSenha.getText().toString();
                    endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/UC14100729/fodao2";
                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(endereco);
>>>>>>> 8139282299bdc64ea3616798dcfff58f455fff75:QuizIT/app/src/main/java/com/example/quizit/quizit/com/quizit/activities/MainActivity.java
                }
                   break;
            case R.id.txtForgot:
                intent = new Intent(this, ForgotPassActivity.class);
                startActivity(intent);
                break;
        }
    }

    //================== MÉTODOS ==================

    //Sintese: Popula o aluno a partir do JSON
    //Entrada:  Json
    //Saída: Aluno populado
    public Aluno getAlunoJson (String json){

        Aluno aluno = new Aluno();
        try {
            JSONObject jsonObj = new JSONObject(json);

            aluno.setIdAluno(jsonObj.getInt("idAluno"));
            aluno.setNome(jsonObj.getString("nome"));
            aluno.setEmail(jsonObj.getString("email"));
            aluno.setSemestre(jsonObj.getInt("semestre"));
            aluno.setSexo(jsonObj.getString("sexo"));
            //aluno.setPontuacao(jsonObj.getInt("idPontuacao"));
            aluno.setCurso(jsonObj.getString("curso"));
            aluno.setSenha(jsonObj.getString("senha"));
            aluno.setMatricula(jsonObj.getString("matricula"));
            aluno.setAvatar(jsonObj.getInt("avatar"));

            return aluno;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }




    //Objetivo: Validar campos de matricula e senha da página de login
    private boolean validaCampo(String matricula, String senha){
        boolean res;
        dlg = new AlertDialog.Builder(this);


        if(res = validator.isCampoVazio(matricula)) {
            edtMatricula.requestFocus();
            util.mensagem("Opa!", "Campo matrícula vazio!", "Ok", dlg);
        }else
            if(res = validator.isCampoVazio(senha)) {
                edtSenha.requestFocus();
                util.mensagem("Opa!", "Campo senha vazio!", "Ok", dlg);
            }

        return res;
    }

    //============= JSON TASKS ===============

<<<<<<< HEAD:QuizIT/app/src/main/java/com/example/quizit/quizit/MainActivity.java


    private class JSONTaskGet extends AsyncTask<String, String, String>{
=======
    public class JSONTaskGet extends AsyncTask<String, String, String>{
>>>>>>> 8139282299bdc64ea3616798dcfff58f455fff75:QuizIT/app/src/main/java/com/example/quizit/quizit/com/quizit/activities/MainActivity.java

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Verificando Credenciais");

        }

        @Override
        protected String doInBackground(String... params) {
            return Network.getEndereco(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            aluno = getAlunoJson(s);

            if(aluno == null){ //Valida se o aluno é nulo e trata ele com o aviso antes de passar pra HomeActivity
                dlg = new AlertDialog.Builder(MainActivity.this);
                util.mensagem("Opa!", "Matricula/Senha não cadastrados", "Ok", dlg);
                progressDialog.dismiss();
            }else{
                intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("ObjAluno", aluno);
                progressDialog.dismiss();
                startActivity(intent);
            }
        }
    }
}
