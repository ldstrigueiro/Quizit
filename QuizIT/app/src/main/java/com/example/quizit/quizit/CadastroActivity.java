package com.example.quizit.quizit;

import android.app.Activity;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class CadastroActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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

    private String url = "http://apitccapp.azurewebsites.net/api/Aluno";



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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        edt_Nome = (EditText) findViewById(R.id.edt_Nome);
        edt_Email = (EditText) findViewById(R.id.edt_Email);
        edt_Senha = (EditText) findViewById(R.id.edt_Senha);
        edt_Matricula = (EditText) findViewById(R.id.edt_Matricula);
        edt_Semestre = (EditText) findViewById(R.id.edt_Semestre);

        new JSONTaskPost().execute();

        Toast.makeText(this,"Cadastro Realizado com sucesso!!!", Toast.LENGTH_LONG).show();

    }

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
               return Network.postCadastro(jsonObject, url);


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

}
