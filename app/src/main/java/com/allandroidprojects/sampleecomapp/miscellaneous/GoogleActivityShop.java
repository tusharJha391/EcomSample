package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.allandroidprojects.sampleecomapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleActivityShop extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    ProgressBar progressBar;
    private ClusterManager<MyItem> mClusterManager;
    ArrayList markerPoints= new ArrayList();
    ArrayList<String> addresses = new ArrayList<>();
    private GoogleMap mMap;
    ArrayList<LatLng> locations = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_map_shoplist);

        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.map),"Sorry, you're offline", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        } else {
            AddAddresses();


            this.mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (this.mapFragment != null) {
                this.mapFragment.getMapAsync(this);
            }

        }

    }




    private void AddAddresses() {
        addresses.add("Lekhram Market, Atta Market, Pocket E, Sector 27, Noida, Uttar Pradesh 201301");
        addresses.add("shop 2, Near metro, Section 16, Noida, U.P");
        addresses.add("shop 3, Main road, Section 22, Noida, U.P");
        addresses.add("shop 4, Main road, Section 12, Noida, U.P");
        for (int i=0;i<addresses.size();i++) {
            String addressForLatlon = addresses.get(i);
            if (getAddressFromLocation(getApplicationContext(),addressForLatlon) == null) {
                return;
            } else {
                getAddressFromLocation(getApplicationContext(),addressForLatlon);
                Double v = getAddressFromLocation(getApplicationContext(),addressForLatlon).latitude;
                Double p = getAddressFromLocation(getApplicationContext(),addressForLatlon).longitude;
                locations.add(new LatLng(v,p));

            }
        }


    }

    public LatLng getAddressFromLocation(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        LatLng p1 = null;
        try {
            addresses = geocoder.getFromLocationName(strAddress, 5);
            if (addresses == null) {
                return null;
            }

            Address location = addresses.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  p1;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        if (locations.size() == 0) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.map),"You are not connected", Snackbar.LENGTH_LONG);
            snackbar.show();

            return;

        }
        double a = locations.get(0).latitude;
        double b = locations.get(0).longitude;
        LatLng NOIDA = new LatLng(a,b);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(getSharedPreferences("myLatLon", 0).getString("lat", "")), Double.parseDouble(getSharedPreferences("myLatLon", 0).getString("lan", ""))), 8));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NOIDA,12));
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        addItems();
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                markerPoints.add(latLng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(options);
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }




            }
        });*/
    }

    /*public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        *//*@Override
        protected Object doInBackground(Object[] objects) {
            String data = "";
            try {
                data = downloadUrl((String) objects[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
*//*
       *//* @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ParserTask parserTask = new ParserTask();


            parserTask.execute((String) o);
        }*//*

       *//* @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(s);
        }*//*
    }*/

    /*private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for (int i = 0; i < lists.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = lists.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap point = path.get(j);

                    double lat = Double.parseDouble((String) point.get("lat"));
                    double lng = Double.parseDouble((String) point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            //mMap.addPolyline(lineOptions);
            if((points != null ? points.size() : 0) !=0)mMap.addPolyline(lineOptions);



            }
        }*/

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            if (iStream != null)
            iStream.close();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }


    private void addItems() {

        // Set some lat/lng coordinates to start with.
        Geocoder geocoder;
        List<Address> addresses;
        ArrayList<LatLng> locationClutters;
        locationClutters = locations;

        geocoder = new Geocoder(this,Locale.getDefault());
        //double lat = Double.parseDouble(getSharedPreferences("myLatLon", 0).getString("lat", ""));
        //double lng = Double.parseDouble(getSharedPreferences("myLatLon", 0).getString("lan", ""));
        double lat, lng;

        String title = "Shop";
        String snippet = "";

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < locationClutters.size(); i++) {
            title = "Shop";
            snippet = " ";
            lat = locationClutters.get(i).latitude;
            lng = locationClutters.get(i).longitude;

            /*double offset = i / 90d;
            lat = lat + offset;
            lng = lng + offset;*/
            try {
                addresses = geocoder.getFromLocation(lat,lng,1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                //snippet = snippet + city + state + country + postalCode;
                snippet = snippet + address;
            } catch (IOException e) {
                e.printStackTrace();
            }
            title = title + " " + (i+1);


            MyItem offsetItem = new MyItem(lat, lng,title,snippet);
            mClusterManager.addItem(offsetItem);

        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


