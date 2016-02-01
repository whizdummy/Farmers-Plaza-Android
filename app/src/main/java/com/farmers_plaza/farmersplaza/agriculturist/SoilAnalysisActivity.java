package com.farmers_plaza.farmersplaza.agriculturist;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.BluetoothConnector;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SoilAnalysisActivity extends AppCompatActivity {
    Button btnResult;
    BluetoothDevice mmDevice;
    ToolbarSetup toolbarSetup;
    Spinner spinner;
    ArrayList<String> farmList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_analysis);
        setUpSpinner();
        setupToolbar();
        checkPairedDevice();
        clickResult();
    }

    private void setUpSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner_farm_list);
        (new FetchFarm()).execute();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_template
        , R.id.empty_text_view_farm_list, farmList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private void checkPairedDevice() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("SOIL_TESTER")) {
                    mmDevice = device;
                    break;
                }
            }
        }
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.soil_testing_toolbar_title), Color.WHITE);
    }

    private void clickResult() {
        btnResult = (Button) findViewById(R.id.btn_check_result);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new BluetoothConnector(SoilAnalysisActivity.this)).execute(mmDevice);
            }
        });
    }

    private class FetchFarm extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> farmQuery = ParseQuery.getQuery("Farm");
            farmQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    for (ParseObject object : objects) {
                        farmList.add(object.getString("farmName"));
                    }
                }
            });
            return null;
        }
    }
}
