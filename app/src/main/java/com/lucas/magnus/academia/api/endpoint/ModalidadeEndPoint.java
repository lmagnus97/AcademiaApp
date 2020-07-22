package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Resposta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ModalidadeEndpoint {

    @POST("api/modalidade/inserir")
    Call<Resposta> PostModalidade(@Body() Modalidade modalidade);

}
