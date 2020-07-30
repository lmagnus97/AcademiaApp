package com.lucas.magnus.academia.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilShared{

    public static final String SHARED_TABLE = "shared_usuario";
    public static final String SHARED_KEY = "id_usuario";

    public static void put(Integer idUsuario, Context context) {
        SharedPreferences ss = context.getSharedPreferences(SHARED_TABLE, 0);
        SharedPreferences.Editor edit = ss.edit();
        edit.clear();
        edit.putInt(SHARED_KEY, idUsuario);
        edit.apply();
    }

    public static Integer getIdUsuario(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_TABLE, 0);
        return settings.getInt(SHARED_KEY, -1);
    }

    public static void logoutUsuario(Context context) {
        SharedPreferences ss = context.getSharedPreferences(SHARED_TABLE, 0);
        SharedPreferences.Editor edit = ss.edit();
        edit.clear();
        edit.putInt(SHARED_KEY, -1);
        edit.apply();
    }
}
