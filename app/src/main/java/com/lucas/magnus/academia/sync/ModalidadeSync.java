package com.lucas.magnus.academia.sync;

import android.util.Log;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class ModalidadeSync {

    protected static Integer sync(Modalidade modalidade) {

        Resposta resposta = API.postModalidade(modalidade);

        if (resposta != null) {
            Utils.log("MODALIDADE SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }else{
            Log.i("INFOLOG", "É NULO");
        }
        return null;
    }


}
