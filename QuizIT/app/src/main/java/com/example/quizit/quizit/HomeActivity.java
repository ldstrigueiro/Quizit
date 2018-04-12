package com.example.quizit.quizit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity implements View.OnClickListener {

    private TextView txtNome;
    private TextView txtSemestre;
    private TextView txtPontuacao;
    private ImageView imgPerfil;
    private Intent intent;
    private Aluno aluno;
    private Validator validator = new Validator();
    private AlertDialog.Builder dlg;
    private ImageButton btnFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNome = (TextView) findViewById(R.id.txtNomeHome);
        txtPontuacao = (TextView) findViewById(R.id.txtPontuacaoHome);
        txtSemestre = (TextView) findViewById(R.id.txtSemestreHome);
        imgPerfil = (ImageView) findViewById(R.id.imgPerfilHome);
        btnFactory = (ImageButton) findViewById(R.id.btnFactoryHome);

        btnFactory.setOnClickListener(this);
        aluno = getIntent().getParcelableExtra("ObjAluno");

        if(aluno != null){
            txtNome.setText(aluno.getNome().toString());
            txtSemestre.setText(String.valueOf(aluno.getSemestre()));
            txtPontuacao.setText(String.valueOf(aluno.getPontuacao()));

            imgPerfil.setOnClickListener(this);

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

            case R.id.btnFactoryHome:

                intent = new Intent(this, FactoryActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                break;
        }
    }
}
