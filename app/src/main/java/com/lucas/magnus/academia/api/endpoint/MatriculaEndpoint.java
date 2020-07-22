package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.Resposta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MatriculaEndpoint {
    @POST("api/matricula/inserir")
    Call<Resposta> PostMatricula(@Body() Matricula matricula);
}
