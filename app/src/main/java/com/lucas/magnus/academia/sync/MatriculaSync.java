package com.lucas.magnus.academia.sync;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.util.Utils;

public class MatriculaSync {

    protected static Integer sync(Matricula matricula) {
        Resposta response = API.postMatricula(matricula);

        if (response != null) {
            Utils.log("MATRICULA SYNC", response.getMensagem());
            return (int) response.getCodigo();
        }

        return null;
    }


}
