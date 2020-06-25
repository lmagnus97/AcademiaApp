package com.lucas.magnus.academia.model;

import java.util.Calendar;

public class Matricula {

    private int codigoMatricula;
    private Integer codigoAluno;
    private Calendar dataMatricula;
    private Integer diaVencimento;
    private Calendar dataEncerramento;
    private String aluno;

    public Matricula() {
    }

    public Matricula(int codigoMatricula, int codigoAluno, Calendar dataMatricula, int diaVencimento, Calendar dataEncerramento, String aluno) {
        this.codigoMatricula = codigoMatricula;
        this.codigoAluno = codigoAluno;
        this.dataMatricula = dataMatricula;
        this.diaVencimento = diaVencimento;
        this.dataEncerramento = dataEncerramento;
        this.aluno = aluno;
    }

    public int getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(int codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public Calendar getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Calendar dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Calendar getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Calendar dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public void setCodigoAluno(Integer codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "codigoMatricula=" + codigoMatricula +
                ", codigoAluno=" + codigoAluno +
                ", dataMatricula=" + dataMatricula +
                ", diaVencimento=" + diaVencimento +
                ", dataEncerramento=" + dataEncerramento +
                ", aluno='" + aluno + '\'' +
                '}';
    }
}
