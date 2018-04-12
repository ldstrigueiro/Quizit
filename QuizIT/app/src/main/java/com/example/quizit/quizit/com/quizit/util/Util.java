package com.example.quizit.quizit.com.quizit.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.quizit.quizit.com.quizit.activities.HomeActivity;

public class Util {

    private AlertDialog alerta;

    //Objetivo: Apresentar uma mensagem padrão
    //Entrada: cabeçalho, mensagem, texto do botão, de onde vem (contexto)
    public void mensagem(String cabecalho, String mensagem, String msgButton, AlertDialog.Builder contexto){
        //Cria o gerador do AlertDialog e recebe o contexto (Activity)
        AlertDialog.Builder builder = contexto;

        //Monta a mensagem
        builder.setTitle(cabecalho);
        builder.setMessage(mensagem);
        builder.setNeutralButton(msgButton, null);

        //Cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
