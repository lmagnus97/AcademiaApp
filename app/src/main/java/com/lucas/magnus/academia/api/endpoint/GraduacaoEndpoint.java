package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Resposta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface GraduacaoEndpoint {

    @POST("api/graduacao/inserir")
    Call<Resposta> PostGraduacao(@Body() Graduacao graduacao);

}
