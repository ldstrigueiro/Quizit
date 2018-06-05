package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;
import com.example.quizit.quizit.com.quizit.util.Network;

public class RunFeedbackActivity extends Activity implements View.OnClickListener {
    private TextView txtResultado;
    private TextView txtQuestionsLeft;
    private TextView txtVidas;

    private int idArea;
    private Aluno aluno;
    private int vidas;
    private int left;
    private String resultado;

    private Button btnProxima;
    private Button btnSair;

    private Intent intent;
    private AlertDialog alertDialog;

    private JSONTaskGet jsonTaskGet;

    private String url = "http://apitccapp.azurewebsites.net/Pergunta/getPerguntaRandomArea/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_feedback);

        txtResultado = findViewById(R.id.txtResultadoRunFB);
        txtQuestionsLeft = findViewById(R.id.txtQuestionLeftRunFB);
        txtVidas = findViewById(R.id.txtVidasRunFB);
        btnProxima = findViewById(R.id.btnProximaRunFB);
        btnSair = findViewById(R.id.btnSairRunFB);

        btnProxima.setOnClickListener(this);
        btnSair.setOnClickListener(this);

        vidas = getIntent().getIntExtra("Vidas", -2);
        left = getIntent().getIntExtra("Modo", -1);
        resultado = getIntent().getStringExtra("TxtResultado");
        aluno = getIntent().getParcelableExtra("ObjAluno");
        idArea = getIntent().getIntExtra("IdArea", -1);

        txtResultado.setText(resultado);

        if(left != 0){

            txtQuestionsLeft.setText("Faltam "+String.valueOf(left)+ " perguntas");
            txtVidas.setText("Ainda tem "+String.valueOf(vidas) + " tentativas");
        }else{
            txtQuestionsLeft.setText("Você concluiu todas as perguntas!");
            txtVidas.setText(" ");
            btnProxima.setText("Sair");
            btnSair.setEnabled(false);
            btnSair.setVisibility(View.INVISIBLE);
        }

        if(txtResultado.getText().toString().equals("GAME OVER")){
            txtQuestionsLeft.setText("Você respondeu todas as perguntas!! Até a próxima.");
            txtVidas.setText(" ");
            btnProxima.setText("Sair");
            btnSair.setEnabled(false);
            btnSair.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProximaRunFB:
                if(left != 0 && vidas != 0){
                    String link = url+aluno.getSemestre()+"/"+idArea;
                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(link);
                }else{
                    intent = new Intent(RunFeedbackActivity.this, HomeActivity.class);
                    intent.putExtra("ObjAluno", aluno);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.btnSairRunFB:
                //Cria um builder de um alertDialog para confirmar a saida da tela

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ÉOQ?");
                builder.setMessage("Deseja realmente sair?");

                //Opcao sim que volta de tela passando como extra o ObjAluno
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        intent = new Intent(RunFeedbackActivity.this, HomeActivity.class);
                        intent.putExtra("ObjAluno", aluno);
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
                break;

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
            Pergunta pergunta = Pergunta.getPerguntaJSON(s);

            intent = new Intent(RunFeedbackActivity.this, PerguntaActivity.class);
            intent.putExtra("ObjPergunta", pergunta);
            intent.putExtra("ObjAluno", aluno);
            intent.putExtra("Vidas", vidas);
            intent.putExtra("Modo", left);
            startActivity(intent);
            finish();

        }
    }
}
