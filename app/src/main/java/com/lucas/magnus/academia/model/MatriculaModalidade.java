package com.lucas.magnus.academia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatriculaModalidade implements Serializable {

    private Matricula matricula;
    private Modalidade modalidade;
    private Graduacao graduacao;
    @SerializedName("data_inicio")
    private Long dataInicio;
    @SerializedName("data_fim")
    private Long dataFim;
    private Plano plano;
    @SerializedName("id_usuario")
    private Integer idUsuario;
    @SerializedName("id_matricula")
    private Integer idMatricula;
    @SerializedName("id_modalidade")
    private Integer idModalidade;
    @SerializedName("id_graduacao")
    private Integer idGraduacao;
    @SerializedName("id_plano")
    private Integer idPlano;
    private Integer idNuvem;

    public MatriculaModalidade() {
    }

    public MatriculaModalidade(Matricula matricula, Modalidade modalidade, Graduacao graduacao, Long dataInicio, Long dataFim, Plano plano) {
        this.matricula = matricula;
        this.modalidade = modalidade;
        this.graduacao = graduacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.plano = plano;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Graduacao getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(Graduacao graduacao) {
        this.graduacao = graduacao;
    }

    public Long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Long getDataFim() {
        return dataFim;
    }

    public void setDataFim(Long dataFim) {
        this.dataFim = dataFim;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Integer getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Integer idModalidade) {
        this.idModalidade = idModalidade;
    }

    public Integer getIdGraduacao() {
        return idGraduacao;
    }

    public void setIdGraduacao(Integer idGraduacao) {
        this.idGraduacao = idGraduacao;
    }

    public Integer getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Integer idPlano) {
        this.idPlano = idPlano;
    }

    public Integer getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(Integer idNuvem) {
        this.idNuvem = idNuvem;
    }

    @Override
    public String toString() {
        return "MatriculaModalidade{" +
                "matricula=" + matricula +
                ", modalidade=" + modalidade +
                ", graduacao=" + graduacao +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", plano=" + plano +
                ", idUsuario=" + idUsuario +
                ", idMatricula=" + idMatricula +
                ", idModalidade=" + idModalidade +
                ", idGraduacao=" + idGraduacao +
                ", idPlano=" + idPlano +
                ", idNuvem=" + idNuvem +
                '}';
    }
}
