package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Plano;
import com.lucas.magnus.academia.model.Resposta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlanoEndpoint {

    @POST("api/plano/inserir")
    Call<Resposta> PostPlano(@Body() Plano plano);

}
