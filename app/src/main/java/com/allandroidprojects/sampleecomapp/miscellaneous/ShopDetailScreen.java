package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Objects;

public class ShopDetailScreen extends Fragment implements OnMapReadyCallback {
    //private TomtomMap map;
    GoogleMap map;
    Bundle bundle;
    Double getLat;
    Double getLon;
    double[] loc_array = new double[2];
    SupportMapFragment mapFragment = null;
    String latitudeString;
    String longitudeString;
    double fromLat;
    double fromLon;
    LatLng shopLatLon;
    double calculatedDistance;
    int distance;



    public ShopDetailScreen() {
        //
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_layout_detail,container,false);
        TextView textView_name_shop = (TextView)view.findViewById(R.id.text_shop_name);
        TextView textView_name_location = (TextView)view.findViewById(R.id.shop_address_short);
        ImageView image_shop = (ImageView)view.findViewById(R.id.shop_image_large);
        TextView text_description = (TextView)view.findViewById(R.id.shop_description);
        TextView text_distance = (TextView)view.findViewById(R.id.shop_address_distance);
        if (isNetworkAvailable())
        if (getFragmentManager() != null) {
            this.mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map_fragment);
        }

        //getLat = bundle.getDouble("lat");
        //getLon = bundle.getDouble("lon");
        /*MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getAsyncMap(this);*/
        loc_array = ((ShopRecyclerView)Objects.requireNonNull(getActivity())).getDoubleLocation();
        getLat = loc_array[0];
        getLon = loc_array[1];
        SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("myLatLon", 0);
        latitudeString = sharedPref.getString("lat","");
        longitudeString = sharedPref.getString("lan","");
        //fromLat = Double.parseDouble(latitudeString);
        //fromLon = Double.parseDouble(longitudeString);
        fromLat = (!latitudeString.equalsIgnoreCase("")) ? Double.parseDouble(latitudeString) : 0.0;
        fromLon = (!longitudeString.equalsIgnoreCase("")) ? Double.parseDouble(longitudeString) : 0.0;
        LatLng from = new LatLng(fromLat,fromLon);
        shopLatLon = new LatLng(getLat, getLon);



        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        calculatedDistance = SphericalUtil.computeDistanceBetween(from,shopLatLon);
        distance = (int) calculatedDistance;
        text_distance.setText(String.valueOf(distance) + " m");



        return view;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        /*googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        googleMap.addMarker(new MarkerOptions().position(shopLatLon).title("Shop Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(shopLatLon));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(getLat,getLon))
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Marker marker = googleMap.addMarker(new MarkerOptions().position(shopLatLon).title("Shop Location"));
        marker.showInfoWindow();
    }
    public boolean isNetworkAvailable() {
        /*ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();*/
        if (getActivity() == null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    /*@Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        *//*this.map = tomtomMap;
        LatLng amsterdam = new LatLng(getLat, getLon);

        SimpleMarkerBalloon balloon = new SimpleMarkerBalloon("Amsterdam");

        tomtomMap.addMarker(new MarkerBuilder(amsterdam).markerBalloon(balloon));

        tomtomMap.centerOn(CameraPosition.builder(amsterdam).zoom(7).build());*//*

    }*/
}
