package com.lucas.magnus.academia.util;

import android.content.Context;
import android.util.Log;

import com.lucas.magnus.academia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT);

    public static Date convertToDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertToSexo(String sexo, Context context) {
        if(sexo.equals(context.getResources().getString(R.string.sexo_masculino))){
            return "M";
        }else if(sexo.equals(context.getResources().getString(R.string.sexo_feminino))){
            return "F";
        }else if(sexo.equals(context.getResources().getString(R.string.sexo_outro))){
            return "O";
        }else{
            return "";
        }
    }

    public static String calendarToString(Calendar calendar){
        try {
            return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        }catch (Exception e){
            Log.i("INFOLOG", "ERRO AO CONVERTER DATA: " + e.getMessage());
            return null;
        }
    }

    public static Calendar stringToCalendar(String data){
       try {
           if(data == null){
               return null;
           }

           String[] sp = data.split("/");
           Calendar calendar = Calendar.getInstance();
           calendar.set(Integer.parseInt(sp[2]), (Integer.parseInt(sp[1]) - 1), Integer.parseInt(sp[0]));
           return calendar;
       }catch (Exception ignored){
           return null;
       }
    }

    public static String convertToMoney(Double value){
        return String.format(Locale.ROOT,"R$%.2f", value).replace(".",",");
    }

}
