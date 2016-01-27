package com.farmers_plaza.farmersplaza.agriculturist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ImageDialogPrompt;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

public class AgriProfileActivity extends AppCompatActivity {
    private ToolbarSetup toolbarSetup;
    private Button btnChangeImage;
    private ImageDialogPrompt imageDialogPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolbar();
        clickChangeImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {  // From Camera
                Log.e("onActivityResult", "CAMERA");
            } else if (requestCode == 2) {   // From Gallery
                Log.e("onActivityResult", "FILE UPLOAD");
            }
        }
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.profile_toolbar_title), Color.WHITE);
    }


    private void clickChangeImage() {
        btnChangeImage = (Button) findViewById(R.id.btn_change_image);
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialogPrompt = new ImageDialogPrompt(AgriProfileActivity.this,
                        AgriProfileActivity.this);
                imageDialogPrompt.show(getString(R.string.profile_picture_dialog_text));
            }
        });
    }
}
