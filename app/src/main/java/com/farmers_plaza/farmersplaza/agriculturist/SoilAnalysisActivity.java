package com.farmers_plaza.farmersplaza.agriculturist;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ProgressDialogPrompt;
import com.farmers_plaza.farmersplaza.controllers.general.SoilAnalysisRecyclerViewAdapter;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;
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
    ParseQuery<ParseObject> farmQuery;

    @Override
    protected void onResume() {
        super.onResume();
        checkPairedDevice();
        getFarmList();
    }

    private void getFarmList() {
        farmQuery = ParseQuery.getQuery("Farm");
        (new FarmFetch()).execute();
    }

    List<FarmAdapter> farmList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    SoilAnalysisRecyclerViewAdapter farmRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_analysis);
        setupToolbar();
//        clickResult();
    }

    private void setUpFarmList() {
        Log.e("PUMASOK", "PUMASOK");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farm_list_analysis);
        linearLayoutManager = new LinearLayoutManager(this);
        farmRecyclerViewAdapter = new SoilAnalysisRecyclerViewAdapter(farmList, this, mmDevice);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(farmRecyclerViewAdapter);
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



//    private void clickResult() {
//        btnResult = (Button) findViewById(R.id.btn_check_result);
//        btnResult.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                (new BluetoothConnector(SoilAnalysisActivity.this)).execute(mmDevice);
//            }
//        });
//    }

    private class FarmFetch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            farmQuery.include("farmer");
            farmQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : objects) {
                            farmList.add(new FarmAdapter(object.getObjectId(),
                                    object.getString("farmName"),
                                    String.format("%s, %s %s",
                                    object.getParseObject("farmer").getString("lastName"),
                                    object.getParseObject("farmer").getString("firstName"),
                                    object.getParseObject("farmer").getString("middleName"))));
                        }
                        setUpFarmList();
                    } else {
                        Log.e("ERROR", e.getMessage());
                    }
                }
            });
            return null;
        }
    }
}
