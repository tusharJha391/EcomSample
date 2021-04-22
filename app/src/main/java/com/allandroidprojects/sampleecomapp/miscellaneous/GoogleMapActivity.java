package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.drawroutemap.DrawMarker;
import com.allandroidprojects.sampleecomapp.drawroutemap.DrawRouteMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;

    ShopRecyclerView shopRecyclerView;
    Bundle bundle;
    GoogleMap mGoogleMap;
    Boolean isClicked;
    private Toolbar toolbar;
    private ProgressBar mProgressbar;
    Marker markerVisible;
    float latitude;
    float longitude;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map);
        bundle = getIntent().getExtras();
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        mProgressbar = (ProgressBar) findViewById(R.id.map_loading);
        this.mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (this.mapFragment != null) {
            this.mapFragment.getMapAsync(this);
        }
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mProgressbar.setVisibility(View.VISIBLE);
    }

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (isClicked != null && isClicked) {
            markerVisible.remove();
            Double latDoubleFrom = Double.valueOf(getSharedPreferences("myLatLon", 0).getString("lat", ""));
            Double longDoubleFrom = Double.valueOf(getSharedPreferences("myLatLon", 0).getString("lan", ""));
            Double latDoubleTo = bundle.getDouble("shopLat");
            Double longDoubleTo = bundle.getDouble("shopLong");
            LatLng origin = new LatLng(latDoubleFrom, longDoubleFrom);
            LatLng destination = new LatLng(latDoubleTo, longDoubleTo);
            String key = "AIzaSyAz-REH4pij_z7BEk_inoa03CUaa7K_-CI";
            DrawRouteMaps.getInstance(this).draw(origin,destination,googleMap);
            DrawMarker.getInstance(this).draw(googleMap,origin,R.drawable.navigation,"Origin Location");
            DrawMarker.getInstance(this).draw(googleMap,destination,R.drawable.pin,"Destination Location");
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(origin)
                    .include(destination).build();
            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getSize(displaySize);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
            /*GoogleMapRoute md = new GoogleMapRoute();

            Document doc = md.getDocument(origin, destination, GoogleMapRoute.MODE_DRIVING, key);
            if (doc == null) return;
            ArrayList<LatLng> directionPoint = md.getDirection(doc);
            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
            for(int i = 0 ; i < directionPoint.size() ; i++) {
                rectLine.add(directionPoint.get(i));
            }
            //mapFragment.addPolyline(rectLine);
            googleMap.addPolyline(rectLine);*/
            mProgressbar.setVisibility(View.INVISIBLE);



        } else {

            if (bundle != null && bundle.getString("activityName") != null && Objects.equals(bundle.getString("activityName"), "ShopRecyclerView")) {
                latitude = (float) bundle.getDouble("shopLat");
                longitude = (float) bundle.getDouble("shopLong");
                LatLng latLng = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Shop Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Shop Location")
                        .snippet(bundle.getString("shopLocation")));
                marker.showInfoWindow();
                markerVisible=marker;
                mProgressbar.setVisibility(View.INVISIBLE);


            } else {
                String latitude = String.valueOf(this.getSharedPreferences("myLatLon", 0).getString("lat", ""));
                String longitude = String.valueOf(this.getSharedPreferences("myLatLon", 0).getString("lan", ""));
                if (latitude.equalsIgnoreCase("") && longitude.equalsIgnoreCase(""))
                    return;
                LatLng latLng = new LatLng((double) Float.parseFloat(latitude), (double) Float.parseFloat(longitude));
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Your Position"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Your position")
                        .snippet(getSharedPreferences("myLatLon", 0).getString("yourAddress", "")));
                marker.showInfoWindow();
                mProgressbar.setVisibility(View.INVISIBLE);

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundle != null && bundle.getString("activityName") != null && Objects.equals(bundle.getString("activityName"), "ShopRecyclerView")) {
            getMenuInflater().inflate(R.menu.item_shoplistmap, menu);

        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.location) {
            Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
            isClicked = true;
            onMapReady(mGoogleMap);


           /* DrawRoute.getInstance(this,GoogleMapActivity.this).setFromLatLong(latDoubleFrom,longDoubleFrom)
                    .setToLatLong(latDoubleTo,longDoubleTo).setGmapAndKey("AIzaSyAz-REH4pij_z7BEk_inoa03CUaa7K_-CI",googleMap).run();*/
            return true;
        }
        if (id == R.id.get_direction) {
            Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),HereMapActivity.class);
            intent.putExtra("clickedOrNot",true);
            intent.putExtra("getLat",latitude);
            intent.putExtra("getLong",longitude);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onDestroy() {
        isClicked = false;
        super.onDestroy();
    }
}
