package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.com.quizit.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Activity implements View.OnClickListener {

    private TextView txtNome;
    private TextView txtSemestre;
    private TextView txtPontuacao;
    private ImageView imgPerfil;
    private ImageButton imgBtnConfig;
    private ImageButton imgBtnFactory;
    private Button btnJogar;
    private Button btnModoRun;

    private String [] listaOpcoesPersonalizada;
    private ArrayAdapter<String> adapter;
    private String urlPostFeedback = "http://apitccapp.azurewebsites.net/api/Suporte";
    private Intent intent;
    private Aluno aluno;
    private Util util = new Util();
    private AlertDialog.Builder dlg;
    private JSONTaskPost jsonTaskPost;

    private Button btnJogarRandom;



    //========== ONCREATE & ONCLICK ============
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Referências
        txtNome = (TextView) findViewById(R.id.txtNomeHome);
        txtPontuacao = (TextView) findViewById(R.id.txtPontuacaoHome);
        txtSemestre = (TextView) findViewById(R.id.txtSemestreHome);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfilHome);
        imgBtnConfig = (ImageButton) findViewById(R.id.btnConfigHome);
        imgBtnFactory = (ImageButton) findViewById(R.id.btnFactoryHome);


        btnJogarRandom = (Button) findViewById(R.id.btnJogarRandomHome);

        btnJogar = findViewById(R.id.btnJogarRandomHome);
        btnModoRun = findViewById(R.id.btnJogarRunHome);


        //Pega o aluno
        aluno = getIntent().getParcelableExtra("ObjAluno");

        if(aluno != null){
            txtNome.setText(aluno.getNome());
            txtSemestre.setText(String.valueOf(aluno.getSemestre()));
            txtPontuacao.setText(String.valueOf(aluno.getPontuacao()));

            imgPerfil.setOnClickListener(this);

        }

        //Botão Jogar
        btnJogar.setOnClickListener(this);

        //Botão Modo Run
        btnModoRun.setOnClickListener(this);

        //Botão fábrica de perguntas
        imgBtnFactory.setOnClickListener(this);

        //Botão configuração
        imgBtnConfig.setOnClickListener(this);
        btnJogarRandom.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPerfilHome:
                intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                break;

            case R.id.btnConfigHome:
                mostraMenuConfig();
                break;

            case R.id.btnFactoryHome:
                intent = new Intent(this, FactoryActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                break;

            case R.id.btnJogarRandomHome:
                intent = new Intent(this, JogarRandomActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            case R.id.btnJogarRunHome:
                mostraModosRun();

                break;

        }
    }

    //============= METODOS ==============

    private void mostraMenuConfig(){
        //Popula a lista de acordo com o strings.xml
        listaOpcoesPersonalizada = getResources().getStringArray(R.array.listaItensConfig);

        //Instancia o adapter associando ele ao xml personalizado e à lista populada acima
        adapter = new ArrayAdapter<>(this, R.layout.config_home, listaOpcoesPersonalizada);

        //Cria o Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Configura o builder
        builder.setTitle(getResources().getString(R.string.titleConfig));
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {

            //which é a posição onde foi clicada nas opçoes apresentadas
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case 0:
                        //Editar Perfil
                        break;
                    case 1:
                        //Feedback activity
                        break;
                    case 2:
                        //Informações Activity (?) Ou outra mensagem dialog msm? (mata esse dialog e mostra um novo dialog
                        //com as formulas
                        break;
                    case 3:
                        //Convidar amigos (Envia email)
                        break;

                }
            }
        });

        //Cria e executa o builder
        builder.create();
        builder.show();
    }

    private void mostraModosRun(){
        //Popula a lista de acordo com o strings.xml
        listaOpcoesPersonalizada = getResources().getStringArray(R.array.listaItensRun);

        //Instancia o adapter associando ele ao xml personalizado e à lista populada acima
        adapter = new ArrayAdapter<>(this, R.layout.config_home, listaOpcoesPersonalizada);

        //Cria o Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Configura o builder
        builder.setTitle(getResources().getString(R.string.titleModoRun));
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {

            //which é a posição onde foi clicada nas opçoes apresentadas
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case 0:
                        //Single Run
                        break;
                    case 1:
                        //5 Run
                        break;
                    case 2:
                        //10 Run
                        break;


                }
            }
        });

        //Cria e executa o builder
        builder.create();
        builder.show();
    }


    //PEGAR ESSE JSON TASK E COLOCAR ELE NA FEEDBACK ACTIVITY

    //=========== JSON TASKS ================
    public class JSONTaskPost extends AsyncTask<String, Void, String> {

        /*ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(HomeActivity.this, "Aguarde", "Enviando seu feedback...");
        }*/

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("idAluno", aluno.getIdAluno());
                //jsonObject.put("mensagem", edtTxtFeedback.getText().toString());

                Log.e("params", jsonObject.toString());

                return Network.postCadastro(jsonObject, urlPostFeedback);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(HomeActivity.this,"Obrigado pelo feedback.", Toast.LENGTH_LONG).show();
        }
    }

}
