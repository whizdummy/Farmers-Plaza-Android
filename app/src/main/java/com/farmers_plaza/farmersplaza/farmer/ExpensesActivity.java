package com.farmers_plaza.farmersplaza.farmer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.FarmBrowseCropRecyclerViewAdapter;
import com.farmers_plaza.farmersplaza.controllers.general.FarmListExpenseRecyclerViewAdapter;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    List<FarmAdapter> farmList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FarmListExpenseRecyclerViewAdapter farmRecyclerViewAdapter;
    ParseQuery<ParseObject> farmQuery;
    String farmerId;

    @Override
    protected void onResume() {
        super.onResume();
        getFarmerId();
    }

    private void getFarmerId() {
        ParseQuery<ParseObject> parseObject = ParseQuery.getQuery("Farmer");
        parseObject.whereEqualTo("user", ParseUser.getCurrentUser());
        parseObject.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                farmerId = object.getObjectId();
                Log.e("farmerId", farmerId);
                getFarmersFarm();
            }
        });
    }

    private void getFarmersFarm() {
        farmQuery = ParseQuery.getQuery("Farm");
        farmQuery.include("farmer");
        farmQuery.whereEqualTo("farmer", ParseObject.createWithoutData("Farmer", farmerId));
        farmQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        farmList.add(new FarmAdapter(object.getString("farmName")));
                    }
                    setUpFarmList();
                    Log.e("MERON", "Meron");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
    }

    private void setUpFarmList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farm_list_expense);
        linearLayoutManager = new LinearLayoutManager(this);
        farmRecyclerViewAdapter = new FarmListExpenseRecyclerViewAdapter(farmList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(farmRecyclerViewAdapter);
    }
}
