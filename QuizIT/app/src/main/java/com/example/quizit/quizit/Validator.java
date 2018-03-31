package com.example.quizit.quizit;

import android.app.AlertDialog;
import android.text.TextUtils;

public class Validator {
    //=============== Métodos ===============
    public boolean isCampoVazio(String string){
        boolean resultado = (TextUtils.isEmpty(string) || string.trim().isEmpty());
        return resultado;
    }

    public void mensagemErroLogin(String cabecalho, String mensagem, String msgButton, AlertDialog.Builder contexto){
        AlertDialog.Builder dlg = contexto;
        dlg.setTitle(cabecalho);
        dlg.setMessage(mensagem);
        dlg.setNeutralButton(msgButton, null);
        dlg.show();
    }
}
