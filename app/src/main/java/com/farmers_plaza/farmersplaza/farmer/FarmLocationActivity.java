package com.farmers_plaza.farmersplaza.farmer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.farmers_plaza.farmersplaza.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class FarmLocationActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private ParseQuery<ParseObject> farmQuery;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_location);
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        mapView.getMapAsync(this);
        getFarmersFarm();
    }

    private void getFarmersFarm() {
        getCurrentFarmer();
    }

    private void getCurrentFarmer() {
        ParseQuery<ParseObject> currentFarmerQuery = ParseQuery.getQuery("Farmer");
        currentFarmerQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        currentFarmerQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                farmQuery = ParseQuery.getQuery("Farm");
                farmQuery.whereEqualTo("farmer", ParseObject.createWithoutData("Farmer", object.getObjectId()));
                Log.e("ID", object.getObjectId());
                (new FetchFarm()).execute();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private class FetchFarm extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            farmQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null) {
                        LatLng farmLocation;
                        for(ParseObject object : objects) {
                            farmLocation =
                                    new LatLng(object.getParseGeoPoint("farmerGeoPoint").getLatitude()
                                    , object.getParseGeoPoint("farmerGeoPoint").getLongitude());
                            marker = map.addMarker(new MarkerOptions().position(farmLocation)
                                    .title(object.getString("farmName")));

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(farmLocation, 15f));
                        }
                    }
                }
            });

            return null;
        }
    }
}
