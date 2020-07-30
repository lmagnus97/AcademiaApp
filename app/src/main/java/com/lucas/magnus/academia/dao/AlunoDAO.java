package com.lucas.magnus.academia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lucas.magnus.academia.db.AbstractDB;
import com.lucas.magnus.academia.db.DBOpenHelper;
import com.lucas.magnus.academia.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends AbstractDB {

    public static final String
            TABLE_NAME = "tb_aluno";

    private final String[] colunas =
            {
                    COLUMN_CODIGO_ALUNO,
                    COLUMN_ALUNO,
                    COLUMN_DATA_NASCIMENTO,
                    COLUMN_SEXO,
                    COLUMN_TELEFONE,
                    COLUMN_CELULAR,
                    COLUMN_EMAIL,
                    COLUMN_OBSERVACAO,
                    COLUMN_ENDERECO,
                    COLUMN_NUMERO,
                    COLUMN_COMPLEMENTO,
                    COLUMN_BAIRRO,
                    COLUMN_CIDADE,
                    COLUMN_ESTADO,
                    COLUMN_PAIS,
                    COLUMN_CEP,
                    COLUMN_ATIVO,
                    COLUMN_ID_NUVEM
            };

    public static final String
            COLUMN_CODIGO_ALUNO = "codigo_aluno",
            COLUMN_ALUNO = "aluno",
            COLUMN_DATA_NASCIMENTO = "data_nascimento",
            COLUMN_SEXO = "sexo",
            COLUMN_TELEFONE = "telefone",
            COLUMN_CELULAR = "celular",
            COLUMN_EMAIL = "email",
            COLUMN_OBSERVACAO = "observacao",
            COLUMN_ENDERECO = "endereco",
            COLUMN_NUMERO = "numero",
            COLUMN_COMPLEMENTO = "complemento",
            COLUMN_BAIRRO = "bairro",
            COLUMN_CIDADE = "cidade",
            COLUMN_ESTADO = "estado",
            COLUMN_PAIS = "pais",
            COLUMN_CEP = "cep",
            COLUMN_ATIVO = "ativo",
            COLUMN_ID_NUVEM = "ID_NUVEM";


    public static final String
            CREATE_TABLE = "create table IF NOT EXISTS " + TABLE_NAME
            + "("
            + COLUMN_CODIGO_ALUNO + " integer primary key autoincrement, "
            + COLUMN_ALUNO + " text not null, "
            + COLUMN_DATA_NASCIMENTO + " long, "
            + COLUMN_SEXO + " text not null, "
            + COLUMN_TELEFONE + " text, "
            + COLUMN_CELULAR + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_OBSERVACAO + " text, "
            + COLUMN_ENDERECO + " text not null, "
            + COLUMN_NUMERO + " integer not null, "
            + COLUMN_COMPLEMENTO + " text not null, "
            + COLUMN_BAIRRO + " text not null, "
            + COLUMN_CIDADE + " text not null, "
            + COLUMN_ESTADO + " text not null, "
            + COLUMN_PAIS + " text not null, "
            + COLUMN_CEP + " text not null, "
            + COLUMN_ATIVO + " integer default 1, "
            + COLUMN_ID_NUVEM + " integer default 0, "
            + " FOREIGN KEY (" + COLUMN_CIDADE + ", " + COLUMN_ESTADO + ", " + COLUMN_PAIS + ") "
            + " REFERENCES " + CidadeDAO.TABLE_NAME + " (" + COLUMN_CIDADE + ", " + COLUMN_ESTADO + ", " + COLUMN_PAIS + ")"
            + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public AlunoDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public List<Aluno> selectAll() {
        List<Aluno> contatos = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    AlunoDAO.TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 1",
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                contatos.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return contatos;
    }

    public List<Aluno> selectPesquisa(String pesquisa) {
        List<Aluno> contatos = new ArrayList<>();

        try {
            open();

            Cursor cursor = database.query(
                    AlunoDAO.TABLE_NAME,
                    colunas,
                    COLUMN_ATIVO + " = 1 and " + COLUMN_ALUNO + " like '%" + pesquisa + "%'",
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                contatos.add(toObject(cursor));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("DATABASE SELECT ALL ERROR " + e.getMessage());
        } finally {
            close();
        }

        return contatos;
    }

    public Aluno select(Integer codAluno) {
        Aluno result = new Aluno();

        try {
            open();

            Cursor cursor = database.query(
                    AlunoDAO.TABLE_NAME,
                    colunas,
                    COLUMN_CODIGO_ALUNO + " = ?",
                    new String[]{String.valueOf(codAluno)},
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

    public long insert(Aluno aluno) {

        long rowsAffect = 0;

        try {
            open();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ALUNO, aluno.getAluno().trim());
            values.put(COLUMN_BAIRRO, aluno.getBairro().trim());
            values.put(COLUMN_CELULAR, aluno.getCelular().trim());
            values.put(COLUMN_CEP, aluno.getCep().trim());
            values.put(COLUMN_CIDADE, aluno.getCidade().trim());
            values.put(COLUMN_COMPLEMENTO, aluno.getComplemento().trim());
            values.put(COLUMN_EMAIL, aluno.getEmail().trim());
            values.put(COLUMN_ENDERECO, aluno.getEndereco().trim());
            values.put(COLUMN_ESTADO, aluno.getEstado().trim());
            values.put(COLUMN_NUMERO, aluno.getNumero().trim());
            values.put(COLUMN_OBSERVACAO, aluno.getObservacao().trim());
            values.put(COLUMN_PAIS, aluno.getPais().trim());
            values.put(COLUMN_SEXO, aluno.getSexo().trim());
            values.put(COLUMN_TELEFONE, aluno.getTelefone().trim());
            values.put(COLUMN_DATA_NASCIMENTO, aluno.getDataNascimento());

            rowsAffect = database.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.i("INFOLOG", "Erro na inserção: " + e.getMessage());
        } finally {
            close();
        }


        Log.i("INFOLOG", "LINHAS AFETADAS: " + String.valueOf(rowsAffect));

        return rowsAffect;
    }

    public long update(Aluno data) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ALUNO, data.getAluno().trim());
            values.put(COLUMN_BAIRRO, data.getBairro().trim());
            values.put(COLUMN_CELULAR, data.getCelular().trim());
            values.put(COLUMN_CEP, data.getCep().trim());
            values.put(COLUMN_CIDADE, data.getCidade().trim());
            values.put(COLUMN_COMPLEMENTO, data.getComplemento().trim());
            values.put(COLUMN_EMAIL, data.getEmail().trim());
            values.put(COLUMN_ENDERECO, data.getEndereco().trim());
            values.put(COLUMN_ESTADO, data.getEstado().trim());
            values.put(COLUMN_NUMERO, data.getNumero().trim());
            values.put(COLUMN_OBSERVACAO, data.getObservacao().trim());
            values.put(COLUMN_PAIS, data.getPais().trim());
            values.put(COLUMN_SEXO, data.getSexo().trim());
            values.put(COLUMN_TELEFONE, data.getTelefone().trim());
            values.put(COLUMN_DATA_NASCIMENTO, data.getDataNascimento());

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_CODIGO_ALUNO + "= ?",
                    new String[]{String.valueOf(data.getCodigoAluno())}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public long updateIdNuvem(Integer idAluno, Integer idNuvem) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_NUVEM, idNuvem);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_CODIGO_ALUNO + " = ?",
                    new String[]{String.valueOf(idAluno)}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Long delete(final Integer codigoAluno) {
        long rowAffect = 0;

        try {
            open();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ATIVO, 0);

            rowAffect = database.update(
                    TABLE_NAME, values,
                    COLUMN_CODIGO_ALUNO + "= ?",
                    new String[]{String.valueOf(codigoAluno)}
            );

        } catch (Exception e) {
            System.out.println("DATABASE UPDATE ERROR " + e.getMessage());
        } finally {
            close();
        }

        return rowAffect;
    }

    public Aluno toObject(Cursor cursor) {
        Aluno aluno = new Aluno();

        aluno.setCodigoAluno(cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGO_ALUNO)));
        aluno.setAluno(cursor.getString(cursor.getColumnIndex(COLUMN_ALUNO)));
        aluno.setBairro(cursor.getString(cursor.getColumnIndex(COLUMN_BAIRRO)));
        aluno.setCelular(cursor.getString(cursor.getColumnIndex(COLUMN_CELULAR)));
        aluno.setCep(cursor.getString(cursor.getColumnIndex(COLUMN_CEP)));
        aluno.setCidade(cursor.getString(cursor.getColumnIndex(COLUMN_CIDADE)));
        aluno.setComplemento(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLEMENTO)));
        aluno.setDataNascimento(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_NASCIMENTO)));
        aluno.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
        aluno.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO)));
        aluno.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO)));
        aluno.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
        aluno.setObservacao(cursor.getString(cursor.getColumnIndex(COLUMN_OBSERVACAO)));
        aluno.setPais(cursor.getString(cursor.getColumnIndex(COLUMN_PAIS)));
        aluno.setSexo(cursor.getString(cursor.getColumnIndex(COLUMN_SEXO)));
        aluno.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE)));
        aluno.setIdNuvem(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NUVEM)));

        return aluno;
    }

}
