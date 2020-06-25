package com.lucas.magnus.academia.dao;

import android.content.Context;
import android.database.Cursor;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Cidade;

import java.util.ArrayList;
import java.util.List;

public class CidadeDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_cidade";

    private final String[] colunas =
            {
                    COLUMN_CIDADE,
                    COLUMN_ESTADO,
                    COLUMN_PAIS,
            };

    public static final String
            COLUMN_CIDADE = "cidade", COLUMN_ESTADO = "estado", COLUMN_PAIS = "pais";


    public static final String
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_CIDADE + " VARCHAR NOT NULL, "
            + COLUMN_ESTADO + " VARCHAR NOT NULL, "
            + COLUMN_PAIS + " VARCHAR NOT NULL, "
            + "PRIMARY KEY ( " + COLUMN_CIDADE + ", " + COLUMN_ESTADO + ", " + COLUMN_PAIS + " )"
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public CidadeDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Cidade> select(String value) {
        List<Cidade> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CIDADE + " LIKE '%" + value + "%'", null);

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


    public Cidade toObject(Cursor cursor) {
        Cidade data = new Cidade();
        data.setCidade(cursor.getString(cursor.getColumnIndex(COLUMN_CIDADE)));
        data.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO)));
        data.setPais(cursor.getString(cursor.getColumnIndex(COLUMN_PAIS)));
        return data;
    }

    public void insertCities(){

    }


}
