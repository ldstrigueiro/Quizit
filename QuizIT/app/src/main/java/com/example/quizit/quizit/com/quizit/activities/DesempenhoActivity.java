package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.adapters.DesempenhoAdapter;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.AreaRatio;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DesempenhoActivity extends Activity {

    private Aluno aluno;
    private ListView listView;
    private List<AreaRatio> listAreaRatio;
    private DesempenhoAdapter desempenhoAdapter;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String urlRatio = "http://apitccapp.azurewebsites.net/Pontuacao/getRatio/";
    private String urlAcertoErro = "http://apitccapp.azurewebsites.net/Pontuacao/getDesempenhoArea/";

    boolean jaPassou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desempenho);

        listView = findViewById(R.id.lstDesempenho);
        listAreaRatio = new ArrayList<>();

        aluno = getIntent().getParcelableExtra("ObjAluno");

        jsonTaskGet.execute(urlRatio +aluno.getIdAluno());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private List<AreaRatio> getJsonRatio(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<AreaRatio> listArea = new ArrayList<>();
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                AreaRatio area = new AreaRatio();
                jsonObject = jsonArray.getJSONObject(i);
                area.setArea(jsonObject.getString("nomeArea"));
                area.setRatio(jsonObject.getDouble("mediaPontuacao"));
                listArea.add(area);
            }
            return listArea;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<AreaRatio> getJsonAcertoErro (String json, List<AreaRatio> listArea){
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                listArea.get(i).setAcertos(jsonObject.getInt("acertos"));
                listArea.get(i).setErros(jsonObject.getInt("erros"));
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
            if(!jaPassou){

                if(s != null){
                    jaPassou = true;
                    listAreaRatio = getJsonRatio(s);

                    //desempenhoAdapter = new DesempenhoAdapter(DesempenhoActivity.this, listAreaRatio);
                    //listView.setAdapter(desempenhoAdapter);

                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(urlAcertoErro+aluno.getIdAluno());
                }else{
                    Toast.makeText(DesempenhoActivity.this, "ERRO AO OBTER DADOS DO SERVIDOR", Toast.LENGTH_SHORT).show();
                }
            }else{
                listAreaRatio = getJsonAcertoErro(s, listAreaRatio);

                desempenhoAdapter = new DesempenhoAdapter(DesempenhoActivity.this, listAreaRatio);
                listView.setAdapter(desempenhoAdapter);
            }

        }
    }
}
