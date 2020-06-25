package com.lucas.magnus.academia.model;

import java.util.ArrayList;
import java.util.List;

public class Modalidade {

    private String modalidade;

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
