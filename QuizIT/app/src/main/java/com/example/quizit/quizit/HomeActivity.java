package com.example.quizit.quizit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity implements View.OnClickListener {

    private TextView txtNome;
    private TextView txtSemestre;
    private TextView txtPontuacao;
    private ImageView imgPerfil;
    private Intent intent;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNome = (TextView) findViewById(R.id.txtNomeHome);
        txtPontuacao = (TextView) findViewById(R.id.txtPontuacaoHome);
        txtSemestre = (TextView) findViewById(R.id.txtSemestreHome);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfilHome);



        aluno = getIntent().getParcelableExtra("ObjAluno");

        if(aluno != null){
            txtNome.setText(aluno.getNome().toString());
            txtSemestre.setText(String.valueOf(aluno.getSemestre()));
            txtPontuacao.setText(String.valueOf(aluno.getPontuacao()));

            imgPerfil.setOnClickListener(this);


        }else{
            txtNome.setText("null");

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPerfilHome:
                intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                break;
        }
    }
}
