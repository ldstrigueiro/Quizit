package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Area;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FactoryActivity extends Activity implements View.OnClickListener {

    private EditText edtEnunciado;
    private EditText edtOpcao1;
    private EditText edtOpcao2;
    private EditText edtOpcao3;
    private EditText resposta;
    private Spinner spinnerDisc;
    private Aluno aluno;
    private Button btnConcluir;

    private JSONTaskPost jsonTaskPost = new JSONTaskPost();
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();

    private String urlPost = "http://apitccapp.azurewebsites.net/api/Pergunta_Avaliacao";
    private String urlGet = "http://apitccapp.azurewebsites.net/api/Area";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        edtEnunciado = (EditText) findViewById(R.id.edtEnunciadoFactory);
        edtOpcao1 = (EditText) findViewById(R.id.edtOpcao1Factory);
        edtOpcao2 = (EditText) findViewById(R.id.edtOpcao2Factory);
        edtOpcao3 = (EditText) findViewById(R.id.edtOpcao3Factory);
        resposta = (EditText) findViewById(R.id.edtRespostaFactory);
        spinnerDisc = (Spinner) findViewById(R.id.spinner_area_factory);
        btnConcluir = (Button) findViewById(R.id.btnConcluirFactory);

        aluno = getIntent().getParcelableExtra("ObjAluno");


        jsonTaskGet.execute(urlGet);



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
                }else
                    jsonTaskPost.execute(urlPost);
                break;
        }
    }

    private ArrayList<Area> getAreaJSON(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<Area> listArea = new ArrayList<>();
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                Area area = new Area();
                jsonObject = jsonArray.getJSONObject(i);
                area.setId(jsonObject.getInt("idArea"));
                area.setNome(jsonObject.getString("nomeArea"));
                listArea.add(area);
            }
            return listArea;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private class JSONTaskGet extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return Network.httpGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){
                ArrayList<Area> listArea = getAreaJSON(s);
                ArrayAdapter areaAdapter = new ArrayAdapter<>(FactoryActivity.this, android.R.layout.simple_list_item_1, listArea);
                spinnerDisc.setAdapter(areaAdapter);
                btnConcluir.setOnClickListener(FactoryActivity.this);
            }
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
                Area area = (Area) spinnerDisc.getSelectedItem();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pergunta", edtEnunciado.getText().toString());
                jsonObject.put("resposta", resposta.getText().toString());
                jsonObject.put("opcao1", edtOpcao1.getText().toString());
                jsonObject.put("opcao2", edtOpcao2.getText().toString());
                jsonObject.put("opcao3", edtOpcao3.getText().toString());
                jsonObject.put("observacaoDisciplina", area.getNome());
                jsonObject.put("idAluno", aluno.getIdAluno());

                Log.e("params", jsonObject.toString());

                //Obtem a conexao, transforma o JSON em URL e envia pro AZURE para popular o banco.
                return Network.httpPost(jsonObject, urlPost);


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
