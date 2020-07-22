package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Resposta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface MatriculaModalidadeEndpoint {

    @POST("api/matricula_modalidade/inserir")
    Call<Resposta> PostMatriculaModalidade(@Body() MatriculaModalidade matriculaModalidade);

}
