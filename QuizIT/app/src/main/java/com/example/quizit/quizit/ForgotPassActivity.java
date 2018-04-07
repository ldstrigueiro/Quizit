package com.example.quizit.quizit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassActivity extends Activity implements View.OnClickListener {

    private Button btnEnviar;
    private EditText edtEmail;
    Validator validator;
    AlertDialog.Builder dlg;
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
         //final String email = edtEmail.getText().toString();

        if(isOnline()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Mail m = new Mail("quizit.project@gmail.com", "michaelbinbows");

                    String[] toArr = {edtEmail.getText().toString()};
                    m.setTo(toArr);

                    m.setFrom("quizit.project@gmail.com");
                    m.setSubject("Recuperação de senha do app Quizit"); //Email de teste do seu app
                    m.setBody("Clique no link a seguir para criar uma nova senha: https://www.youtube.com/watch?v=dQw4w9WgXcQ");

                    try {
                        //m.addAttachment("pathDoAnexo");//anexo opcional
                        if(m.send()){
                            //Toast.makeText(ForgotPassActivity.this, "Email de recuperação enviado com sucesso.", Toast.LENGTH_LONG);
                            validator.mensagemErro("Opa!", "Email de recuperação enviado com sucesso", "Ok", dlg);

                        }else{
                            //Toast.makeText(ForgotPassActivity.this, "Erro ao enviar email. Por favor, contate a equipe de suporte ou algum professor.", Toast.LENGTH_LONG);
                            validator.mensagemErro("Opa!", "Erro ao enviar email. Por favor, contate a equipe de suporte ou algum professor.", "Ok", dlg);

                        }
                    }
                    catch(RuntimeException rex){

                    }//erro ignorado
                    catch(Exception e) {
                        //tratar algum outro erro aqui
                        //validator.mensagemErro("Opa!", "Exception", "Ok", dlg);
                    }

                    finish();

                }
            }).start();
        }else{
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isOnline() {
        try {
            ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conn.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
