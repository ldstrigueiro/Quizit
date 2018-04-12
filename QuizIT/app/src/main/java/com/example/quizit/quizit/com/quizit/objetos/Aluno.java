package com.example.quizit.quizit.com.quizit.objetos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas Dilan on 23/03/2018.
 */

public class Aluno implements Parcelable {

    private String nome;
    private String matricula;
    private String email;
    private int idAluno;
    private int semestre;
    private String sexo;
    private Integer pontuacao;
    private String curso;
    private String senha;


    public Aluno(String nome, String matricula, String email, int idAluno, int semestre, String sexo, Integer pontuacao, String curso, String senha){
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

    protected Aluno(Parcel in) {
        nome = in.readString();
        matricula = in.readString();
        email = in.readString();
        idAluno = in.readInt();
        semestre = in.readInt();
        sexo = in.readString();
        //if (in.readByte() == 0) {
            //pontuacao = null;
        //} else {
            pontuacao = in.readInt();
        //}
        curso = in.readString();
        senha = in.readString();
    }

    public static final Creator<Aluno> CREATOR = new Creator<Aluno>() {
        @Override
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }

        @Override
        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }
    };

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

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(matricula);
        parcel.writeString(email);
        parcel.writeInt(idAluno);
        parcel.writeInt(semestre);
        parcel.writeString(sexo);
        if (pontuacao == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(pontuacao);
        }
        parcel.writeString(curso);
        parcel.writeString(senha);
    }
}
