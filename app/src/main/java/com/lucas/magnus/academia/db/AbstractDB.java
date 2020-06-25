package com.lucas.magnus.academia.db;
import android.database.sqlite.SQLiteDatabase;

public class AbstractDB {

    protected SQLiteDatabase database;
    protected DBOpenHelper dbOpenHelper;

    protected final void open() {
        database = dbOpenHelper.getWritableDatabase();
    }

    protected final void close() {
        dbOpenHelper.close();
    }

}
