package com.example.quizit.quizit.com.quizit.objetos;

public class Area {

    private int id;
    private String nome;


    public Area (){

    }

    public Area (int id, String nome){
        this.setId(id);
        this.setNome(nome);
    }

    @Override
    public String toString() {
        return this.nome;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
