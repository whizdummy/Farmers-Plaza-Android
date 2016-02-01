package com.farmers_plaza.farmersplaza.farmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.CropsRecyclerView;
import com.farmers_plaza.farmersplaza.controllers.general.FarmRecyclerViewAdapter;

import java.util.ArrayList;

public class CropBrowseResultActivity extends AppCompatActivity {
    ArrayList<String> cropNameList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CropsRecyclerView cropsRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    private void setUpView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farm_list);
        linearLayoutManager = new LinearLayoutManager(this);
        cropsRecyclerView = new CropsRecyclerView(cropNameList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cropsRecyclerView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_crops_result);
        if(getIntent().getExtras() != null) {
            for(String crop : getIntent().getExtras().getStringArrayList("crops")) {
                cropNameList.add(crop);
                Log.e("CRAP", crop);
            }
        }
    }
}
