package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;

import java.util.ArrayList;
import java.util.List;

public class MatriculaModalidadeDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_matricula_modalidade";

    private final String[] colunas =
            {
                    COLUMN_CODIGO_MATRICULA,
                    COLUMN_MODALIDADE,
                    COLUMN_GRADUACAO,
                    COLUMN_DATA_INICIO,
                    COLUMN_DATA_FIM,
                    COLUMN_PLANO,
            };

    public static final String
            COLUMN_CODIGO_MATRICULA = "codigo_matricula",
            COLUMN_MODALIDADE = "modalidade",
            COLUMN_GRADUACAO = "graduacao",
            COLUMN_DATA_INICIO = "data_inicio",
            COLUMN_DATA_FIM = "data_fim",
            COLUMN_PLANO = "plano";


    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_CODIGO_MATRICULA + " int NOT NULL, " +
            COLUMN_MODALIDADE + " varchar(45) NOT NULL, " +
            COLUMN_GRADUACAO + " varchar(45) NOT NULL, " +
            COLUMN_DATA_INICIO + " integer DEFAULT NULL, " +
            COLUMN_DATA_FIM + " integer DEFAULT NULL, " +
            COLUMN_PLANO + " varchar(45) NOT NULL, " +
            "  PRIMARY KEY (" + COLUMN_CODIGO_MATRICULA + ", " + COLUMN_MODALIDADE + "), " +
            "  FOREIGN KEY ( " + COLUMN_CODIGO_MATRICULA + ") REFERENCES " + MatriculaDAO.TABLE_NAME + " (" + COLUMN_CODIGO_MATRICULA + "), " +
            "  FOREIGN KEY (" + COLUMN_MODALIDADE + ") REFERENCES " + ModalidadeDAO.TABLE_NAME + " (" + COLUMN_MODALIDADE + "), " +
            "  FOREIGN KEY (" + COLUMN_MODALIDADE + ", " + COLUMN_GRADUACAO + ") REFERENCES " + GraduacaoDAO.TABLE_NAME + " (" + COLUMN_MODALIDADE + ", " + COLUMN_GRADUACAO + "), " +
            "  FOREIGN KEY (" + COLUMN_MODALIDADE + ", " + COLUMN_PLANO + ") REFERENCES " + PlanoDAO.TABLE_NAME + " (" + COLUMN_MODALIDADE + ", " + COLUMN_PLANO + ") " +
            ")";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public MatriculaModalidadeDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<MatriculaModalidade> selectAll() {
        List<MatriculaModalidade> result = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT mm.*, a." + AlunoDAO.COLUMN_ALUNO + ", p." + PlanoDAO.COLUMN_VALOR_MENSAL +
                    " FROM " + TABLE_NAME + " mm " +
                    " INNER JOIN " + MatriculaDAO.TABLE_NAME + " ma ON mm." + COLUMN_CODIGO_MATRICULA + " = ma." + MatriculaDAO.COLUMN_CODIGO_MATRICULA +
                    " INNER JOIN " + AlunoDAO.TABLE_NAME + " a ON ma." + MatriculaDAO.COLUMN_CODIGO_ALUNO + " = a." + AlunoDAO.COLUMN_CODIGO_ALUNO +
                    " INNER JOIN " + PlanoDAO.TABLE_NAME + " p ON mm." + COLUMN_PLANO + " = p." + PlanoDAO.COLUMN_PLANO + " and mm." + COLUMN_MODALIDADE + " = p." +
                    PlanoDAO.COLUMN_MODALIDADE, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Log.i("INFOLOG", "OBJ RETORNADO:" + toObject(cursor).toString());
                result.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return result;
    }

    public List<MatriculaModalidade> selectForCodigoMatricula(Integer codigo_matricula) {
        List<MatriculaModalidade> result = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT mm.*, a." + AlunoDAO.COLUMN_ALUNO + ", p." + PlanoDAO.COLUMN_VALOR_MENSAL +
                    " FROM " + TABLE_NAME + " mm " +
                    " INNER JOIN " + MatriculaDAO.TABLE_NAME + " ma ON mm." + COLUMN_CODIGO_MATRICULA + " = ma." + MatriculaDAO.COLUMN_CODIGO_MATRICULA +
                    " INNER JOIN " + AlunoDAO.TABLE_NAME + " a ON ma." + MatriculaDAO.COLUMN_CODIGO_ALUNO + " = a." + AlunoDAO.COLUMN_CODIGO_ALUNO +
                    " INNER JOIN " + PlanoDAO.TABLE_NAME + " p ON mm." + COLUMN_PLANO + " = p." + PlanoDAO.COLUMN_PLANO + " and mm." + COLUMN_MODALIDADE + " = p." + PlanoDAO.COLUMN_MODALIDADE +
                    " WHERE mm." + COLUMN_CODIGO_MATRICULA + " = ?", new String[]{String.valueOf(codigo_matricula)});

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Log.i("INFOLOG", "OBJ RETORNADO:" + toObject(cursor).toString());
                result.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return result;
    }

    public long insert(MatriculaModalidade matriculaModalidade) {

        long rowsAffect = 0;

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(COLUMN_CODIGO_MATRICULA, matriculaModalidade.getMatricula().getCodigoMatricula());
            values.put(COLUMN_MODALIDADE, matriculaModalidade.getModalidade().getModalidade());
            values.put(COLUMN_GRADUACAO, matriculaModalidade.getGraduacao().getGraduacao());
            values.put(COLUMN_DATA_INICIO, matriculaModalidade.getDataInicio());
            values.put(COLUMN_DATA_FIM, matriculaModalidade.getDataFim());
            values.put(COLUMN_PLANO, matriculaModalidade.getPlano().getPlano());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }


        Log.i("INFOLOG", "LINHAS AFETADAS: " + String.valueOf(rowsAffect));

        return rowsAffect;
    }

    public void delete(MatriculaModalidade matriculaModalidade) {
        open();
        database.delete(TABLE_NAME, COLUMN_MODALIDADE + "= ? AND " + COLUMN_CODIGO_MATRICULA + " = ?",
                new String[]{matriculaModalidade.getModalidade().getModalidade(), String.valueOf(matriculaModalidade.getMatricula())});
        close();
    }

    public MatriculaModalidade toObject(Cursor cursor) {

        Matricula matricula = new Matricula();
        matricula.setCodigoMatricula(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_MATRICULA)));

        Modalidade modalidade = new Modalidade();
        modalidade.setModalidade(cursor.getString(cursor.getColumnIndex(COLUMN_MODALIDADE)));

        Graduacao graduacao = new Graduacao();
        graduacao.setGraduacao(cursor.getString(cursor.getColumnIndex(COLUMN_GRADUACAO)));

        Plano plano = new Plano();
        plano.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_PLANO)));
        plano.setValorMensal(cursor.getDouble(cursor.getColumnIndex(PlanoDAO.COLUMN_VALOR_MENSAL)));

        MatriculaModalidade matriculaModalidade = new MatriculaModalidade();
        matriculaModalidade.setMatricula(matricula);
        matriculaModalidade.setModalidade(modalidade);
        matriculaModalidade.setGraduacao(graduacao);
        matriculaModalidade.setDataInicio(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_INICIO)));
        matriculaModalidade.setDataFim(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_FIM)));
        matriculaModalidade.setPlano(plano);
        return matriculaModalidade;
    }

}
