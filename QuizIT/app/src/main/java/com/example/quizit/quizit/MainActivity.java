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

;


public class MainActivity extends Activity{

    private TextView txtForgot;
    private EditText edtMatricula;
    private EditText edtSenha;
    private TextView txtCadastrar;
    private Intent intentHome;
    private Button btnLogar;
    private String endereco;
    JSONTask jsonTask;
    Aluno aluno = new Aluno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCadastrar = (TextView) findViewById(R.id.txtCadastrar);
        edtMatricula = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);



        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                endereco = "http://apitccapp.azurewebsites.net/Aluno/autenticaAluno/"+edtMatricula.getText().toString()+ "/"+edtSenha.getText().toString();
                jsonTask.execute(endereco);

                //getAlunoJson(jsonTask.get());
                txtForgot = (TextView) findViewById(R.id.txtForgot);
                txtForgot.setText(aluno.getNome().toString());

            }
        });
        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentHome = new Intent(MainActivity.this, Act_Cadastro.class);
                startActivity(intentHome);
            }
        });





    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            return Network.getEndereco(params[0]);
            /*HttpURLConnection conn = null;
            BufferedReader reader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL (params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();


                InputStream stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();

                String linha = null;
                while((linha = reader.readLine()) != null){
                    buffer.append(linha);
                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(conn != null)
                    conn.disconnect();
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getAlunoJson(s);
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
    /*public void onClick(View view) {

        switch (view.getId()){

            case R.id.txtCadastrar:
                intentHome = new Intent(this, Act_Cadastro.class);
                startActivity(intentHome);
                break;
            case R.id.btnLogar:

                jsonTask.execute(endereco);
                try {
                    getAlunoJson(jsonTask.get());
                    txtForgot = (TextView) findViewById(R.id.txtForgot);
                    txtForgot.setText(aluno.getNome().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                break;
        }
    }*/
}
