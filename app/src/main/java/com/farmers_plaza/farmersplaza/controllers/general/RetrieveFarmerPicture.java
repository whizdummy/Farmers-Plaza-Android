package com.farmers_plaza.farmersplaza.controllers.general;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.farmers_plaza.farmersplaza.R;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class RetrieveFarmerPicture extends AsyncTask<Void, Void, Void> {
    ImageView pictureView;
    ParseQuery<ParseObject> query;
    ParseFile parseFile;
    Bitmap bitmap;
    byte[] data;
    AppCompatActivity activity;

    public RetrieveFarmerPicture(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        pictureView = (ImageView) activity.findViewById(R.id.small_farmer_profile);
        query = ParseQuery.getQuery("Farmer");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    parseFile = object.getParseFile("photo");
                    try {
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                   RetrieveFarmerPicture.this.data = data;
                                } else {
                                    Log.e("IMG RETRIEVAL ERROR", e.getMessage());
                                }
                            }
                        });
                    } catch (NullPointerException ex) {
                        Log.e("NO FILE", ex.getMessage());
                    }
                } else {
                    Log.e("QUERY ERROR", e.getMessage());
                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        pictureView.setImageBitmap(Bitmap.createScaledBitmap(
                bitmap, 40, 40, false
        ));
        pictureView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        pictureView.setAdjustViewBounds(true);
    }
}
