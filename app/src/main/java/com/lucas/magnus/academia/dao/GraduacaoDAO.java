package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Modalidade;

import java.util.ArrayList;
import java.util.List;

public class GraduacaoDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_graduacao";

    private final String[] colunas =
            {
                    COLUMN_GRADUACAO,
                    COLUMN_MODALIDADE,
                    COLUMN_ATIVO,
                    COLUMN_ID_NUVEM
            };

    public static final String
            COLUMN_MODALIDADE = "modalidade",
            COLUMN_GRADUACAO = "graduacao",
            COLUMN_ATIVO = "ativo",
            COLUMN_ID_NUVEM = "id_nuvem";


    public static final String
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_GRADUACAO + " VARCHAR(100), "
            + COLUMN_MODALIDADE + " VARCHAR(45), "
            + COLUMN_ATIVO + " INTEGER DEFAULT 1, "
            + COLUMN_ID_NUVEM + " INTEGER DEFAULT 0, " +
            " PRIMARY KEY ( " + COLUMN_MODALIDADE + ", " + COLUMN_GRADUACAO + ")" +
            " FOREIGN KEY (" + COLUMN_MODALIDADE + ") REFERENCES " + ModalidadeDAO.TABLE_NAME + " ( " + COLUMN_MODALIDADE + ") "
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public GraduacaoDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Graduacao> selectAll() {
        List<Graduacao> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    GraduacaoDAO.TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 1",
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                lista.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return lista;
    }

    public List<Graduacao> selectForModalidade(String modalidade) {
        List<Graduacao> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    GraduacaoDAO.TABLE_NAME,
                    colunas,
                    COLUMN_MODALIDADE + " = ? AND " + COLUMN_ATIVO + " = 1",
                    new String[]{modalidade},
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                lista.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return lista;
    }

    public long insert(Graduacao graduacao) {

        long rowsAffect = 0;

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(COLUMN_GRADUACAO, graduacao.getGraduacao().trim());
            values.put(COLUMN_MODALIDADE, graduacao.getModalidade().getModalidade().trim());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }

        return rowsAffect;
    }

    public boolean isDesativado(final Graduacao graduacao) {

        try {
            open();

            Cursor cursor = database.query(
                    TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 0 and " + COLUMN_MODALIDADE + " = ? and " + COLUMN_GRADUACAO + " = ?",
                    new String[]{graduacao.getModalidade().getModalidade(), graduacao.getGraduacao()},
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                return true;
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return false;
    }

    public long ativar(final Graduacao graduacao) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 1);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ? AND " + COLUMN_GRADUACAO + " = ?",
                    new String[]{graduacao.getModalidade().getModalidade(), graduacao.getGraduacao()});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public long updateIdNuvem(String graduacao, Integer idNuvem) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_NUVEM, idNuvem);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_GRADUACAO + " = ?",
                    new String[]{graduacao}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Graduacao select(String graduacao) {
        Graduacao result = new Graduacao();

        try {
            open();

            Cursor cursor = database.query(
                    TABLE_NAME,
                    colunas,
                    COLUMN_GRADUACAO + " = ?",
                    new String[]{graduacao},
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                result = toObject(cursor);
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

    public long delete(final Graduacao graduacao) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 0);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ? AND " + COLUMN_GRADUACAO + " = ?",
                    new String[]{graduacao.getModalidade().getModalidade(), graduacao.getGraduacao()});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Graduacao toObject(Cursor cursor) {
        Graduacao graduacao = new Graduacao();
        graduacao.setGraduacao(cursor.getString(cursor.getColumnIndex(COLUMN_GRADUACAO)));
        graduacao.setModalidade(new Modalidade(cursor.getString(cursor.getColumnIndex(COLUMN_MODALIDADE))));
        graduacao.setIdNuvem(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NUVEM)));
        return graduacao;
    }

}
