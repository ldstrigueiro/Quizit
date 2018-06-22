package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.quizit.quizit.com.quizit.objetos.ImagemPojo;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.com.quizit.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Activity implements View.OnClickListener {

    private TextView txtNome;
    private TextView txtSemestre;
    private ImageView imgPerfil;
    private ImageButton imgBtnConfig;
    private ImageButton imgBtnFactory;
    private ImageButton imgBtnRanking;
    private ImageButton imgBtnDesempenho;

    private Button btnJogarRandom;
    private Button btnModoRun;

    private String [] listaOpcoesPersonalizada;
    private ArrayAdapter<String> adapter;
    private String urlPostFeedback = "http://apitccapp.azurewebsites.net/api/Suporte";
    private Intent intent;
    private Aluno aluno;
    private Util util = new Util();
    private AlertDialog.Builder dlg;
    private JSONTaskPost jsonTaskPost;

    private SharedPreferences sp;

    private AlertDialog alertDialog;


    //========== ONCREATE & ONCLICK ============
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Referências
        txtNome = (TextView) findViewById(R.id.txtNomeHome);
        txtSemestre = (TextView) findViewById(R.id.txtSemestreHome);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfilHome);
        imgBtnConfig = (ImageButton) findViewById(R.id.btnConfigHome);
        imgBtnFactory = (ImageButton) findViewById(R.id.btnFactoryHome);
        imgBtnRanking = findViewById(R.id.btnRankingHome);
        imgBtnDesempenho = findViewById(R.id.btnDesempenhoHome);



        btnJogarRandom = (Button) findViewById(R.id.btnJogarRandomHome);
        btnModoRun = findViewById(R.id.btnJogarRunHome);


        //Pega o aluno
        aluno = getIntent().getParcelableExtra("ObjAluno");

        if(aluno != null) {
            txtNome.setText(aluno.getNome());
            txtSemestre.setText(String.valueOf(aluno.getSemestre())+"°Semestre");

            imgPerfil.setOnClickListener(this);
            imgPerfil.setImageResource(ImagemPojo.idImagem[aluno.getAvatar()]);

            //Botão Jogar
            btnJogarRandom.setOnClickListener(this);

            //Botão Modo Run
            btnModoRun.setOnClickListener(this);

            //Botão fábrica de perguntas
            imgBtnFactory.setOnClickListener(this);

            //Botão configuração
            imgBtnConfig.setOnClickListener(this);

            imgBtnRanking.setOnClickListener(this);

            imgBtnDesempenho.setOnClickListener(this);

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //alertDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPerfilHome:
                goToAlteraAvatarActivity ();
                break;

            case R.id.btnConfigHome:
                mostraMenuConfig();
                break;

            case R.id.btnFactoryHome:
                goToFactoryActivity();
                break;

            case R.id.btnJogarRandomHome:
                goToJogarRandomActivity();
                break;

            case R.id.btnJogarRunHome:
                mostraModosRun();

                break;
            case R.id.btnRankingHome:
                goToRankingActivity();

                break;
            case R.id.btnDesempenhoHome:
                goToDesempenhoActivity();

                break;

        }
    }

    private void goToDesempenhoActivity() {
        intent = new Intent(this, DesempenhoActivity.class);
        intent.putExtra("ObjAluno", aluno);
        startActivity(intent);
        
    }

    private void goToAlteraAvatarActivity (){
        intent = new Intent(this, AlteraAvatarActivity.class);
        intent.putExtra("ObjAluno", aluno);
        startActivity(intent);
        finish();
    }

    private void goToFactoryActivity(){
        intent = new Intent(this, FactoryActivity.class);
        intent.putExtra("ObjAluno", aluno);
        startActivity(intent);

    }

    private void goToJogarRandomActivity(){
        intent = new Intent(this, JogarRandomActivity.class);
        intent.putExtra("Modo", 0);
        intent.putExtra("ObjAluno", aluno);
        intent.putExtra("Feedback", "Boa Sorte!!");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void goToRankingActivity(){
        intent = new Intent(this, RankingActivity.class);
        intent.putExtra("Semestre", aluno.getSemestre());
        startActivity(intent);
    }

    private void goToSuporteActivity(){
        intent = new Intent(this, SuporteActivity.class);
        intent.putExtra("ObjAluno", aluno);
        startActivity(intent);
        finish();

    }

    //============= METODOS ==============

    private void mostraMenuConfig(){
        //Popula a lista de acordo com o strings.xml
        listaOpcoesPersonalizada = getResources().getStringArray(R.array.listaItensConfig);

        //Instancia o adapter associando ele ao xml personalizado e à lista populada acima
        adapter = new ArrayAdapter<>(this, R.layout.config_home, listaOpcoesPersonalizada);

        //Cria o Dialog Builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Configura o builder
        builder.setTitle(getResources().getString(R.string.titleConfig));
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {

            //which é a posição onde foi clicada nas opçoes apresentadas
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case 0:
                        //Editar Perfil
                        intent = new Intent(HomeActivity.this, EditarPerfilActivity.class);
                        intent.putExtra("ObjAluno", aluno);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        goToSuporteActivity();
                        break;
                    case 2:
                        //Informações Activity (?) Ou outra mensagem dialog msm? (mata esse dialog e mostra um novo dialog
                        //com as formulas
                        break;
                    case 3:
                        doSair();
                        break;

                }
            }
        });

        //Cria e executa o builder
        builder.create();
        builder.show();
    }

    private void doSair(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ÉOQ?");
        builder.setMessage("Deseja realmente sair?");

        //Opcao sim que volta de tela passando como extra o ObjAluno
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                sp = getSharedPreferences("login",MODE_PRIVATE);
                sp.edit().putBoolean("logged",false).apply();
                startActivity(intent);
                finish();
            }
        });

        //Opcao nao que continua na activity
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void doIntent(int modo){
        intent = new Intent(this, JogarRunActivity.class);
        intent.putExtra("ObjAluno", aluno);
        intent.putExtra("Modo", modo);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                        doIntent(1);
                        //overridePendingTransition(R.anim.from_middle, R.anim.to_middle); Usar na tela de feedback de acerto
                        break;
                    case 1:
                        //5 Run
                        doIntent(5);
                        break;
                    case 2:
                        //10 Run
                        doIntent(10);
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
    private class JSONTaskPost extends AsyncTask<String, Void, String> {

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

                return Network.httpPost(jsonObject, urlPostFeedback);
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
