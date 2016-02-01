package com.farmers_plaza.farmersplaza.farmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CropDetailsActivity extends AppCompatActivity {
    TextView textViewCropName;
    TextView textViewCropDetails;
    String strCropNameSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_detail);
        setUp();
        if(getIntent().getExtras() != null) {
            strCropNameSelected = getIntent().getExtras().getString("cropName");
        }
        retrieveCropDetails();
    }

    private void retrieveCropDetails() {
        ParseQuery<ParseObject> cropQuery = ParseQuery.getQuery("Crop");
        cropQuery.whereEqualTo("cropName", strCropNameSelected);
        cropQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e == null) {
                    textViewCropName.setText(object.getString("cropName"));
                    textViewCropDetails.setText(object.getString("cropDesc"));
                } else {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });

    }

    private void setUp() {
        textViewCropName = (TextView) findViewById(R.id.text_view_crop_name);
        textViewCropDetails = (TextView) findViewById(R.id.text_view_crop_details);
    }
}
