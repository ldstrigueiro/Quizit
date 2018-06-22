package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.adapters.RankingAdapter;
import com.example.quizit.quizit.com.quizit.objetos.RankingObject;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {

    private ListView listView;
    private List<RankingObject> listRankingObject;
    private RankingAdapter rankingAdapter;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String url = "http://apitccapp.azurewebsites.net/Ranking/getRankingGeral/";
    private int semestre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listView = findViewById(R.id.listRanking);
        listRankingObject = new ArrayList<>();

        semestre = getIntent().getIntExtra("Semestre", -1);

        jsonTaskGet.execute(url+String.valueOf(semestre));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private List<RankingObject> getJsonRanking(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<RankingObject> listArea = new ArrayList<>();
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                RankingObject rankingObject = new RankingObject();
                jsonObject = jsonArray.getJSONObject(i);
                rankingObject.setAvatar(jsonObject.getInt("avatar"));
                rankingObject.setNome(jsonObject.getString("nome"));
                rankingObject.setPontos(jsonObject.getString("pontos"));
                rankingObject.setRanking(jsonObject.getString("rank"));

                //Trunca a string caso seja maior que 15 caracteres

                if(rankingObject.getNome().length() > 15){
                    String s = rankingObject.getNome().substring(0, 15);
                    rankingObject.setNome(s+"...");
                }


                listArea.add(rankingObject);
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
                listRankingObject = getJsonRanking(s);
                rankingAdapter = new RankingAdapter(RankingActivity.this, listRankingObject);
                listView.setAdapter(rankingAdapter);
            }else{
                Toast.makeText(RankingActivity.this, "ERRO AO OBTER DADOS DO SERVIDOR", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
