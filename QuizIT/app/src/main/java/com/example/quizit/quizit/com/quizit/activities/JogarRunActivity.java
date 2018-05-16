package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Area;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JogarRunActivity extends Activity implements View.OnClickListener {
    private Area area;
    private Spinner spinner;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String urlGetAreas = "http://apitccapp.azurewebsites.net/Pergunta/getIdNomeArea/";
    private String urlPergunta = "http://apitccapp.azurewebsites.net/Pergunta/getPerguntaRandomArea/";
    private Aluno aluno;
    private Button btnSelecionar;
    private Intent intent;
    private TextView txtExplicacao;

    private int modo;
    private int vidas;
    //Para reutilizar o AsyncTask verificando se for true, ja populou o spinner.
    private boolean jaPassou = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_run);
        spinner = (Spinner) findViewById(R.id.spinner_area);
        txtExplicacao = (TextView) findViewById(R.id.txtExplicaRun);

        modo = getIntent().getIntExtra("Modo", -1);
        aluno = getIntent().getParcelableExtra("ObjAluno");

        if(modo == 1){
            txtExplicacao.setText("Modo Single Run\n\n Neste modo, você responderá a apenas uma pergunta da área selecionada acima.");
        }else if(modo == 5){
            txtExplicacao.setText("Modo 5 Run\n\n Neste modo, você responderá a CINCO perguntas da área selecionada acima. Terá duas tentativas extras");
        }else if(modo == 10){
            txtExplicacao.setText("Modo 10 Run\n\n Neste modo, você responderá a DEZ perguntas da área selecionada acima. Terá tres tentativas extras");
        }else if(modo == -1){
            txtExplicacao.setText("ERROR -1");
        }


        String url = urlGetAreas+String.valueOf(aluno.getSemestre());
        jsonTaskGet.execute(url);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelecionaAreaRun:
                jsonTaskGet = new JSONTaskGet();
                area = (Area)spinner.getSelectedItem();
                Toast.makeText(this, ""+area.getNome(), Toast.LENGTH_SHORT).show();
                String url = urlPergunta+aluno.getSemestre()+"/"+area.getId();
                jsonTaskGet.execute(url);
                break;
        }
    }



    private class JSONTaskGet extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) { return Network.httpGet(strings[0]);   }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!jaPassou){
                if(s != null){
                    jaPassou = true;
                    ArrayList<Area> listArea = getAreaJSON(s);
                    ArrayAdapter areaAdapter = new ArrayAdapter<Area>(JogarRunActivity.this, android.R.layout.simple_list_item_1, listArea);
                    spinner.setAdapter(areaAdapter);
                    btnSelecionar = (Button) findViewById(R.id.btnSelecionaAreaRun);
                    btnSelecionar.setOnClickListener(JogarRunActivity.this);

                }else{
                    Toast.makeText(JogarRunActivity.this, "Erro ao obter dados do servidor, tente mais tarde.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }else{
                if(s != null){
                    Pergunta pergunta = Pergunta.getPerguntaJSON(s);
                    intent = new Intent(JogarRunActivity.this, PerguntaActivity.class);
                    intent.putExtra("Modo", modo);
                    if(modo == 1)
                        vidas = 1;
                    else if (modo == 5)
                        vidas = 2;
                    else if (modo == 10)
                        vidas = 3;
                    intent.putExtra("Vidas", vidas);
                    intent.putExtra("ObjPergunta", pergunta);
                    intent.putExtra("ObjAluno", aluno);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(JogarRunActivity.this, "Erro ao obter dados do servidor, tente mais tarde.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }
    }
}
