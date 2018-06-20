package com.example.quizit.quizit.com.quizit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizit.quizit.R;
import com.example.quizit.quizit.com.quizit.objetos.Aluno;
import com.example.quizit.quizit.com.quizit.util.Network;
import com.example.quizit.quizit.com.quizit.util.Validator;

import org.json.JSONException;
import org.json.JSONObject;

public class EditarPerfilActivity extends Activity implements View.OnClickListener {
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtNovaSenha;
    private EditText edtConfirmarSenha;
    private EditText edtSenhaAtual;
    private Button btnConfirmar;
    private Button btnSair;
    private Aluno aluno;
    private Boolean isPasswordCorrect = true;

    private JSONObject jsonObject;

    private JSONTaskPut jsonTaskPut;

    private Intent intent;

    private String url = "http://apitccapp.azurewebsites.net/api/Aluno";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        edtNome = findViewById(R.id.edtNomeEditarPerfil);
        edtEmail = findViewById(R.id.edtEmailEditarPerfil);
        edtNovaSenha = findViewById(R.id.edtNovaSenhaEditarPerfil);
        edtConfirmarSenha = findViewById(R.id.edtConfimarSenhaEdtPerfil);
        edtSenhaAtual = findViewById(R.id.edtSenhaAtualEditarPerfil);
        btnConfirmar = findViewById(R.id.btnConfirmarEditarPerfil);
        btnSair = findViewById(R.id.btnSairEditarPerfil);

        aluno = getIntent().getParcelableExtra("ObjAluno");

        edtNome.setText(aluno.getNome());
        edtEmail.setText(aluno.getEmail());

        btnConfirmar.setOnClickListener(this);
        btnSair.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
          switch(v.getId()){
              case R.id.btnConfirmarEditarPerfil:
                  boolean isChanged = false;
                  jsonObject = obtemJSONObject();

                  if(jsonObject != null){
                      if(isPasswordCorrect){
                          try {
                              if(!(aluno.getNome().equals(jsonObject.getString("nome"))))
                                  isChanged = true;
                              if(!(aluno.getEmail().equals(jsonObject.getString("email"))))
                                  isChanged = true;
                              if(!(aluno.getSenha().equals(jsonObject.getString("senha"))))
                                  isChanged = true;

                              if(isChanged){
                                  jsonTaskPut = new JSONTaskPut();
                                  jsonTaskPut.execute(url);
                              }else{
                                  Toast.makeText(this, "NENHUM DADO FOI ALTERADO", Toast.LENGTH_LONG).show();

                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }else{
                          edtNovaSenha.requestFocus();
                          isPasswordCorrect = true;
                      }
                  }else
                      Toast.makeText(this, "ERRO AO OBTER RESPOSTA DO SERVIDOR. TENTE MAIS TARDE", Toast.LENGTH_LONG).show();
                  break;
              case R.id.btnSairEditarPerfil:
                  intent = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                  intent.putExtra("ObjAluno", aluno);
                  startActivity(intent);
                  finish();
                  break;
          }

    }

    private JSONObject obtemJSONObject (){
        JSONObject jsonObject = new JSONObject();
        Validator validator = new Validator();
        //Se a senha do campo senha atual bater com a do objAluno
        try {
            if(edtSenhaAtual.getText().toString().equals(aluno.getSenha())){

                jsonObject.put("idAluno", aluno.getIdAluno());
                jsonObject.put("matricula", aluno.getMatricula());
                jsonObject.put("nome", aluno.getNome());
                jsonObject.put("email", aluno.getEmail());
                jsonObject.put("semestre", aluno.getSemestre());
                jsonObject.put("sexo", aluno.getSexo());
                jsonObject.put("senha", aluno.getSenha());
                jsonObject.put("curso", "Ciência da Computação");
                jsonObject.put("avatar", aluno.getAvatar());

                //Se o campo nao estiver vazio, troca a informação do JSONObject

                //NOME
                if(!validator.isCampoVazio(edtNome.getText().toString())){
                    jsonObject.put("nome", edtNome.getText().toString());
                }

                //EMAIL
                if(!validator.isCampoVazio(edtEmail.getText().toString())){
                    if(validator.isEmailValido(edtEmail.getText().toString()))
                        jsonObject.put("email", edtEmail.getText().toString());
                    else{
                        Toast.makeText(this, "Email invalido!", Toast.LENGTH_LONG).show();
                        edtEmail.requestFocus();
                    }
                }

                //NOVA SENHA
                if(!validator.isCampoVazio(edtNovaSenha.getText().toString())){
                    isPasswordCorrect = false;
                    //Se a nova senha conferir com a confirmação, troca a informação do JSONObject
                    if(edtNovaSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())){

                        if(validator.isValidPassword(edtNovaSenha.getText().toString())){
                            jsonObject.put("senha", edtNovaSenha.getText().toString());
                            isPasswordCorrect = true;
                        }else{
                            Toast.makeText(this, "A senha deverá ser composta por letras maiuscula e minuscula, numeros e ter pelo menos 8 caracteres", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this, "As senhas nao conferem!!", Toast.LENGTH_LONG).show();
                        edtNovaSenha.requestFocus();
                    }
                }

                //CONFIRMAR SENHA
                if(!validator.isCampoVazio(edtConfirmarSenha.getText().toString())){
                    //Verifica se o campo nova senha está vazio
                    if(validator.isCampoVazio(edtNovaSenha.getText().toString())){
                        Toast.makeText(this, "Por favor, confirme a nova senha.", Toast.LENGTH_LONG).show();
                        edtConfirmarSenha.requestFocus();
                        isPasswordCorrect = false;
                    }
                }
                return jsonObject;
            }else{
                Toast.makeText(this, "Senha atual incorreta!!", Toast.LENGTH_LONG).show();
                edtSenhaAtual.requestFocus();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    private class JSONTaskPut extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return Network.httpPut(jsonObject, url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            boolean isCorrect = Boolean.parseBoolean(s);
            
            if(isCorrect){
                Toast.makeText(EditarPerfilActivity.this, "DADOS ALTERADOS COM SUCESSO", Toast.LENGTH_LONG).show();
                try {
                    aluno.setNome(jsonObject.getString("nome"));
                    aluno.setEmail(jsonObject.getString("email"));
                    aluno.setSenha(jsonObject.getString("senha"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                intent.putExtra("ObjAluno", aluno);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(EditarPerfilActivity.this, "ERRO AO SINCRONIZAR DADOS COM O SERVIDOR", Toast.LENGTH_LONG).show();
            }
        }
    }
}
