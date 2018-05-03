package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.R;

public class PerfilActivity extends Activity {

    private Aluno aluno;
    private TextView txtNome;
    private TextView txtSemestre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNome = (TextView) findViewById(R.id.txtNomePerfil);
        txtSemestre = (TextView) findViewById(R.id.txtSemestrePerfil);

        aluno = getIntent().getParcelableExtra("ObjAluno");

        txtNome.setText(aluno.getNome().toString());
        txtSemestre.setText(String.valueOf(aluno.getSemestre()));


    }
}
