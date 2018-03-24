package com.example.quizit.quizit;

/**
 * Created by Lucas Dilan on 23/03/2018.
 */

public class Aluno {

    private String nome;
    private String matricula;
    private String email;
    private int idAluno;
    private int semestre;
    private String sexo;
    private Double pontuacao;
    private String curso;
    private String senha;


    public Aluno(String nome, String matricula, String email, int idAluno, int semestre, String sexo, Double pontuacao, String curso, String senha){
        this.setNome(nome);
        this.setCurso(curso);
        this.setEmail(email);
        this.setIdAluno(idAluno);
        this.setMatricula(matricula);
        this.setPontuacao(pontuacao);
        this.setSenha(senha);
        this.setSexo(sexo);
        this.setSemestre(semestre);
    }

    public Aluno (){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    //Getter and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
