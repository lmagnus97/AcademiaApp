package com.lucas.magnus.academia.api.endpoint;

import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioEndpoint {

    @POST("api/usuario/inserir")
    Call<Resposta> PostUsuario(@Body() Usuario usuario);

    @GET("api/usuario/buscar")
    Call<Usuario> GetUsuario(@Query("nome") final String login, @Query("senha") final String senha);

}
