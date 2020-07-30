package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class MatriculaModalidadeSync {

    protected static Integer sync(MatriculaModalidade matriculaModalidade) {

        Resposta resposta = API.postMatriculaModalidade(matriculaModalidade);

        if (resposta != null) {
            Utils.log("MATRICULA MODALIDADE SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }
        return null;
    }


}
