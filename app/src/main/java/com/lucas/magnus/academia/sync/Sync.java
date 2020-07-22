package com.lucas.magnus.academia.sync;

import android.content.Context;

import com.lucas.magnus.academia.dao.AlunoDAO;
import com.lucas.magnus.academia.dao.GraduacaoDAO;
import com.lucas.magnus.academia.dao.MatriculaDAO;
import com.lucas.magnus.academia.dao.MatriculaModalidadeDAO;
import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.dao.PlanoDAO;
import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;
import com.lucas.magnus.academia.util.UtilShared;
import com.lucas.magnus.academia.util.Utils;

import java.util.List;

public class Sync {

    static void sync(Context context) {

        List<Aluno> listaAlunos = new AlunoDAO(context).selectAll();
        List<Modalidade> listaModalidades = new ModalidadeDAO(context).selectAll();
        List<Graduacao> listaGraduacoes = new GraduacaoDAO(context).selectAll();
        List<Plano> listaPlanos = new PlanoDAO(context).selectAll();
        List<Matricula> listaMatriculas = new MatriculaDAO(context).selectAll();
        List<MatriculaModalidade> listaMatriculaModalidades = new MatriculaModalidadeDAO(context).selectAll();

        final Integer idUsuario = UtilShared.getIdUsuario(context);
        if (idUsuario.equals(-1)) return;

        //ALUNOS
        for (Aluno aluno : listaAlunos) {
            Integer idAux = aluno.getCodigoAluno();
            aluno.setCodigoAluno(null);
            aluno.setIdUsuario(idUsuario);
            aluno.setIdNuvem(AlunoSync.sync(aluno));
            aluno.setCodigoAluno(idAux);
        }

        //MODALIDADES
        for (Modalidade modalidade : listaModalidades) {
            modalidade.setIdUsuario(idUsuario);
            Integer idModalidade = ModalidadeSync.sync(modalidade);
            Utils.log("GET MODALIDADE", String.valueOf(idModalidade));
            modalidade.setIdNuvem(idModalidade);
        }

        //GRADUACOES
        for (Graduacao graduacao : listaGraduacoes) {
            for (Modalidade modalidade : listaModalidades) {
                if (modalidade.getModalidade().equals(graduacao.getModalidade().getModalidade())) {
                    graduacao.setIdUsuario(idUsuario);
                    graduacao.setIdModalidade(modalidade.getIdNuvem());
                    graduacao.setIdNuvem(GraduacaoSync.sync(graduacao));
                }

            }

        }

        //PLANOS
        for (Plano plano : listaPlanos) {
            for (Modalidade modalidade : listaModalidades) {
                if (modalidade.getModalidade().equals(plano.getModalidade().getModalidade())) {
                    plano.setIdUsuario(idUsuario);
                    plano.setIdModalidade(modalidade.getIdNuvem());
                    plano.setIdNuvem(PlanoSync.sync(plano));
                }
            }

        }

        //MATRICULAS
        for (Matricula matricula : listaMatriculas) {
            for (Aluno aluno : listaAlunos) {
                if (aluno.getCodigoAluno().equals(matricula.getIdAluno())) {

                    Integer idCodigoMatricula = matricula.getCodigoMatricula();
                    matricula.setCodigoMatricula(null);
                    matricula.setIdUsuario(idUsuario);
                    matricula.setCodigoAluno(aluno.getIdNuvem());
                    matricula.setIdNuvem(MatriculaSync.sync(matricula));
                    matricula.setCodigoMatricula(idCodigoMatricula);
                }
            }
        }

        //MATRICULASMODALIDADE
        for (MatriculaModalidade matriculaModalidade : listaMatriculaModalidades) {
            matriculaModalidade.setIdUsuario(idUsuario);

            for (Matricula matricula : listaMatriculas) {
                if (matricula.getCodigoMatricula().equals(matriculaModalidade.getMatricula().getCodigoMatricula())) {
                    matricula.setIdNuvem(matricula.getIdNuvem());
                    matriculaModalidade.setMatricula(matricula);
                }
            }

            for (Modalidade modalidade : listaModalidades) {
                if (modalidade.getModalidade().equals(matriculaModalidade.getModalidade().getModalidade())) {
                    matriculaModalidade.setIdModalidade(modalidade.getIdNuvem());
                }
            }

            for (Graduacao graduacao : listaGraduacoes) {
                if (graduacao.getGraduacao().equals(matriculaModalidade.getGraduacao().getGraduacao())) {
                    matriculaModalidade.setIdGraduacao(graduacao.getIdNuvem());
                }
            }

            for (Plano plano : listaPlanos) {
                if (plano.getPlano().equals(matriculaModalidade.getPlano().getPlano())) {
                    matriculaModalidade.setIdGraduacao(plano.getIdNuvem());
                }
            }

            matriculaModalidade.setIdNuvem(MatriculaModalidadeSync.sync(matriculaModalidade));

        }

    }

}
