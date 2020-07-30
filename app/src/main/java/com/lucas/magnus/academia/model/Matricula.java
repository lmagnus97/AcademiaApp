package com.lucas.magnus.academia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Matricula implements Serializable {

    private Integer codigoMatricula;
    @SerializedName("data_matricula")
    private Long dataMatricula;
    @SerializedName("dia_vencimento")
    private Integer diaVencimento;
    @SerializedName("data_encerramento")
    private Long dataEncerramento;
    private Aluno aluno;
    @SerializedName("id_usuario")
    private Integer idUsuario;
    private Integer idNuvem;
    @SerializedName("id_aluno")
    private Integer idAluno;

    public Matricula() {
    }

    public Matricula(int codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Matricula(int codigoMatricula, Integer idAluno, Long dataMatricula, int diaVencimento, Long dataEncerramento, Aluno aluno) {
        this.codigoMatricula = codigoMatricula;
        this.idAluno = idAluno;
        this.dataMatricula = dataMatricula;
        this.diaVencimento = diaVencimento;
        this.dataEncerramento = dataEncerramento;
        this.aluno = aluno;
    }

    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Long getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Long dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Long getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Long dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(Integer idNuvem) {
        this.idNuvem = idNuvem;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "codigoMatricula=" + codigoMatricula +
                ", codigoAluno=" + idAluno +
                ", dataMatricula=" + dataMatricula +
                ", diaVencimento=" + diaVencimento +
                ", dataEncerramento=" + dataEncerramento +
                ", aluno='" + aluno + '\'' +
                '}';
    }
}
