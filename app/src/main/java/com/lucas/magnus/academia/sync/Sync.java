package com.lucas.magnus.academia.sync;

import android.content.Context;
import android.util.Log;

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

    public static void sync(Context context) {
        final Integer idUsuario = UtilShared.getIdUsuario(context);
        if (idUsuario.equals(-1)) return;

        List<Aluno> listaAlunos = new AlunoDAO(context).selectAll();
        List<Modalidade> listaModalidades = new ModalidadeDAO(context).selectAll();
        List<Graduacao> listaGraduacoes = new GraduacaoDAO(context).selectAll();
        List<Plano> listaPlanos = new PlanoDAO(context).selectAll();
        List<Matricula> listaMatriculas = new MatriculaDAO(context).selectAll();
        List<MatriculaModalidade> listaMatriculaModalidades = new MatriculaModalidadeDAO(context).selectAll();

        //////ALUNOS
        for (Aluno aluno : listaAlunos) {
            Log.i("INFOLOG", "ID NUVEM DO ALUNO: " + aluno.getIdNuvem());
            Integer idAux = aluno.getCodigoAluno();
            aluno.setCodigoAluno(null);
            aluno.setIdUsuario(idUsuario);
            Integer idNuvem = aluno.getIdNuvem() == 0 ? AlunoSync.sync(aluno) : aluno.getIdNuvem();
            aluno.setIdNuvem(idNuvem);
            aluno.setCodigoAluno(idAux);

            if (idNuvem != null && idNuvem != 999) {
                AlunoDAO alunoDAO = new AlunoDAO(context);
                alunoDAO.updateIdNuvem(aluno.getCodigoAluno(), idNuvem);
            }
        }

        //////MODALIDADES
        for (Modalidade modalidade : listaModalidades) {
            Log.i("INFOLOG", "ID NUVEM DO MOD: " + modalidade.getIdNuvem());
            modalidade.setIdUsuario(idUsuario);
            Log.i("INFOLOG MODALIDADE", modalidade.getIdNuvem().toString());
            Integer idNuvem = modalidade.getIdNuvem() == 0 ? ModalidadeSync.sync(modalidade) : modalidade.getIdNuvem();
            Utils.log("GET MODALIDADE", String.valueOf(idNuvem));
            modalidade.setIdNuvem(idNuvem);

            if (idNuvem != null && idNuvem != 999) {
                ModalidadeDAO modalidadeDAO = new ModalidadeDAO(context);
                modalidadeDAO.updateIdNuvem(modalidade.getModalidade(), idNuvem);
            }
        }

        //////GRADUACOES
        for (Graduacao graduacao : listaGraduacoes) {
            for (Modalidade modalidade : listaModalidades) {
                if (modalidade.getModalidade().equals(graduacao.getModalidade().getModalidade())) {
                    graduacao.setIdUsuario(idUsuario);
                    graduacao.setIdModalidade(modalidade.getIdNuvem());
                    Integer idNuvem = graduacao.getIdNuvem() == 0 ? GraduacaoSync.sync(graduacao) : graduacao.getIdNuvem();
                    graduacao.setIdNuvem(idNuvem);

                    if (idNuvem != null && idNuvem != 999) {
                        GraduacaoDAO graduacaoDAO = new GraduacaoDAO(context);
                        graduacaoDAO.updateIdNuvem(graduacao.getGraduacao(), idNuvem);
                    }
                }
            }
        }

        //////PLANOS
        for (Plano plano : listaPlanos) {
            for (Modalidade modalidade : listaModalidades) {
                if (modalidade.getModalidade().equals(plano.getModalidade().getModalidade())) {
                    plano.setIdUsuario(idUsuario);
                    plano.setIdModalidade(modalidade.getIdNuvem());
                    Integer idNuvem = plano.getIdNuvem() == 0 ? PlanoSync.sync(plano) : plano.getIdNuvem();
                    plano.setIdNuvem(idNuvem);

                    if (idNuvem != null && idNuvem != 999) {
                        PlanoDAO planoDAO = new PlanoDAO(context);
                        planoDAO.updateIdNuvem(plano, idNuvem);
                    }
                }
            }

        }

        //////MATRICULAS
        for (Matricula matricula : listaMatriculas) {
            for (Aluno aluno : listaAlunos) {
                if (aluno.getCodigoAluno().equals(matricula.getIdAluno())) {
                    Log.i("INFOLOG", "O ID DO ALUNO NA NUVEM É: " + aluno.getIdNuvem().toString());

                    Integer idCodigoMatricula = matricula.getCodigoMatricula();
                    matricula.setCodigoMatricula(null);
                    matricula.setIdUsuario(idUsuario);
                    matricula.setIdAluno(aluno.getIdNuvem());
                    Integer idNuvem = matricula.getIdNuvem() == 0 ? MatriculaSync.sync(matricula) : matricula.getIdNuvem();
                    matricula.setIdNuvem(idNuvem);
                    matricula.setCodigoMatricula(idCodigoMatricula);

                    if (idNuvem != null && idNuvem != 999) {
                        MatriculaDAO matriculaDAO = new MatriculaDAO(context);
                        matriculaDAO.updateIdNuvem(matricula.getCodigoMatricula(), idNuvem);
                    }
                }
            }
        }

        //////MATRICULASMODALIDADE
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
