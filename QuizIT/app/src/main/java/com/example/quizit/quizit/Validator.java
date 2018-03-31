package com.example.quizit.quizit;

import android.app.AlertDialog;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    //=============== Métodos ===============
    //Objetivo: Valida se o campo está vazio
    //Entrada: String inserida no EditText
    //Saida: boolean. True: Campo vazio || False: Campo não vazio
    public boolean isCampoVazio(String string){
        return (TextUtils.isEmpty(string) || string.trim().isEmpty());
    }

    //Objetivo: Validar se o padrão da matricula segue o exemplo "UC12345678"
    //Entrada: Matricula inserida no EditText
    //Saida: boolean. True: está no padrão
    public boolean isPadraoMatricula(String string){
        try{
            String parts[] = string.split("C"); //Separa a matricula onde tem o C
            String UC = parts[0]; //U
            String numeros = parts[1]; //14101906
            boolean isNum = isInt(numeros);

            return (UC.equalsIgnoreCase("U") && isNum == true);
        }catch (Exception e){
            return false;
        }

    }

    //Objetivo: Validar se o padrão da matricula segue o exemplo "UC12345678"
    //Entrada: Matricula inserida no EditText
    //Saida: boolean. True: está no padrão
    public void mensagemErroLogin(String cabecalho, String mensagem, String msgButton, AlertDialog.Builder contexto){
        AlertDialog.Builder dlg = contexto;
        dlg.setTitle(cabecalho);
        dlg.setMessage(mensagem);
        dlg.setNeutralButton(msgButton, null);
        dlg.show();
    }

    //Objetivo: Validar se a string é composta apenas por numeros
    //Entrada: Parte numeral da matricula inserida no EditText
    //Saida: boolean. True: é numeral
    public boolean isInt(String v) {
        /* Verifica se um numero é inteiro ou não */
        try {
            Integer.parseInt(v);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Objetivo: Valida se um email é valido
    //Entrada: String de um email
    //Saida: Boolean. True: email é valido
    public boolean isEmailValido (String email){
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches())
                isEmailIdValid = true;
        }
        return isEmailIdValid;
    }
}
