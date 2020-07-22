package com.lucas.magnus.academia.model;

import java.io.Serializable;

public class Resposta implements Serializable {

    private String Mensagem;
    private boolean Sucesso;
    private long codigo;

    public Resposta() {
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public boolean isSucesso() {
        return Sucesso;
    }

    public void setSucesso(boolean sucesso) {
        Sucesso = sucesso;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
}
