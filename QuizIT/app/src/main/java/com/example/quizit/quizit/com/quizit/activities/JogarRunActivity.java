package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Area;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JogarRunActivity extends Activity implements View.OnClickListener {
    private Area area;
    private Spinner spinner;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String urlGetAreas = "http://apitccapp.azurewebsites.net/Pergunta/getIdNomeArea/";
    private Aluno aluno;
    private Button btnSelecionar;
    private Intent intent;
    private TextView txtExplicacao;
    private int modo;



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
            txtExplicacao.setText("Modo Single Run\n\n Neste modo, você responderá a CINCO perguntas da área selecionada acima. Terá duas tentativas extras");
        }else if(modo == 10){
            txtExplicacao.setText("Modo Single Run\n\n Neste modo, você responderá a DEZ perguntas da área selecionada acima. Terá tres tentativas extras");
        }else if(modo == -1){
            txtExplicacao.setText("ERROR -1");
        }


        String url = urlGetAreas+String.valueOf(aluno.getSemestre());
        jsonTaskGet.execute(url);
    }

    private ArrayList<Area> getPerguntaJSON(String json){
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
                area = (Area)spinner.getSelectedItem();
                Toast.makeText(this, ""+area.getNome(), Toast.LENGTH_SHORT).show();
                intent = new Intent(this, PerguntaActivity.class);

                break;
        }
    }



    private class JSONTaskGet extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return Network.getDados(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Area> listArea = getPerguntaJSON(s);


            ArrayAdapter areaAdapter = new ArrayAdapter<Area>(JogarRunActivity.this, android.R.layout.simple_list_item_1, listArea);
            spinner.setAdapter(areaAdapter);

            btnSelecionar = (Button) findViewById(R.id.btnSelecionaAreaRun);
            btnSelecionar.setOnClickListener(JogarRunActivity.this);


        }
    }
}
