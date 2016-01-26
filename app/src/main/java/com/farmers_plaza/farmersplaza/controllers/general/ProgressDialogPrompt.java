package com.farmers_plaza.farmersplaza.controllers.general;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogPrompt extends ProgressDialog {



    public ProgressDialogPrompt(Context contextObj) {
        super(contextObj);
    }

    public void showProgress(String strMessage) {
        setMessage(strMessage);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setIndeterminate(true);
        setCancelable(false);
        show();
    }

    public void stopProgress() {
        dismiss();
    }
}
