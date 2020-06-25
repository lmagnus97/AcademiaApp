package com.lucas.magnus.academia.model;

import java.util.Calendar;

public class MatriculaModalidade {

    private int codigoMatricula;
    private String modalidade;
    private String graduacao;
    private Calendar dataInicio;
    private Calendar dataFim;
    private String plano;
    private String aluno;
    private Double valorMensal;

    public MatriculaModalidade() {
    }

    public MatriculaModalidade(int codigoMatricula, String modalidade, String graduacao, Calendar dataInicio, Calendar dataFim, String plano, String aluno, Double valorMensal) {
        this.codigoMatricula = codigoMatricula;
        this.modalidade = modalidade;
        this.graduacao = graduacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.plano = plano;
        this.aluno = aluno;
        this.valorMensal = valorMensal;
    }

    public int getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(int codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public Double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(Double valorMensal) {
        this.valorMensal = valorMensal;
    }

    @Override
    public String toString() {
        return "MatriculaModalidade{" +
                "codigoMatricula=" + codigoMatricula +
                ", modalidade='" + modalidade + '\'' +
                ", graduacao='" + graduacao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", plano='" + plano + '\'' +
                ", aluno='" + aluno + '\'' +
                ", valorMensal=" + valorMensal +
                '}';
    }
}
