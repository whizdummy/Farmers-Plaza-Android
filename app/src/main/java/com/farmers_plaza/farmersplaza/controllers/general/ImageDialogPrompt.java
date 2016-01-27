package com.farmers_plaza.farmersplaza.controllers.general;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.farmers_plaza.farmersplaza.R;

public class ImageDialogPrompt {
    AlertDialog.Builder                 builder;
    private Intent                      intent;
    private Context                     context;
    final int CAMERA_REQUEST = 1;
    final int FILE_SELECT_REQUEST = 2;
    AppCompatActivity activity;

    public ImageDialogPrompt(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void show(String title) {
        builder = new AlertDialog.Builder(this.context);
        builder.setTitle(title);
        builder.setItems(R.array.image_dialog_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(intent, CAMERA_REQUEST);
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        activity.startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), FILE_SELECT_REQUEST);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }
}
