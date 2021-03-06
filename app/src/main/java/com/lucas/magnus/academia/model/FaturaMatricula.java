package com.lucas.magnus.academia.model;

import java.util.Calendar;

public class FaturaMatricula {

    private int codigoMatricula;
    private Calendar dataVencimento;
    private Double valor;
    private Long dataPagamento;
    private Long dataCancelamento;
    private String aluno;

    public FaturaMatricula() {
    }

    public FaturaMatricula(int codigoMatricula, Calendar dataVencimento, Double valor, Long dataPagamento, Long dataCancelamento, String aluno) {
        this.codigoMatricula = codigoMatricula;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.dataCancelamento = dataCancelamento;
        this.aluno = aluno;
    }

    public int getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(int codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Calendar getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Calendar dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Long dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Long getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Long dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }
}
