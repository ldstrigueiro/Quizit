package com.example.quizit.quizit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassActivity extends Activity implements View.OnClickListener {

    private Button btnEnviar;
    private EditText edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        btnEnviar = (Button) findViewById(R.id.btnEnviarForgot);
        edtEmail = (EditText) findViewById(R.id.edtEmailForgot);

        btnEnviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnEnviarForgot:
                enviarEmail();

                break;
        }
    }

    private void enviarEmail() {

        
    }
}
