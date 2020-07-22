package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class AlunoSync {

    protected static Integer sync(Aluno aluno) {
        aluno.setCodigoAluno(null);
        Resposta resposta = API.PostAluno(aluno);

        if (resposta != null) {
            Utils.log("ALUNO SYNC", resposta.getMensagem());
            return (int) resposta.getCodigo();
        }

        return null;
    }


}
