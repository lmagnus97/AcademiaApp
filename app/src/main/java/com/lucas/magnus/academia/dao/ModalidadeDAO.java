package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Modalidade;

import java.util.ArrayList;
import java.util.List;

public class ModalidadeDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_modalidade";

    private final String[] colunas =
            {
                    COLUMN_MODALIDADE,
                    COLUMN_ATIVO,
                    COLUMN_ID_NUVEM
            };

    public static final String
            COLUMN_MODALIDADE = "modalidade",
            COLUMN_ATIVO = "ativo",
            COLUMN_ID_NUVEM = "id_nuvem";


    public static final String
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_MODALIDADE + " VARCHAR(100) PRIMARY KEY, "
            + COLUMN_ATIVO + " INTEGER DEFAULT 1, "
            + COLUMN_ID_NUVEM + " INTEGER DEFAULT 0"
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public ModalidadeDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Modalidade> selectAll() {
        List<Modalidade> lista = new ArrayList<>();

        try {
            open();
            Cursor cursor = database.query(
                    ModalidadeDAO.TABLE_NAME,
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

    public long insert(Modalidade modalidade) {

        long rowsAffect = 0;

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(COLUMN_MODALIDADE, modalidade.getModalidade().trim());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }


        Log.i("INFOLOG", "LINHAS AFETADAS: " + String.valueOf(rowsAffect));

        return rowsAffect;
    }

    public boolean isDesativado(String modalidade) {

        try {
            open();

            Cursor cursor = database.query(
                    ModalidadeDAO.TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 0 and " + COLUMN_MODALIDADE + " = ?",
                    new String[]{modalidade},
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

    public long updateIdNuvem(String modalidade, Integer idNuvem) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_NUVEM, idNuvem);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + " = ?",
                    new String[]{modalidade}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public long ativarModalidade(final String modalidade) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 1);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ?",
                    new String[]{String.valueOf(modalidade)});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public long delete(final String modalidade) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 0);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ?",
                    new String[]{String.valueOf(modalidade)});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Modalidade toObject(Cursor cursor) {
        Modalidade modalidade = new Modalidade();
        modalidade.setModalidade(cursor.getString(cursor.getColumnIndex(COLUMN_MODALIDADE)));
        modalidade.setIdNuvem(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NUVEM)));
        return modalidade;
    }

}
