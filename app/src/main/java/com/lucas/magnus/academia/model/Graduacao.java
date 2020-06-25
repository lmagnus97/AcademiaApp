package com.lucas.magnus.academia.model;

import java.util.ArrayList;
import java.util.List;

public class Graduacao {

    private String modalidade;
    private String graduacao;

    public Graduacao() {
    }

    public Graduacao(String modalidade, String graduacao) {
        this.modalidade = modalidade;
        this.graduacao = graduacao;
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
