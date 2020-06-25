package com.lucas.magnus.academia.model;

import java.util.ArrayList;
import java.util.List;

public class Plano {

    private String plano;
    private String modalidade;
    private Double valorMensal;

    public Plano(String plano, String modalidade, Double valorMensal) {
        this.plano = plano;
        this.modalidade = modalidade;
        this.valorMensal = valorMensal;
    }

    public Plano() {
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public Double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(Double valorMensal) {
        this.valorMensal = valorMensal;
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
