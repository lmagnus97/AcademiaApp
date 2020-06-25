package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.FaturaMatricula;
import com.lucas.magnus.academia.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class FaturaDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_fatura_matricula";

    private final String[] colunas =
            {
                    COLUMN_CODIGO_MATRICULA,
                    COLUMN_DATA_VENCIMENTO,
                    COLUMN_VALOR,
                    COLUMN_DATA_PAGAMENTO,
                    COLUMN_DATA_CANCELAMENTO
            };

    public static final String
            COLUMN_CODIGO_MATRICULA = "codigo_matricula", COLUMN_DATA_VENCIMENTO = "data_vencimento", COLUMN_VALOR = "valor",
            COLUMN_DATA_PAGAMENTO = "data_pagamento", COLUMN_DATA_CANCELAMENTO = "data_cancelamento";


    public static final String
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_CODIGO_MATRICULA + " int NOT NULL, "
            + COLUMN_DATA_VENCIMENTO + " VARCHAR NOT NULL, "
            + COLUMN_VALOR + " decimal(10,2) NOT NULL, "
            + COLUMN_DATA_PAGAMENTO + " VARCHAR DEFAULT NULL, "
            + COLUMN_DATA_CANCELAMENTO + " VARCHAR DEFAULT NULL, "
            + " PRIMARY KEY (" + COLUMN_DATA_VENCIMENTO + ", " + COLUMN_CODIGO_MATRICULA + "), "
            + " FOREIGN KEY ( " + COLUMN_CODIGO_MATRICULA + ") REFERENCES " + MatriculaDAO.TABLE_NAME + " (" + COLUMN_CODIGO_MATRICULA + ")"
            + ")";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public FaturaDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<FaturaMatricula> selectAll() {
        List<FaturaMatricula> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT f.*, a." + AlunoDAO.COLUMN_ALUNO + " FROM " + TABLE_NAME + " f " +
                    " LEFT JOIN " + MatriculaDAO.TABLE_NAME + " m ON f." + COLUMN_CODIGO_MATRICULA + " = m." + MatriculaDAO.COLUMN_CODIGO_MATRICULA +
                    " LEFT JOIN " + AlunoDAO.TABLE_NAME + " a ON a." + AlunoDAO.COLUMN_CODIGO_ALUNO + " = m." + MatriculaDAO.COLUMN_CODIGO_ALUNO, null
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

    public long insert(FaturaMatricula faturaMatricula) {

        long rowsAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_CODIGO_MATRICULA, faturaMatricula.getCodigoMatricula());
            values.put(COLUMN_DATA_VENCIMENTO, Utils.calendarToString(faturaMatricula.getDataVencimento()));
            values.put(COLUMN_VALOR, faturaMatricula.getValor());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }

        return rowsAffect;
    }

    public FaturaMatricula select(FaturaMatricula faturaMatricula) {
        FaturaMatricula result = new FaturaMatricula();

        try {
            open();

            Cursor cursor = database.query(
                    TABLE_NAME,
                    colunas,
                    COLUMN_CODIGO_MATRICULA + " = ? AND " + COLUMN_DATA_VENCIMENTO + " = ?",
                    new String[]{Utils.calendarToString(faturaMatricula.getDataVencimento()), String.valueOf(faturaMatricula.getCodigoMatricula())},
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

    public void delete(final FaturaMatricula faturaMatricula) {
        open();
        database.delete(TABLE_NAME, COLUMN_CODIGO_MATRICULA + " = ? AND " + COLUMN_DATA_VENCIMENTO + " = ?",
                new String[]{String.valueOf(faturaMatricula.getCodigoMatricula()), Utils.calendarToString(faturaMatricula.getDataVencimento())});
        close();
    }

    public FaturaMatricula toObject(Cursor cursor) {
        FaturaMatricula faturaMatricula = new FaturaMatricula();
        faturaMatricula.setCodigoMatricula(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_MATRICULA)));
        faturaMatricula.setDataCancelamento(Utils.stringToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO_MATRICULA))));
        faturaMatricula.setDataPagamento(Utils.stringToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO_MATRICULA))));
        faturaMatricula.setDataVencimento(Utils.stringToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_DATA_VENCIMENTO))));
        faturaMatricula.setValor(cursor.getDouble(cursor.getColumnIndex(COLUMN_VALOR)));
        faturaMatricula.setAluno(cursor.getString(cursor.getColumnIndex(AlunoDAO.COLUMN_ALUNO)));
        return faturaMatricula;
    }

}
