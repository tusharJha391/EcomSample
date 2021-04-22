package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopRecyclerView extends AppCompatActivity {

    Context context = this;
    private List<ShopData> shopDataList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RecyclerView mRecyclerView;
    private ShopListAdapter mShopListAdapter;
    FrameLayout frameLayout;
    boolean network;
    SendData sendData;
    double getDoubleLat;
    double getDoubleLon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_recycler);
        this.mRecyclerView = findViewById(R.id.shop_recycler_list);
        this.frameLayout = findViewById(R.id.recycler_container);
        this.layoutManager = new LinearLayoutManager(context);
        this.mRecyclerView.setLayoutManager(this.layoutManager);
        this.mRecyclerView.setHasFixedSize(true);
        prepareShopDataList();

        mShopListAdapter = new ShopListAdapter(shopDataList, new ShopListAdapter.LocationDetailAdapterListener() {
            @Override
            public void onClickShopLocation(View v, int position) {
                if (!isNetworkAvailable()) return;
                Toast.makeText(context,"Button Clicked at" + position, Toast.LENGTH_SHORT).show();
                String addressShop = shopDataList.get(position).getShopAddress();
                getLocationFromAddress(getApplicationContext(),addressShop);
                Intent intent = new Intent(getApplicationContext(),GoogleMapActivity.class);
                //Intent intent = new Intent(getApplicationContext(),HereMapActivity.class);
                intent.putExtra("shopLocation", addressShop);
                intent.putExtra("shopLat",getLocationFromAddress(getApplicationContext(),addressShop).latitude);
                intent.putExtra("shopLong",getLocationFromAddress(getApplicationContext(),addressShop).longitude);
                intent.putExtra("activityName",ShopRecyclerView.class.getSimpleName());
                startActivity(intent);
                }

            @Override
            public void onRowClick(View v, int position) {
                if (!isNetworkAvailable()) return;
                /*Toast.makeText(context,"Button clicked at" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ShopDetailScreen.class);
                intent.putExtra("lat",getLocationFromAddress(getApplicationContext(),String.valueOf(shopDataList.get(position).getShopAddress())).latitude);
                intent.putExtra("lon",getLocationFromAddress(getApplicationContext(),String.valueOf(shopDataList.get(position).getShopAddress())).longitude);
                startActivity(intent);*/
                Activity activity = (Activity) context;
                /* FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ShopDetailScreen fragment = new ShopDetailScreen();
                fragmentTransaction.add(R.id.my_container,fragment);
                fragmentTransaction.commit();*/
               /* FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ShopDetailScreen fragment = new ShopDetailScreen();
                fragmentTransaction.add(R.id.my_container,fragment);
                fragmentTransaction.commit();*/
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.recycler_container, new ShopDetailScreen()).commit();
                frameLayout.setVisibility(View.VISIBLE);
                getDoubleLat = getLocationFromAddress(context,String.valueOf(shopDataList.get(position).getShopAddress())).latitude;
                getDoubleLon =getLocationFromAddress(context,String.valueOf(shopDataList.get(position).getShopAddress())).longitude;
                /*if (sendData == null) return;
                sendData.sendInformation(Double.valueOf(getLocationFromAddress(context,String.valueOf(shopDataList.get(position).getShopAddress())).latitude)
                        ,Double.valueOf(getLocationFromAddress(context,String.valueOf(shopDataList.get(position).getShopAddress())).longitude));*/

                }
        });
        mRecyclerView.setAdapter(mShopListAdapter);
        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar.make(mRecyclerView,"Sorry, you're offline", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
        getDoubleLocation();
    }

    interface SendData {
        void sendInformation(Double d1, Double d2);
    }


    private void prepareShopDataList() {
        ShopData shopData = new ShopData(R.drawable.store,"Shop Number 1"
                ,"Lekhram Market, Atta Market, Pocket E, Sector 27, Noida, Uttar Pradesh 201301", 3.5f);
        shopDataList.add(shopData);
        shopData = new ShopData(R.drawable.store,"Shop Number 2"
                ,"shop 2, Near metro, Section 16, Noida, U.P", 3.0f);
        shopDataList.add(shopData);
        shopData = new ShopData(R.drawable.store,"Shop Number 3"
                ,"shop 3, Main road, Section 22, Noida, U.P", 2.5f);
        shopDataList.add(shopData);
        shopData = new ShopData(R.drawable.store,"Shop Number 4"
                ,"shop 4, Main road, Section 12, Noida, U.P", 4.5f);
        shopDataList.add(shopData);

        }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        LatLng p1 = null;
        try {
            addresses = geocoder.getFromLocationName(strAddress, 5);
            if (addresses == null) {
                return null;
            }

            Address location = addresses.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  p1;
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public double[] getDoubleLocation() {
        double locationLat = getDoubleLat;
        double locationLon = getDoubleLon;
        double[] arr = new double[2];
        arr[0] = locationLat;
        arr[1] = locationLon;

        return arr;

    }



    @Override
    public void onBackPressed() {
        if (frameLayout.getVisibility() == View.VISIBLE) {
            frameLayout.setVisibility(View.GONE);

        } else {
            super.onBackPressed();

        }

    }
}
