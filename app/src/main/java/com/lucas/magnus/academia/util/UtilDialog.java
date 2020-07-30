package com.lucas.magnus.academia.util;

import android.content.Context;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class UtilDialog {

    static public SweetAlertDialog showDialog(Context context, Integer alertType, String title, String contentText) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, alertType)
                .setTitleText(title)
                .setContentText(contentText);

        alertDialog.show();
        return alertDialog;
    }

}
