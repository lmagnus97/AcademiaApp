package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;

import java.util.ArrayList;
import java.util.List;

public class PlanoDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_plano";

    private final String[] colunas =
            {
                    COLUMN_PLANO,
                    COLUMN_MODALIDADE,
                    COLUMN_VALOR_MENSAL,
                    COLUMN_ATIVO
            };

    public static final String
            COLUMN_PLANO = "plano",
            COLUMN_MODALIDADE = "modalidade",
            COLUMN_VALOR_MENSAL = "valor_mensal",
            COLUMN_ATIVO = "ativo";


    public static final String
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_PLANO + " VARCHAR, "
            + COLUMN_MODALIDADE + " VARCHAR, "
            + COLUMN_VALOR_MENSAL + " REAL NOT NULL, "
            + COLUMN_ATIVO + " INTEGER DEFAULT 1, "
            + " PRIMARY KEY ( " + COLUMN_PLANO + ", " + COLUMN_MODALIDADE + " ), "
            + " FOREIGN KEY (" + COLUMN_MODALIDADE + ") REFERENCES " + ModalidadeDAO.TABLE_NAME + " ( " + COLUMN_MODALIDADE + " )"
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public PlanoDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Plano> selectAll() {
        List<Plano> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    PlanoDAO.TABLE_NAME,
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

    public List<Plano> selectPlanosModalidades(String modalidade) {
        List<Plano> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    PlanoDAO.TABLE_NAME,
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


    public long insert(Plano data) {


        long rowsAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PLANO, data.getPlano().trim());
            values.put(COLUMN_MODALIDADE, data.getModalidade().getModalidade().trim());
            values.put(COLUMN_VALOR_MENSAL, data.getValorMensal());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }

        return rowsAffect;
    }

    public boolean isDesativado(Plano plano) {

        try {
            open();

            Cursor cursor = database.query(
                    TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 0 and " + COLUMN_MODALIDADE + " = ? and " + COLUMN_PLANO + " = ?",
                    new String[]{plano.getModalidade().getModalidade(), plano.getPlano()},
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

    public long ativar(final Plano plano) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 1);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ? and " + COLUMN_PLANO + " = ?",
                    new String[]{plano.getModalidade().getModalidade(), plano.getPlano()});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Plano select(String plano, String modalidade) {
        Plano result = new Plano();

        try {
            open();

            Cursor cursor = database.query(
                    TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 1 AND " + COLUMN_PLANO + " = ? AND " + COLUMN_MODALIDADE + " = ?",
                    new String[]{plano, modalidade},
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

    public long update(Plano data) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALOR_MENSAL, data.getValorMensal());

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_MODALIDADE + "= ? and " + COLUMN_PLANO + " = ?",
                    new String[]{data.getModalidade().getModalidade(), data.getPlano()}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;

    }

    public long delete(final Plano plano) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 0);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_PLANO + "= ? and " + COLUMN_MODALIDADE + " = ?",
                    new String[]{plano.getPlano(), plano.getModalidade().getModalidade()});

        } catch (SQLException e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Plano toObject(Cursor cursor) {
        Plano data = new Plano();
        data.setModalidade(new Modalidade(cursor.getString(cursor.getColumnIndex(COLUMN_MODALIDADE))));
        data.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_PLANO)));
        data.setValorMensal(cursor.getDouble(cursor.getColumnIndex(COLUMN_VALOR_MENSAL)));
        return data;
    }

}
