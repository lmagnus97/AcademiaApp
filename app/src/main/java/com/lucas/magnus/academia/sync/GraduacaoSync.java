package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class GraduacaoSync {

    protected static Integer sync(Graduacao graduacao) {

        Resposta resposta = API.PostGraduacao(graduacao);

        if (resposta != null) {
            Utils.log("GRADUACAO SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }
        return null;
    }


}
