package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Resposta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlunoEndpoint {

    @POST("api/aluno/inserir")
    Call<Resposta> PostAluno(@Body() Aluno aluno);

}
