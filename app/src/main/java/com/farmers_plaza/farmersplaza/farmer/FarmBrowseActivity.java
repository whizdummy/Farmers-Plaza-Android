package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.FarmRecyclerViewAdapter;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FarmBrowseActivity extends AppCompatActivity {
    ToolbarSetup toolbarSetup;
    Button btnAddFarm;
    List<FarmAdapter> farmList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FarmRecyclerViewAdapter farmRecyclerViewAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        getFarmersFarm();
    }

    private void getFarmersFarm() {
        ParseQuery<ParseObject> farmQuery = ParseQuery.getQuery("Farm");
        farmQuery.whereEqualTo("farmer", ParseUser.getCurrentUser());
        (new FarmFetch(farmQuery)).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_farms);
        setUp();
        addFarmClick();
        setUpFarmList();
    }

    private void setUpFarmList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farm_list);
        linearLayoutManager = new LinearLayoutManager(this);
        farmRecyclerViewAdapter = new FarmRecyclerViewAdapter(farmList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(farmRecyclerViewAdapter);
    }

    private void addFarmClick() {
        btnAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmBrowseActivity.this, AddFarmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUp() {
        btnAddFarm = (Button) findViewById(R.id.btn_add_farm);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar_farm), this, this);
        toolbarSetup.initialize(getString(R.string.browse_farm_toolbar_title), Color.WHITE);
    }

    private class FarmFetch extends AsyncTask<Void, Void, Void> {
        ParseQuery<ParseObject> parseQuery;

        FarmFetch(ParseQuery<ParseObject> parseQuery) {
            this.parseQuery = parseQuery;
        }

        @Override
        protected Void doInBackground(Void... params) {
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    for(ParseObject object : objects) {
                        farmList.add(new FarmAdapter(object.getString("farmName")));
                    }
                }
            });
            return null;
        }
    }
}
