package com.farmers_plaza.farmersplaza.agriculturist;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.BluetoothConnector;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

import java.util.Set;

public class SoilAnalysisActivity extends AppCompatActivity {
    Button btnResult;
    BluetoothDevice mmDevice;
    ToolbarSetup toolbarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_analysis);
        setupToolbar();
        checkPairedDevice();
        clickResult();
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
}
