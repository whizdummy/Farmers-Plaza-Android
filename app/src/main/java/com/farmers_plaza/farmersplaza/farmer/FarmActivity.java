package com.farmers_plaza.farmersplaza.farmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.service.FarmService;
import com.farmers_plaza.farmersplaza.service.FarmerService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FarmActivity extends AppCompatActivity{

    private ListView listViewFarm;
    private ArrayAdapter<Farm> adapterFarm;
    private List<Farm> listFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_farms);
    }//end onCreate

    public void setUp(){


    }//end setUp



}//end FarmActivity
