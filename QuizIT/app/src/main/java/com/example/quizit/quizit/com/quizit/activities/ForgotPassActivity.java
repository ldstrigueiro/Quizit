package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.quizit.quizit.com.quizit.util.Mail;
import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.com.quizit.util.Validator;

public class ForgotPassActivity extends Activity implements View.OnClickListener {
    private Button btnEnviar;
    private EditText edtEmail;
    private EditText edtMatricula;
    private Validator validator = new Validator();
    private boolean checkEmail;
    private String emailSemPonto;
    private JSONTaskGet jsonTaskGet;
    private String url = "http://apitccapp.azurewebsites.net/Aluno/recuperaSenha/";

    AlertDialog.Builder dlg;
    Intent intent;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        edtEmail = (EditText) findViewById(R.id.edtEmailForgot);
        edtMatricula = (EditText) findViewById(R.id.edtMatriculaForgot);
        btnEnviar = (Button) findViewById(R.id.btnEnviarForgot);

        btnEnviar.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnEnviarForgot:
                Validator validator = new Validator();


                if(!validator.isEmailValido(edtEmail.getText().toString())){
                    Toast.makeText(this, "O campo está em branco ou o email está invalido", Toast.LENGTH_LONG).show();
                    edtEmail.requestFocus();
                }else if(validator.isCampoVazio(edtMatricula.getText().toString())) {
                    Toast.makeText(this, "O campo da matricula está em branco", Toast.LENGTH_LONG).show();
                    edtMatricula.requestFocus();
                }else{
                    emailSemPonto = edtEmail.getText().toString().replace(".", "-");
                    jsonTaskGet = new JSONTaskGet();
                    jsonTaskGet.execute(url + emailSemPonto + "/" + edtMatricula.getText().toString().toUpperCase());
                }
                break;
        }
    }


    private class JSONTaskGet extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ForgotPassActivity.this, "Aguarde", "Verificando dados...");
        }

        @Override
        protected String doInBackground(String... strings) {
            return Network.getDados(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            boolean isCorrect = Boolean.parseBoolean(s);

            progressDialog.dismiss();
            
            if(isCorrect){
                Toast.makeText(ForgotPassActivity.this, "Senha enviada com sucesso!!", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(ForgotPassActivity.this, "Email ou matricula não cadastrada no sistema.", Toast.LENGTH_LONG).show();
                edtEmail.requestFocus();
            }
        }
    }

   /* protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ForgotPassActivity.this, "Aguarde", "Enviando email");
    }
    private void enviarEmail() {
        if(isOnline()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Cria um objeto email e prepara ele para ser disparado
                    Mail m = new Mail("quizit.project@gmail.com", "michaelbinbows");
                    String[] toArr = {edtEmail.getText().toString()};
                    m.setTo(toArr);
                    m.setFrom("quizit.project@gmail.com");
                    m.setSubject("Recuperação de senha do app Quizit"); //Email de teste do seu app
                    m.setBody("Clique no link a seguir para criar uma nova senha: https://www.youtube.com/watch?v=dQw4w9WgXcQ");
                    try {
                        //m.addAttachment("pathDoAnexo");//anexo opcional
                        m.send(); //Pega o objeto populado e envia
                        progressDialog.dismiss(); //Mata o objeto que para a tela
                        //Continuar a jornada aqui para poder apresentar uma mensagem de sucesso
                    }
                    catch(RuntimeException rex){
                    }//erro ignorado
                    catch(Exception e) {
                        //tratar algum outro erro aqui
                    }
                    System.exit(0);
                }
            }
            ).start();
        }else{
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_LONG).show();
        }
    }*/

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