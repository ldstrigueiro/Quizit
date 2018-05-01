package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Pergunta;

public class PerguntaActivity extends Activity {

    private TextView enunciado;
    private TextView opcao1;
    private TextView opcao2;
    private TextView opcao3;
    private TextView opcao4;

    private Pergunta pergunta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta);

        enunciado = (TextView) findViewById(R.id.txtEnunciadoPerg);
        opcao1 = (TextView) findViewById(R.id.txtOpcao1);
        opcao2 = (TextView) findViewById(R.id.txtOpcao2);
        opcao3 = (TextView) findViewById(R.id.txtOpcao3);
        opcao4 = (TextView) findViewById(R.id.txtOpcao4);


        pergunta = getIntent().getParcelableExtra("ObjPergunta");

        enunciado.setText(pergunta.getEnunciado());
        opcao1.setText(pergunta.getOpcao1());
        opcao2.setText(pergunta.getOpcao2());
        opcao3.setText(pergunta.getOpcao3());
        opcao4.setText(pergunta.getResposta());

    }
}
