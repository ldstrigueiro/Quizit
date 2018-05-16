package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.ImagemPojo;
import com.example.quizit.quizit.com.quizit.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class AlteraAvatarActivity extends Activity implements View.OnClickListener {

    private ImageView imgAvatar;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private Button btnConcluir;

    private Aluno aluno;

    private JSONObject jsonObject;
    private JSONTaskPut jsonTaskPut;
    private String url = "http://apitccapp.azurewebsites.net/api/Aluno";

    private Intent intent;
    //Alterar caso acrescente mais imagens
    private final int MAX_IMAGE = 11;
    private int contaImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_avatar);

        imgAvatar = findViewById(R.id.imgAlteraAvatar);
        btnConcluir = findViewById(R.id.btnEscolherAvatar);
        btnPrevious = findViewById(R.id.btnPrevAvatar);
        btnNext = findViewById(R.id.btnNextAvatar);

        btnConcluir.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        aluno = getIntent().getParcelableExtra("ObjAluno");
        imgAvatar.setImageResource(ImagemPojo.idImagem[aluno.getAvatar()]);

        contaImg = aluno.getAvatar();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextAvatar:

                contaImg++;
                //Cria loop de imagens
                if(contaImg > MAX_IMAGE){//Alterar MAX_IMAGE acima do OnCreate
                    contaImg = 0;
                }
                imgAvatar.setImageResource(ImagemPojo.idImagem[contaImg]);
                break;
            case R.id.btnPrevAvatar:
                contaImg--;
                //Cria loop de imagens
                if(contaImg < 0){
                    contaImg = MAX_IMAGE;
                }
                imgAvatar.setImageResource(ImagemPojo.idImagem[contaImg]);
                break;
            case R.id.btnEscolherAvatar:
                jsonObject = obtemJSONObject(contaImg);
                if(jsonObject != null){
                    jsonTaskPut = new JSONTaskPut();
                    jsonTaskPut.execute(url);
                }
                break;
        }
    }

    private JSONObject obtemJSONObject (int avatar){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idAluno", aluno.getIdAluno());
            jsonObject.put("matricula", aluno.getMatricula());
            jsonObject.put("nome", aluno.getNome());
            jsonObject.put("email", aluno.getEmail());
            jsonObject.put("semestre", aluno.getSemestre());
            jsonObject.put("sexo", aluno.getSexo());
            jsonObject.put("senha", aluno.getSenha());
            jsonObject.put("curso", "Ciência da Computação");
            jsonObject.put("avatar", avatar);

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private class JSONTaskPut extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return Network.httpPut(jsonObject, url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            boolean isCorrect = Boolean.parseBoolean(s);

            if(isCorrect){
                aluno.setAvatar(contaImg);
                intent = new Intent(AlteraAvatarActivity.this, HomeActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(AlteraAvatarActivity.this, "ERRO AO SINCRONIZAR DADOS COM O SERVIDOR.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
