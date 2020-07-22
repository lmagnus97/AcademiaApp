package com.lucas.magnus.academia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Modalidade implements Serializable {

    private String modalidade;
    @SerializedName("id_usuario")
    private Integer idUsuario;
    private Integer idNuvem;

    public Modalidade() {
    }

    public Modalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
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

    public static List<String> convertToSpinner(List<Modalidade> lista, String titulo){
        List<String> newLista = new ArrayList<>();

        if(titulo != null){
            newLista.add(titulo);
        }

        for(Modalidade item : lista){
            newLista.add(item.getModalidade());
        }

        return newLista;
    }
}
