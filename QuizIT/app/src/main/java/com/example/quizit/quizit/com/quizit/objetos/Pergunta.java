package com.example.quizit.quizit.com.quizit.objetos;

public class Pergunta {

    private String enunciado;
    private String opcao1;
    private String opcao2;
    private String opcao3;
    private String respostas;
    private String sugestaoDisc;
    private int id;
    private String area;

    private int idArea;

    public Pergunta(){

    }


    //Construtor com sugestao para a fabrica de perguntas

    public Pergunta(String enunciado, String opcao1, String opcao2, String opcao3, String respostas, String sugestaoDisc) {
        this.enunciado = enunciado;
        this.opcao1 = opcao1;
        this.opcao2 = opcao2;
        this.opcao3 = opcao3;
        this.respostas = respostas;
        this.sugestaoDisc = sugestaoDisc;

    }
    //Construtor sem sugestao para o jogo em si

    public Pergunta(String enunciado, String opcao1, String opcao2, String opcao3, String respostas, int id, String area, int idArea) {
        this.enunciado = enunciado;
        this.opcao1 = opcao1;
        this.opcao2 = opcao2;
        this.opcao3 = opcao3;
        this.respostas = respostas;
        this.id = id;
        this.area = area;
        this.idArea = idArea;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getId() { return id;    }

    public void setId(int id) { this.id = id;    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getOpcao1() {
        return opcao1;
    }

    public void setOpcao1(String opcao1) {
        this.opcao1 = opcao1;
    }

    public String getOpcao2() {
        return opcao2;
    }

    public void setOpcao2(String opcao2) {
        this.opcao2 = opcao2;
    }

    public String getOpcao3() {
        return opcao3;
    }

    public void setOpcao3(String opcao3) {
        this.opcao3 = opcao3;
    }

    public String getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas = respostas;
    }

    public String getSugestaoDisc() {
        return sugestaoDisc;
    }

    public void setSugestaoDisc(String sugestaoDisc) {
        this.sugestaoDisc = sugestaoDisc;
    }



}
