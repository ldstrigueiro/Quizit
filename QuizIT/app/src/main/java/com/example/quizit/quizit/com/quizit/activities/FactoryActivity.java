package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class FactoryActivity extends Activity implements View.OnClickListener {

    private EditText edtEnunciado;
    private EditText edtOpcao1;
    private EditText edtOpcao2;
    private EditText edtOpcao3;
    private EditText resposta;
    private EditText disc;
    private Aluno aluno;
    private Button btnConcluir;

    private JSONTaskPost jsonTaskPost = new JSONTaskPost();

    private String url = "http://apitccapp.azurewebsites.net/api/Pergunta_Avaliacao";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        edtEnunciado = (EditText) findViewById(R.id.edtEnunciadoFactory);
        edtOpcao1 = (EditText) findViewById(R.id.edtOpcao1Factory);
        edtOpcao2 = (EditText) findViewById(R.id.edtOpcao2Factory);
        edtOpcao3 = (EditText) findViewById(R.id.edtOpcao3Factory);
        resposta = (EditText) findViewById(R.id.edtRespostaFactory);
        disc = (EditText) findViewById(R.id.edtSugestaoDiscFactory);
        btnConcluir = (Button) findViewById(R.id.btnConcluirFactory);

        aluno = getIntent().getParcelableExtra("ObjAluno");

        btnConcluir.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConcluirFactory:
                if(edtEnunciado.getText().toString().isEmpty()){
                    Toast.makeText(this, "O campo ENUNCIADO está vazio.", Toast.LENGTH_SHORT).show();
                    edtEnunciado.requestFocus();
                }else if(resposta.getText().toString().isEmpty()){
                    Toast.makeText(this, "O campo RESPOSTA está vazio.", Toast.LENGTH_SHORT).show();
                    resposta.requestFocus();
                }else if(edtOpcao1.getText().toString().isEmpty()){
                    Toast.makeText(this, "O campo ALTERNATIVA 1 está vazio.", Toast.LENGTH_SHORT).show();
                    edtOpcao1.requestFocus();
                }else if(edtOpcao2.getText().toString().isEmpty()){
                    Toast.makeText(this, "O campo ALTERNATIVA 2 está vazio.", Toast.LENGTH_SHORT).show();
                    edtOpcao2.requestFocus();
                }else if(edtOpcao3.getText().toString().isEmpty()) {
                    Toast.makeText(this, "O campo ALTERNATIVA 3 está vazio.", Toast.LENGTH_SHORT).show();
                    edtOpcao3.requestFocus();
                }else if(disc.getText().toString().isEmpty()) {
                    Toast.makeText(this, "O campo DISCIPLINA está vazio.", Toast.LENGTH_SHORT).show();
                    disc.requestFocus();
                }else
                    jsonTaskPost.execute(url);
                break;
        }
    }

    private class JSONTaskPost extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(FactoryActivity.this, "Aguarde", "Enviando pergunta...");
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                //Popula o JSON com os dados dos campos ta tela de cadastro
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pergunta", edtEnunciado.getText().toString());
                jsonObject.put("resposta", resposta.getText().toString());
                jsonObject.put("opcao1", edtOpcao1.getText().toString());
                jsonObject.put("opcao2", edtOpcao2.getText().toString());
                jsonObject.put("opcao3", edtOpcao3.getText().toString());
                jsonObject.put("observacaoDisciplina", disc.getText().toString());
                jsonObject.put("idAluno", aluno.getIdAluno());

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
            progressDialog.dismiss();
            Toast.makeText(FactoryActivity.this, "Pergunta enviada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
