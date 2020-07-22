package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class ModalidadeSync {

    protected static Integer sync(Modalidade modalidade) {

        Resposta resposta = API.PostModalidade(modalidade);

        if (resposta != null) {
            Utils.log("MODALIDADE SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }
        return null;
    }


}
