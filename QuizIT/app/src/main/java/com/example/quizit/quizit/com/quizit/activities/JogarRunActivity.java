package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

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

public class JogarRunActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Area area;
    private Spinner spinner;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();
    private String urlGetAreas = "http://apitccapp.azurewebsites.net/Pergunta/getIdNomeArea/";
    private Aluno aluno;
    private Button btnSelecionar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_run);
        spinner = (Spinner) findViewById(R.id.spinner_area);



        aluno = getIntent().getParcelableExtra("ObjAluno");
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


                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            spinner.setOnItemSelectedListener(JogarRunActivity.this);

            btnSelecionar = (Button) findViewById(R.id.btnSelecionaAreaRun);
            btnSelecionar.setOnClickListener(JogarRunActivity.this);


        }
    }
}
