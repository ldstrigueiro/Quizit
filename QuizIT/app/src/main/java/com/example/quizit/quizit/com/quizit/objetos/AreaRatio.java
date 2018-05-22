package com.example.quizit.quizit.com.quizit.objetos;

public class AreaRatio {
    private String area;
    private Double ratio;
    private int acertos;
    private int erros;

    public AreaRatio(String area, Double ratio, int acertos, int erros) {
        this.area = area;
        this.ratio = ratio;
        this.acertos = acertos;
        this.erros = erros;
    }

    public AreaRatio (){

    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }


}
