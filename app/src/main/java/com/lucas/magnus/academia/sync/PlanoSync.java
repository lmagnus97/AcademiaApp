package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Plano;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class PlanoSync {

    protected static Integer sync(Plano plano) {

        Resposta resposta = API.postPlano(plano);

        if (resposta != null) {
            Utils.log("PLANO SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }
        return null;
    }


}
