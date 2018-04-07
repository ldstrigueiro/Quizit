package com.example.quizit.quizit;

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

    JSONTaskGet jsonTaskGet;
    Aluno aluno;
    Validator validator = new Validator();
    AlertDialog.Builder dlg;


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

                //Transoforma o UC da matricula pra maiusculo


                //se os campos não estiveres vazios ele entra...
                if(!validaCampo(edtMatricula.getText().toString(), edtSenha.getText().toString())){
                    endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/"+edtMatricula.getText().toString().toUpperCase()+"/"+edtSenha.getText().toString();
                    //endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/UC14100729/tchecao";
                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(endereco);
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

            aluno.setNome(jsonObj.getString("nome"));
            aluno.setMatricula(jsonObj.getString("matricula"));
            aluno.setEmail(jsonObj.getString("email"));
            aluno.setIdAluno(jsonObj.getInt("idAluno"));
            aluno.setSemestre(jsonObj.getInt("semestre"));
            aluno.setSexo(jsonObj.getString("sexo"));
            aluno.setPontuacao(jsonObj.getDouble("pontuacao"));
            aluno.setSenha(jsonObj.getString("senha"));
            aluno.setCurso(jsonObj.getString("curso"));

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
            validator.mensagemErro("Opa!", "Campo matrícula vazio!", "Ok", dlg);
        }else
            if(res = validator.isCampoVazio(senha)) {
                edtSenha.requestFocus();
                validator.mensagemErro("Opa!", "Campo senha vazio!", "Ok", dlg);
            }

        return res;
    }

    //============= JSON TASKS ===============

    public class JSONTaskPost extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }
    }

    public class JSONTaskGet extends AsyncTask<String, String, String>{

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
                validator.mensagemErro("Opa!", "Matricula/Senha não cadastrados", "Ok", dlg);
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
