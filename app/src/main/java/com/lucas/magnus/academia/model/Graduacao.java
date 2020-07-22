package com.lucas.magnus.academia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graduacao implements Serializable {

    private Modalidade modalidade;
    private String graduacao;
    @SerializedName("id_modalidade")
    private Integer idModalidade;
    @SerializedName("id_usuario")
    private Integer idUsuario;
    private Integer idNuvem;

    public Graduacao() {
    }

    public Graduacao(Modalidade modalidade, String graduacao) {
        this.modalidade = modalidade;
        this.graduacao = graduacao;
    }

    public Graduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    public Integer getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Integer idModalidade) {
        this.idModalidade = idModalidade;
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

    public static List<String> convertToSpinner(List<Graduacao> lista, String titulo){
        List<String> newLista = new ArrayList<>();

        newLista.add(titulo);
        for(Graduacao item : lista){
            newLista.add(item.getGraduacao());
        }

        return newLista;
    }

    @Override
    public String toString() {
        return "Graduacao{" +
                "modalidade='" + modalidade + '\'' +
                ", graduacao='" + graduacao + '\'' +
                '}';
    }
}
