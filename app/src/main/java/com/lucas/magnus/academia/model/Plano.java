package com.lucas.magnus.academia.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Plano {

    private String plano;
    private Modalidade modalidade;
    private Double valorMensal;
    @SerializedName("id_usuario")
    private Integer idUsuario;
    @SerializedName("id_modalidade")
    private Integer idModalidade;
    private Integer idNuvem;

    public Plano(String plano, Modalidade modalidade, Double valorMensal) {
        this.plano = plano;
        this.modalidade = modalidade;
        this.valorMensal = valorMensal;
    }

    public Plano(String plano) {
        this.plano = plano;
    }

    public Plano() {
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(Double valorMensal) {
        this.valorMensal = valorMensal;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Integer idModalidade) {
        this.idModalidade = idModalidade;
    }

    public Integer getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(Integer idNuvem) {
        this.idNuvem = idNuvem;
    }

    public static List<String> convertToSpinner(List<Plano> lista, String titulo){
        List<String> newLista = new ArrayList<>();

        newLista.add(titulo);
        for(Plano item : lista){
            newLista.add(item.getPlano());
        }

        return newLista;
    }

    @Override
    public String toString() {
        return "Plano{" +
                "plano='" + plano + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", valorMensal=" + valorMensal +
                '}';
    }
}
