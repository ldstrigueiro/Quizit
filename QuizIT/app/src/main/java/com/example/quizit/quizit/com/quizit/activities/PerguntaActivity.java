package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;
import com.example.quizit.quizit.com.quizit.util.Network;

public class PerguntaActivity extends Activity implements View.OnClickListener {

    private TextView enunciado;
    private RadioGroup rgOpcoes;
    private RadioButton rbOpcao1;
    private RadioButton rbOpcao2;
    private RadioButton rbOpcao3;
    private RadioButton rbOpcao4;
    private RadioButton rbResposta;
    private Button btnEscolherPerg;
    private TextView area;
    private ConstraintLayout constraintLayout;
    private JSONTaskGet jsonTaskGet = new JSONTaskGet();

    private String urlPontuacao = "http://apitccapp.azurewebsites.net/Pergunta/getResultadoPergunta/";

    private Intent intent;

    private Pergunta pergunta;
    private Aluno aluno;

    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta);

        area = (TextView) findViewById(R.id.txtAreaPerg);
        enunciado = (TextView) findViewById(R.id.txtEnunciadoPerg);
        rgOpcoes = (RadioGroup) findViewById(R.id.rgOpcoesPerg);
        rbOpcao1 = (RadioButton) findViewById(R.id.rbOpcao1Perg);
        rbOpcao2 = (RadioButton) findViewById(R.id.rbOpcao2Perg);
        rbOpcao3 = (RadioButton) findViewById(R.id.rbOpcao3Perg);
        rbOpcao4 = (RadioButton) findViewById(R.id.rbOpcao4Perg);
        constraintLayout = (ConstraintLayout) findViewById(R.id.ConstLayoutPerg);
        btnEscolherPerg = (Button) findViewById(R.id.btnEscolherPerg);

        btnEscolherPerg.setOnClickListener(this);


        pergunta = getIntent().getParcelableExtra("ObjPergunta");
        aluno = getIntent().getParcelableExtra("ObjAluno");

        area.setText(pergunta.getArea());
        enunciado.setText(pergunta.getEnunciado());
        rbOpcao1.setText(pergunta.getOpcao1());
        rbOpcao2.setText(pergunta.getOpcao2());
        rbOpcao3.setText(pergunta.getOpcao3());
        rbOpcao4.setText(pergunta.getResposta());
        //Dar shuffle num ArrayList com as perguntas.

    }

    @Override
    public void onBackPressed() {

        //Cria um builder de um alertDialog para confirmar a saida da tela pelo botao back
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ÉOQ?");
        builder.setMessage("Deseja realmente sair?");

        //Opcao sim que volta de tela passando como extra o ObjAluno
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                intent = new Intent(PerguntaActivity.this, JogarRandomActivity.class);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEscolherPerg:
                int idResposta = rgOpcoes.getCheckedRadioButtonId();
                rbResposta = (RadioButton) findViewById(idResposta);
                if(idResposta != -1)
                    jsonTaskGet.execute(urlPontuacao+pergunta.getId()+"/"+aluno.getIdAluno()+"/"+rbResposta.getText()+"/"+pergunta.getIdArea());
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
            return Network.getEndereco(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            boolean isCorreto = Boolean.parseBoolean(s);
            if(isCorreto){
                Toast.makeText(PerguntaActivity.this, "Acertou", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(PerguntaActivity.this, "Errou", Toast.LENGTH_LONG).show();
            }
            intent = new Intent(PerguntaActivity.this, JogarRandomActivity.class);
            intent.putExtra("ObjAluno", aluno);
            startActivity(intent);
            finish();
        }
    }
}