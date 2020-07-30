package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Matricula;

import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_matricula";

    private final String[] colunas =
            {
                    COLUMN_CODIGO_MATRICULA,
                    COLUMN_CODIGO_ALUNO,
                    COLUMN_DATA_MATRICULA,
                    COLUMN_DIA_VENCIMENTO,
                    COLUMN_DATA_ENCERRAMENTO,
                    COLUMN_ID_NUVEM
            };

    public static final String
            COLUMN_CODIGO_MATRICULA = "codigo_matricula",
            COLUMN_CODIGO_ALUNO = "codigo_aluno",
            COLUMN_DATA_MATRICULA = "data_matricula",
            COLUMN_DIA_VENCIMENTO = "dia_vencimento",
            COLUMN_DATA_ENCERRAMENTO = "data_encerramento",
            COLUMN_ID_NUVEM = "id_nuvem";


    public static final String
            CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_CODIGO_MATRICULA + " INTEGER primary key autoincrement, "
            + COLUMN_CODIGO_ALUNO + " INTEGER DEFAULT NULL, "
            + COLUMN_DATA_MATRICULA + " integer NOT NULL, "
            + COLUMN_DIA_VENCIMENTO + " INTEGER NOT NULL, "
            + COLUMN_DATA_ENCERRAMENTO + " integer DEFAULT NULL, "
            + COLUMN_ID_NUVEM + " integer DEFAULT 0, " +
            " UNIQUE (" + COLUMN_CODIGO_MATRICULA + ", " + COLUMN_CODIGO_ALUNO + " ) ,"
            + " FOREIGN KEY ( " + COLUMN_CODIGO_ALUNO + " ) REFERENCES " + AlunoDAO.TABLE_NAME + " ( " + COLUMN_CODIGO_ALUNO + " )"
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public MatriculaDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Matricula> selectAll() {
        List<Matricula> lista = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT c.*, a." + AlunoDAO.COLUMN_ALUNO + " FROM " + TABLE_NAME + " c " +
                    " LEFT JOIN " + AlunoDAO.TABLE_NAME + " a ON c." + COLUMN_CODIGO_ALUNO + " = a." + AlunoDAO.COLUMN_CODIGO_ALUNO, null);

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

    public int insert(Matricula data) {


        int rowsAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_CODIGO_ALUNO, data.getIdAluno());
            values.put(COLUMN_DATA_ENCERRAMENTO, data.getDataEncerramento());
            values.put(COLUMN_DATA_MATRICULA, data.getDataMatricula());
            values.put(COLUMN_DIA_VENCIMENTO, data.getDiaVencimento());

            rowsAffect = (int) database.insert(TABLE_NAME, null, values);
            Log.i("INFOLOG", "ID GERADO DA MATRICULA: " + rowsAffect);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }

        return rowsAffect;
    }

    public Matricula select(Integer codigo_matricula) {
        Matricula result = new Matricula();

        try {
            open();

            Cursor cursor = database.rawQuery("SELECT c.*, a." + AlunoDAO.COLUMN_ALUNO + " FROM " + TABLE_NAME + " c " +
                    "LEFT JOIN " + AlunoDAO.TABLE_NAME + " a ON c." + COLUMN_CODIGO_ALUNO + " = a." + AlunoDAO.COLUMN_CODIGO_ALUNO
                    + " WHERE c." + COLUMN_CODIGO_MATRICULA + " = ?", new String[]{String.valueOf(codigo_matricula)});

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

    public long update(Integer codMatricula, Integer diaVencimento) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_DIA_VENCIMENTO, diaVencimento);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_CODIGO_MATRICULA + "= ?",
                    new String[]{String.valueOf(codMatricula)}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;

    }

    public long updateIdNuvem(Integer codMatricula, Integer idNuvem) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_NUVEM, idNuvem);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_CODIGO_MATRICULA + "= ?",
                    new String[]{String.valueOf(codMatricula)}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;

    }

    public void delete(final String data) {
        open();
        database.delete(TABLE_NAME, COLUMN_CODIGO_MATRICULA + "= ?", new String[]{data});
        close();
    }

    public Matricula toObject(Cursor cursor) {
        Aluno aluno = new Aluno();
        aluno.setAluno(cursor.getString(cursor.getColumnIndex(AlunoDAO.COLUMN_ALUNO)));
        aluno.setCodigoAluno(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_ALUNO)));

        Matricula data = new Matricula();
        data.setCodigoMatricula(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_MATRICULA)));
        data.setIdAluno(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_ALUNO)));
        data.setDiaVencimento(cursor.getInt(cursor.getColumnIndex(COLUMN_DIA_VENCIMENTO)));
        data.setDataMatricula(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_MATRICULA)));
        data.setDataEncerramento(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_ENCERRAMENTO)));
        data.setAluno(aluno);
        data.setIdNuvem(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NUVEM)));
        return data;
    }

}
