package com.example.quizit.quizit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

    private TextView txtNomeTeste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNomeTeste = (TextView) findViewById(R.id.txtNomeTeste);
        Aluno aluno = getIntent().getParcelableExtra("ObjAluno");

        if(aluno != null){
            txtNomeTeste.setText(aluno.getNome());
        }else{
            txtNomeTeste.setText("null");
        }

    }
}
