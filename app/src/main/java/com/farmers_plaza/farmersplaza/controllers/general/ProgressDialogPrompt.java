package com.farmers_plaza.farmersplaza.controllers.general;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by djfable02 on 1/27/16.
 */
public class ProgressDialogPrompt {
    ProgressDialog objProgDiag;

    public ProgressDialogPrompt(Context contextObj) {
        objProgDiag = new ProgressDialog(contextObj);
    }

    public void showProgress(String strMessage) {
        objProgDiag.setMessage(strMessage);
        objProgDiag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        objProgDiag.setIndeterminate(true);
        objProgDiag.setCancelable(false);
        objProgDiag.show();
    }

    public void stopProgress() {
        objProgDiag.dismiss();
    }
}
