package com.example.quizit.quizit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //=============== Variáveis Globais =================
    private Button btn_Cadastrar;

    //Bloco dos campos de cadastro
    private EditText edt_Nome;
    private EditText edt_Email;
    private EditText edt_Senha;
    private EditText edt_Matricula;
    private EditText edt_Semestre;

    //Bloco de variaveis relacionadas ao spinner da opcao sexo.
    private Spinner spin_sexo;
    private String spin_valor;
    private String[] sexo = {"M","F"};

    private String urlCadastro = "http://apitccapp.azurewebsites.net/api/Aluno";
    private String urlVerifica = "http://apitccapp.azurewebsites.net/Aluno/autenticaAlunoMatricula/";
    private boolean isMatriculaValida;
    Validator validator = new Validator();
    AlertDialog.Builder dlg;
    Intent intent;
    JSONTaskGet jsonTaskGet;
    JSONTaskPost jsonTaskPost;



    //============ onCreate & onClick ===============
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Spinner (dropdownlist) da opção sexo
        spin_sexo = findViewById(R.id.spinner_Sexo);
        spin_sexo.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexo );
        spin_sexo.setAdapter(spinAdapter);

        //Botão cadastrar
        btn_Cadastrar = findViewById(R.id.btn_Cadastrar);
        btn_Cadastrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        edt_Nome = (EditText) findViewById(R.id.edt_Nome);
        edt_Email = (EditText) findViewById(R.id.edt_Email);
        edt_Senha = (EditText) findViewById(R.id.edt_Senha);
        edt_Matricula = (EditText) findViewById(R.id.edt_Matricula);
        edt_Semestre = (EditText) findViewById(R.id.edt_Semestre);

        //Validar os campos aqui
        if(!isCamposValidado(edt_Nome.getText().toString(), edt_Email.getText().toString(),
                edt_Senha.getText().toString(), edt_Matricula.getText().toString().toUpperCase(),
                edt_Semestre.getText().toString()) ) {

            //Valida se a matricula ja existe
            jsonTaskGet = new JSONTaskGet();
            jsonTaskGet.execute(urlVerifica + edt_Matricula.getText().toString());

            if(isMatriculaValida){
                jsonTaskPost.execute(urlCadastro);
            }

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                spin_valor = sexo[0];
                break;
            case 1:
                spin_valor = sexo[1];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {  }

    //=============== Métodos ================
    //Objetivo: Validar campos de matricula e senha da página de login
    private boolean isCamposValidado(String nome, String email, String senha, String matricula, String semestre){
        boolean res;
        dlg = new AlertDialog.Builder(this);
        int semestreInt = strToInt(semestre, 0);


        if(res = validator.isCampoVazio(nome)) {
            edt_Nome.requestFocus();
            validator.mensagemErroLogin("Nome inválido!",
                    "O nome não pode ser vazio.",
                    "Ok", dlg);
        }else
            if(res = !validator.isEmailValido(email)) {
                edt_Email.requestFocus();
                validator.mensagemErroLogin("Email inválido!",
                        "Confira se o email inserido está correto.",
                        "Ok", dlg);
            }else
                if(res = validator.isCampoVazio(senha)) {
                    edt_Senha.requestFocus();
                    validator.mensagemErroLogin("Senha inválida!",
                            "A senha não pode ser vazia.",
                            "Ok", dlg);
                }else
                    if(res = (validator.isCampoVazio(matricula) || !validator.isPadraoMatricula(matricula))){
                        edt_Matricula.requestFocus();
                        validator.mensagemErroLogin("Matrícula inválida!",
                                "A matrícula está vazia ou não está no padrão correto. Ex: UC12345678",
                                "Ok", dlg);
                    }else
                        if(res = (validator.isCampoVazio(semestre) || (semestreInt <= 0 || semestreInt > 8))) {
                            edt_Semestre.requestFocus();
                            validator.mensagemErroLogin("Semestre inválido!",
                                    "O semestre tem que estár entre 1 e 8.",
                                    "Ok", dlg);
                        }
        return res;
    }

    public int strToInt(String valor, int padrao)
    {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        }
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }

    //================== JSONTasks ===================
    public class JSONTaskPost extends AsyncTask<String, Void, String>{

        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Popula o JSON com os dados dos campos ta tela de cadastro
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("nome", edt_Nome.getText().toString());
                jsonObject.put("email", edt_Email.getText().toString());
                jsonObject.put("senha", edt_Senha.getText().toString());
                jsonObject.put("matricula", edt_Matricula.getText().toString().toUpperCase());
                jsonObject.put("semestre", edt_Semestre.getText());
                jsonObject.put("curso", "Ciência da Computação");
                jsonObject.put("sexo", spin_valor.toString());

                Log.e("params", jsonObject.toString());

                //Obtem a conexao, transforma o JSON em URL e envia pro AZURE para popular o banco.
               return Network.postCadastro(jsonObject, urlCadastro);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    public class JSONTaskGet extends AsyncTask<String, String, String>{

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CadastroActivity.this, "Aguarde", "Cadastrando...");
        }

        @Override
        protected String doInBackground(String... params) { //  Retorna o resultado do json passado pelo parametro params[0] em
            return Network.getMatriculaRepetida(params[0]); //  forma de string.
        }

        @Override
        protected void onPostExecute(String s) { //  String s = resultado do json. Se for nulo (sem conexão pra consumir o json)
            super.onPostExecute(s);              //  ele retorna do doInBackground uma string null

            if(s != null){
                int isMatriculaRepetida = Integer.parseInt(s);

                if(isMatriculaRepetida == 1){
                    edt_Matricula.requestFocus();
                    validator.mensagemErroLogin("Erro!", "Matrícula já cadastrada no sistema!", "Ok", dlg);
                }else{
                    Toast.makeText(CadastroActivity.this,"Cadastro Realizado com sucesso!!!", Toast.LENGTH_LONG).show();

                    new JSONTaskPost().execute();
                    intent = new Intent(CadastroActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }else{
                validator.mensagemErroLogin("Opa!",
                        "Parece que você está sem conexão...",
                        "Tentar novamente", dlg);
            }
            progressDialog.dismiss();
        }
    }

}
