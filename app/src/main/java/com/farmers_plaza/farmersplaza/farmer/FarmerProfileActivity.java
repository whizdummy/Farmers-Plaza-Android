package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ImageDialogPrompt;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class FarmerProfileActivity extends AppCompatActivity{

    private Intent intent;
    private ToolbarSetup toolbarSetup;
    private ImageView pictureView;
    private ParseQuery<ParseObject> query;
    private ParseFile parseFile;
    private Bitmap bitmap;
    private Button btnChangeImage;
    private ImageDialogPrompt imageDialogPrompt;
    private Uri selectedImage;
    private Cursor cursor;
    private String picturePath;
    private ByteArrayOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolbar();
        setImageToView();
        clickChangeImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {  // From Camera
                Log.e("onActivityResult", "CAMERA");
                bitmap = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == 2) {   // From Gallery
                Log.e("onActivityResult", "FILE UPLOAD");
                selectedImage = data.getData();
                String[] strArrFilePathColumn = {MediaStore.Images.Media.DATA};
                cursor = getContentResolver().query(selectedImage, strArrFilePathColumn,
                        null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(strArrFilePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                }
                bitmap = BitmapFactory.decodeFile(picturePath);
            }
            savePhotoToDb();
        }
    }

    private void clickChangeImage() {
        btnChangeImage = (Button) findViewById(R.id.btn_change_image);
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialogPrompt = new ImageDialogPrompt(FarmerProfileActivity.this,
                        FarmerProfileActivity.this);
                imageDialogPrompt.show(getString(R.string.profile_picture_dialog_text));
            }
        });
    }

    private void setImageToView() {
        pictureView = (ImageView) findViewById(R.id.img_view_profile_pic);
        query = ParseQuery.getQuery("Agriculturist");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    parseFile = object.getParseFile("photo");
                    parseFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                pictureView.setImageBitmap(bitmap);
                                pictureView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                pictureView.setAdjustViewBounds(true);
                            }
                        }
                    });
                }
            }
        });
    }

    private void savePhotoToDb() {
        outputStream = new ByteArrayOutputStream();
        if(bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        }
        parseFile = new ParseFile(outputStream.toByteArray());
        parseFile.saveInBackground();
        query = ParseQuery.getQuery("Agriculturist");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                object.put("photo", parseFile);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            setImageToView();
                        }
                    }
                });
            }
        });
    }

    private void setUpToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.profile_toolbar_title), Color.WHITE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showIntent(HomeScreenActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showIntent(Class className) {
        intent = new Intent(FarmerProfileActivity.this, className);
        startActivity(intent);
        finish();
    }

}//end FarmerProfileActivity
