package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;

public class RunFeedbackActivity extends Activity {
    private TextView txtResultado;
    private TextView txtQuestionsLeft;
    private TextView txtVidas;

    private Aluno aluno;
    private int vidas;
    private int modo;
    private String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_feedback);

        txtResultado = findViewById(R.id.txtResultadoRunFB);
        txtQuestionsLeft = findViewById(R.id.txtQuestionLeftRunFB);
        txtVidas = findViewById(R.id.txtVidasRunFB);

        vidas = getIntent().getIntExtra("Vidas", -2);
        modo = getIntent().getIntExtra("Modo", -1);
        resultado = getIntent().getStringExtra("TxtResultado");
        aluno = getIntent().getParcelableExtra("ObjAluno");

        txtResultado.setText(resultado);
        txtQuestionsLeft.setText(String.valueOf(modo));
        txtVidas.setText(String.valueOf(vidas));

    }
}
