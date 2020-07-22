package com.lucas.magnus.academia.api;

import com.lucas.magnus.academia.api.endpoint.AlunoEndpoint;
import com.lucas.magnus.academia.api.endpoint.GraduacaoEndpoint;
import com.lucas.magnus.academia.api.endpoint.MatriculaEndpoint;
import com.lucas.magnus.academia.api.endpoint.MatriculaModalidadeEndpoint;
import com.lucas.magnus.academia.api.endpoint.ModalidadeEndpoint;
import com.lucas.magnus.academia.api.endpoint.PlanoEndpoint;
import com.lucas.magnus.academia.api.endpoint.UsuarioEndpoint;
import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.model.Usuario;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    static final String URL_ROOT = "http://nextcodedev.com.br/";


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void GetUsuario(final String login, final String senha, Callback<Usuario> respostaCall) {
        UsuarioEndpoint usuarioEndpoint = retrofit.create(UsuarioEndpoint.class);

        Call<Usuario> resposta = usuarioEndpoint.GetUsuario(login, senha);

        resposta.enqueue(respostaCall);
    }

    public static void PostUsuario(final Usuario usuario, Callback<Resposta> respostaCallback) {
        UsuarioEndpoint usuarioEndpoint = retrofit.create(UsuarioEndpoint.class);
        Call<Resposta> resposta = usuarioEndpoint.PostUsuario(usuario);
        resposta.enqueue(respostaCallback);
    }

    public static Resposta PostAluno(final Aluno aluno) {
        AlunoEndpoint alunoEndpoint = retrofit.create(AlunoEndpoint.class);

        Call<Resposta> resposta = alunoEndpoint.PostAluno(aluno);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Resposta PostModalidade(final Modalidade modalidade) {
        ModalidadeEndpoint modalidadeEndpoint = retrofit.create(ModalidadeEndpoint.class);

        Call<Resposta> resposta = modalidadeEndpoint.PostModalidade(modalidade);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Resposta PostGraduacao(final Graduacao graduacao) {
        GraduacaoEndpoint graduacaoEndpoint = retrofit.create(GraduacaoEndpoint.class);

        Call<Resposta> resposta = graduacaoEndpoint.PostGraduacao(graduacao);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Resposta PostPlano(final Plano plano) {
        PlanoEndpoint planoEndpoint = retrofit.create(PlanoEndpoint.class);

        Call<Resposta> resposta = planoEndpoint.PostPlano(plano);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Resposta PostMatricula(final Matricula matricula) {
        MatriculaEndpoint matriculaEndpoint = retrofit.create(MatriculaEndpoint.class);

        Call<Resposta> resposta = matriculaEndpoint.PostMatricula(matricula);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Resposta PostMatriculaModalidade(final MatriculaModalidade matriculaModalidade) {
        MatriculaModalidadeEndpoint matriculaModalidadeEndpoint = retrofit.create(MatriculaModalidadeEndpoint.class);

        Call<Resposta> resposta = matriculaModalidadeEndpoint.PostMatriculaModalidade(matriculaModalidade);

        try {
            return resposta.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}