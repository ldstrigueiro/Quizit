package com.example.quizit.quizit.com.quizit.objetos;

public class RankingObject {
    private String nome;
    private String pontos;
    private int avatar;
    private String ranking;

    public RankingObject(String nome, String pontos, int avatar, String ranking) {
        this.nome = nome;
        this.pontos = pontos;
        this.avatar = avatar;
        this.ranking = ranking;
    }

    public RankingObject() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }





}
