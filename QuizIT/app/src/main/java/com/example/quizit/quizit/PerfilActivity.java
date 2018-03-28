package com.example.quizit.quizit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends Activity {

    private Aluno aluno;
    private TextView txtNome;
    private TextView txtPontuacao;
    private TextView txtSemestre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNome = (TextView) findViewById(R.id.txtNomePerfil);
        txtPontuacao = (TextView) findViewById(R.id.txtPontuacaoPerfil);
        txtSemestre = (TextView) findViewById(R.id.txtSemestrePerfil);

        aluno = getIntent().getParcelableExtra("ObjAluno");

        txtNome.setText(aluno.getNome().toString());
        txtPontuacao.setText(String.valueOf(aluno.getPontuacao()));
        txtSemestre.setText(String.valueOf(aluno.getSemestre()));


    }
}
