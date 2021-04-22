package com.allandroidprojects.sampleecomapp.startup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.authentication.SignInActivity;
import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;
import com.allandroidprojects.sampleecomapp.fragments.EditFragment;
import com.allandroidprojects.sampleecomapp.fragments.ImageListFragment;
import com.allandroidprojects.sampleecomapp.miscellaneous.CategoriesActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.EmptyActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.ExpandedMenuModel;
import com.allandroidprojects.sampleecomapp.miscellaneous.GoogleActivityShop;
import com.allandroidprojects.sampleecomapp.miscellaneous.GoogleMapActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.GridViewImageTextActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.GridView_categories;
import com.allandroidprojects.sampleecomapp.miscellaneous.HereMapActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.ImageSliderActivity;
import com.allandroidprojects.sampleecomapp.miscellaneous.ProfileDetailFragment;
import com.allandroidprojects.sampleecomapp.notification.NotificationCountSetClass;
import com.allandroidprojects.sampleecomapp.options.CartListActivity;
import com.allandroidprojects.sampleecomapp.options.SearchResultActivity;
import com.allandroidprojects.sampleecomapp.options.WishlistActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EditFragment.OnFragmentInteractionListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static final String[] GRID_DATA = new String[]{"Offers", "Electronics", "Lifestyle", "Home Application", "Books & More", "Health", "Sports & Fitness", "Kids' Fashion", "Grocery", "Bags & Luggage", "Art & Handicraft"};
    static final String[] electronics_spinner = new String[]{"list 1", "list 2", "list 3", "list 4"};
    public static String lat = "";
    public static String lon = "";
    public static int notificationCountCart = 0;
    static TabLayout tabLayout;
    static ViewPager viewPager;
    final String PREFS_NAME = "MyPrefsFile";
    ArrayAdapter<String> adapter;
    BottomNavigationView bottomNavigationView;
    RelativeLayout rlConnectivity;
    String cityName = "";
    DrawerLayout drawer;
    FirebaseFirestore db;
    ExpandableListView expandableList;
    Geocoder geocoder;
    GridView gridView;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    Map<String, Object> stringHashMap;
    String getData;


    List<ExpandedMenuModel> listDataHeader;
    LocationManager locationManager;
    ExpandableListAdapter mMenuAdapter;
    Spinner mSpinner;
    Location mobileLocation;
    TextView navUsername;
    TextView appUserName;
    String provider;
    SharedPreferences sharedPref;
    LocationListener locationOnPause;
    Button tryAgain;
    int tryInt = 0;
    List<Integer> position_int = new ArrayList<Integer>();
    boolean getBoolean;
    Button action_location;
    FrameLayout frameLayout;
    CoordinatorLayout coordinatorLayout;
    LinearLayout linearLayoutContainer;
    RelativeLayout relativeLayoutViewPager;
    CartListActivity cartListActivity;
    int countNumber;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    public static String mUserMail;
    String mUserPassword;
    public static String mUserPhone;
    public static String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences count = getSharedPreferences("my_count_preference",MODE_PRIVATE);
        countNumber = count.getInt("count_int",0);
        notificationCountCart = countNumber;
        NotificationCountSetClass.setNotifyCount(countNumber);
        rlConnectivity = findViewById(R.id.rlConnectivity);
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //frameLayout = (FrameLayout) findViewById(R.id.main_container_id);
        this.expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.navUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_main_address);
        this.appUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.app_user_name);
        tryAgain = (Button) findViewById(R.id.try_again_button);
        //new NetConnectivityTask().execute();
        getDataBase();



        if (!checkForInternet()) {
            tryInt = 1;
            rlConnectivity.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            //final boolean finalGetBoolean = getBoolean;
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkForInternet()) {
                        init();
                    }
                }
            });
        } else {
            init();
            /*rlConnectivity.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            prepareListData();
            mMenuAdapter = new com.allandroidprojects.sampleecomapp.miscellaneous.ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
            this.expandableList.setAdapter(this.mMenuAdapter);
            setupBottomNavigationView(this.bottomNavigationView);
            if (viewPager != null) {
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
            setUpClickGroupView(this.expandableList);
            setUpClickChildView(this.expandableList);
            this.sharedPref = getSharedPreferences("myLatLon", 0);
            checkLocationPermission();
            this.navUsername.setText(getSharedPreferences("myLatLon", 0).getString("yourAddress", ""));*/

        }
        getFireBaseToken();

    }

    private void getFireBaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(MainActivity.this.getClass().getName(), "getInstanceId failed", task.getException());
                            return;

                        }
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        String msg = getString(R.string.msg_token_fmt,token);
                        Log.d(this.getClass().getName(), msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseUser == null) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }
    }

    public boolean checkForInternet() {
        try {
            getBoolean = new NetConnectivityTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getBoolean;
    }

    public void init() {
        rlConnectivity.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        prepareListData();
        mMenuAdapter = new com.allandroidprojects.sampleecomapp.miscellaneous.ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        this.expandableList.setAdapter(this.mMenuAdapter);
        setupBottomNavigationView(this.bottomNavigationView);
        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        setUpClickGroupView(this.expandableList);
        setUpClickChildView(this.expandableList);
        this.sharedPref = getSharedPreferences("myLatLon", 0);
        String check = sharedPref.getString("lat", "");
        if (check.equalsIgnoreCase("")) {
            /*action_location = (Button) this.findViewById(R.id.action_location);
            action_location.performClick();*/
            //getLocationOfUser();
            //LocationManager loc = (LocationManager) getSystemService(LOCATION_SERVICE);
            //loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10,locationListener);
        }
        checkLocationPermission();
        this.navUsername.setText(getSharedPreferences("myLatLon", 0).getString("yourAddress", ""));
        /*if (mFirebaseUser!=null) {
            this.appUserName.setText(mUserName);
        }*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*public boolean isNetworkAvailableCheck() {
        if (!isNetworkAvailable()) {

        } else {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e("MainActivity", "Error checking internet connection", e);
            }
            Log.d("MainActivity", "No network available!");
        }
        return false;
    }*/
    private void getDataBase()  {
        if (mFirebaseUser==null || mFirebaseAuth==null ) return;
        stringHashMap = new HashMap<>();
        DocumentReference documentReference = db.collection("Users")
                .document(mFirebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document != null) {
                        if (document.exists()) {
                            stringHashMap = document.getData();
                            //mUserMail = stringHashMap.get()
                            mUserMail= Objects.requireNonNull(stringHashMap.get("Email")).toString();
                            mUserPhone=Objects.requireNonNull(stringHashMap.get("Phone")).toString();
                            mUserName=Objects.requireNonNull(stringHashMap.get("Name")).toString();
                            appUserName.setText(mUserName);
                            Log.d(this.getClass().getName(), "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d(this.getClass().getName(), "No such document");
                        }
                    }

                } else {
                    Log.d(this.getClass().getName(), "get failed with ", task.getException());
                }
            }
        });
    }

    private void prepareListData() {
        this.listDataHeader = new ArrayList();
        this.listDataChild = new HashMap();
        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Categories");
        item1.setIconImg(R.drawable.ic_apps_grey_24dp);
        this.listDataHeader.add(item1);
        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Shops");
        item2.setIconImg(R.drawable.ic_shopping_basket_black_24dp);
        this.listDataHeader.add(item2);
        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Personal");
        item3.setIconImg(R.drawable.user_p);
        this.listDataHeader.add(item3);
        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Open Map");
        item4.setIconImg(R.drawable.ic_map_black_24dp);
        this.listDataHeader.add(item4);
        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("Get Services");
        item5.setIconImg(R.drawable.ic_action_name_service);
        this.listDataHeader.add(item5);
        List<String> heading1 = new ArrayList();
        heading1.add("Offers");
        heading1.add("Electronics");
        heading1.add("Lifestyle");
        heading1.add("Home Appliances");
        heading1.add("Books & More");
        heading1.add("More");
        List<String> heading2 = new ArrayList();
        heading2.add("Open Location");
        heading2.add("Offers");
        heading2.add("Electronics");
        heading2.add("Lifestyle");
        heading2.add("Home Appliances");
        heading2.add("Books & More");
        heading2.add("More");
        List<String> heading4 = new ArrayList<>();
        heading4.add("My Cart");
        heading4.add("My Wishlist");
        heading4.add("Profile");
        heading4.add("My Order");
        heading4.add("Logout");
        this.listDataChild.put(this.listDataHeader.get(0), heading1);
        this.listDataChild.put(this.listDataHeader.get(1), heading2);
        this.listDataChild.put(this.listDataHeader.get(2), heading4);
    }

    private void setUpClickGroupView(ExpandableListView expandableList) {
        expandableList.setOnGroupClickListener(new SetUpGroupListView());
    }

    private void setUpClickChildView(ExpandableListView expandableList) {
        expandableList.setOnChildClickListener(new C03712());
    }

    private void setupNavigationSpinner(Spinner mSpinner) {
        mSpinner.setOnItemSelectedListener(new C03723());
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            new AlertDialog.Builder(this).setTitle((CharSequence) "Application wants to access your location").setMessage((CharSequence) "Grant Access ?").setPositiveButton((CharSequence) "Ok", new C03734()).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 99) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupBottomNavigationView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new C05525());
    }

    @Override
    protected void onResume() {
        super.onResume();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.this_is_container);
        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        invalidateOptionsMenu();
        //Toast.makeText(MainActivity.this,"on resume", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.this_is_container);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }*/
        if (linearLayout.getVisibility() == View.GONE && getSupportFragmentManager().getBackStackEntryCount()==1) {
            linearLayout.setVisibility(View.VISIBLE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            super.onBackPressed();



        } else if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        NotificationCountSetClass.setAddToCart(this, menu.findItem(R.id.action_cart), notificationCountCart);
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Context context = this;
        int id = item.getItemId();
        if (id == R.id.action_location) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new C03746();
            if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                return false;
            }
            if (locationManager != null) {
                locationManager.requestLocationUpdates("gps", 0, 0.0f, locationListener);
                mobileLocation = locationManager.getLastKnownLocation("gps");
                if (mobileLocation == null) {
                    locationManager.requestLocationUpdates("network", 0, 0.0f, locationListener);
                    mobileLocation = locationManager.getLastKnownLocation("network");
                    locationOnPause = locationListener;
                } if (mobileLocation == null) {
                    List<String> providers = locationManager.getProviders(true);
                    for (String provider:providers) {
                        Location l = locationManager.getLastKnownLocation(provider);
                        if (l == null) {
                            continue;
                        }
                        if (mobileLocation == null || l.getAccuracy()<mobileLocation.getAccuracy()) {
                            mobileLocation=l;
                        }
                    }
                }
                if (mobileLocation != null) {
                    lat = Double.toString(mobileLocation.getLatitude());
                    lon = Double.toString(mobileLocation.getLongitude());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lat", lat).apply();
                    editor.putString("lan", lon).apply();
                    GetAddress(lat, lon);
                } else {


                }
            }
            return true;
        } else if (id == R.id.action_cart) {
            startActivity(new Intent(context, CartListActivity.class));
            return true;
        } else {
            startActivity(new Intent(context, EmptyActivity.class));
            return super.onOptionsItemSelected(item);
        }
    }

    /*public void getLocationOfUser() {*/
        //LocationManager loc = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        /* Location locCurrent = loc.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locCurrent != null) {
            double latCurrent = locCurrent.getLatitude();
            double lonCurrent = locCurrent.getLongitude();
        }*/
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latCurrent = location.getLatitude();
                double lonCurrent = location.getLongitude();
                lat = Double.toString(latCurrent);
                lon = Double.toString(lonCurrent);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("lat", lat).apply();
                editor.putString("lan", lon).apply();
                GetAddress(lat, lon);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };



    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().popBackStack();

    }

    public void GetAddress(String lat, String lon) {
        String ret = "";
        String ret2;
        try {
            List<Address> addresses = new Geocoder(this, Locale.ENGLISH).getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            this.cityName = ((Address) addresses.get(0)).getAddressLine(0);
            this.sharedPref.edit().putString("yourAddress", this.cityName).apply();
            String stateName = ((Address) addresses.get(0)).getAddressLine(1);
            String countryName = ((Address) addresses.get(0)).getAddressLine(2);
            if (addresses != null) {
                Address returnedAddress = (Address) addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                    strReturnedAddress.append("\n");
                }

                ret2 = strReturnedAddress.toString();
            } else {
                ret2 = "No Address returned!";
            }
        } catch (IOException e) {
            e.printStackTrace();
            ret2 = "Can't get Address!";
        }
        this.navUsername.setText(getSharedPreferences("myLatLon", 0).getString("yourAddress", ""));
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        ImageListFragment fragment = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_1));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_2));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 3);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_3));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 4);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_4));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 5);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_5));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 6);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_6));
        viewPager.setAdapter(adapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item1) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_item2) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_item3) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_item4) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_item5) {
            viewPager.setCurrentItem(4);
        } else if (id == R.id.nav_item6) {
            viewPager.setCurrentItem(5);
        } else if (id == R.id.my_wishlist) {
            startActivity(new Intent(MainActivity.this, WishlistActivity.class));
        } else if (id == R.id.my_cart) {
            startActivity(new Intent(MainActivity.this, CartListActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, EmptyActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof EditFragment) {
            EditFragment editFragment = (EditFragment)fragment;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    public class NetConnectivityTask extends AsyncTask<Void, Void, Boolean> {
        boolean valueCheck;

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (isNetworkAvailable())  {
                try {
                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    valueCheck = urlc.getResponseCode() == 204 && urlc.getContentLength() == 0;
                    /*return (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0);*/
                } catch (IOException e) {
                    //Log.e(TAG, "Error checking internet connection", e);
                }
            } /*else {
                //Log.d(TAG, "No network available!");
                return false;*/
            return valueCheck;
            }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            boolean resultCheckPost = valueCheck;

        }
    }

    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$1 */
    class SetUpGroupListView implements ExpandableListView.OnGroupClickListener {
        SetUpGroupListView() {
        }

        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
            Object object = Long.valueOf(expandableListView.getExpandableListPosition(i));
            StringBuilder strUri = new StringBuilder();
            strUri.append("http://maps.google.com/maps?q=loc:");
            strUri.append(MainActivity.this.getSharedPreferences("myLatLon", 0).getString("lat", ""));
            strUri.append(",");
            strUri.append(MainActivity.this.getSharedPreferences("myLatLon", 0).getString("lan", ""));
            strUri.append(" (Your current location)");
            String id = object.toString();
            if (i == 3) {
                MainActivity.this.drawer.closeDrawers();

//                new Intent("android.intent.action.VIEW", Uri.parse(strUri.toString())).setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                MainActivity.this.startActivity(new Intent(MainActivity.this, GoogleMapActivity.class));
            }
            if (i==4) {
                MainActivity.this.drawer.closeDrawers();
                MainActivity.this.startActivity(new Intent(MainActivity.this, GridViewImageTextActivity.class));
            }
            return false;
        }
    }

    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$2 */
    class C03712 implements ExpandableListView.OnChildClickListener {
        C03712() {
        }

        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
            if (i == 0) {
                if (i1 == 0) {
                    //MainActivity.viewPager.setCurrentItem(0);
                    MainActivity.this.drawer.closeDrawers();
                    MainActivity.this.startActivity(new Intent(MainActivity.this, ImageSliderActivity.class));

                }
                if (i1 == 1) {
                    position_int.add(0);
                    // MainActivity.viewPager.setCurrentItem(1);
                    MainActivity.this.drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, GridView_categories.class);
                    intent.putExtra("jump_to", 0);
                    intent.putExtra(GridView_categories.whichActivityFrom, MainActivity.class.getSimpleName());
                    MainActivity.this.startActivity(intent);
                }
                if (i1 == 2) {
                    position_int.add(1);
                    //MainActivity.viewPager.setCurrentItem(2);
                    MainActivity.this.drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, GridView_categories.class);
                    intent.putExtra("jump_to", 1);
                    intent.putExtra(GridView_categories.whichActivityFrom, MainActivity.class.getSimpleName());
                    MainActivity.this.startActivity(intent);
                }
                if (i1 == 3) {
                    position_int.add(3);
                    //MainActivity.viewPager.setCurrentItem(3);
                    MainActivity.this.drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, GridView_categories.class);
                    intent.putExtra("jump_to", 3);
                    intent.putExtra(GridView_categories.whichActivityFrom, MainActivity.class.getSimpleName());
                    MainActivity.this.startActivity(intent);
                }
                if (i1 == 4) {
                    position_int.add(5);
                    //MainActivity.viewPager.setCurrentItem(4);
                    MainActivity.this.drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, GridView_categories.class);
                    intent.putExtra("jump_to", 5);
                    intent.putExtra(GridView_categories.whichActivityFrom, MainActivity.class.getSimpleName());
                    MainActivity.this.startActivity(intent);
                }
                if (i1 == 5) {
                    position_int.add(7);
                    //MainActivity.viewPager.setCurrentItem(5);
                    MainActivity.this.drawer.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, GridView_categories.class);
                    intent.putExtra("jump_to", 7);
                    intent.putExtra(GridView_categories.whichActivityFrom, MainActivity.class.getSimpleName());
                    MainActivity.this.startActivity(intent);
                }

            }
            if (i == 1) {
                if (i1 == 0) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, GoogleActivityShop.class));
                }
                if (i1 == 1) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, HereMapActivity.class));
                }
            }
            if (i == 2) {
                if (i1 == 0) {
                    startActivity(new Intent(MainActivity.this, CartListActivity.class));
                }
                if (i1 == 1) {
                    startActivity(new Intent(MainActivity.this,WishlistActivity.class));
                }
                if (i1 ==2) {
                    //startActivity(new Intent(MainActivity.this,));

                    //coordinatorLayout = (CoordinatorLayout)findViewById(R.id.id_main_coordinator);
                    //coordinatorLayout.setVisibility(View.GONE);
                    //frameLayout.setVisibility(View.GONE);
                    //linearLayoutContainer =(LinearLayout)findViewById(R.id.this_is_container);
                    //linearLayoutContainer.setVisibility(View.GONE);
                    //bottomNavigationView.setVisibility(View.GONE);
                    //relativeLayoutViewPager = (RelativeLayout)findViewById(R.id.content_main);
                    //relativeLayoutViewPager.removeAllViewsInLayout();
                    //coordinatorLayout.removeAllViewsInLayout();
                    //linearLayoutContainer.setVisibility(View.GONE);
                    //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    //bottomNavigationView.setVisibility(View.GONE);



                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .add(R.id.id_main_coordinator,new ProfileDetailFragment()).addToBackStack(null).commit();
                    drawer.closeDrawers();
                }
                if (i1 == 4) {
                   /* FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this,"User logged out!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SignInActivity.class));*/
                    MainActivity.this.drawer.closeDrawers();

                   AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                   b.setTitle("Logging Out");
                   b.setMessage("Are you sure?");
                   b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           FirebaseAuth.getInstance().signOut();
                           Toast.makeText(MainActivity.this,"User logged out!",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(MainActivity.this,SignInActivity.class));

                       }
                   });
                   b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();

                       }
                   });
                   b.show();




                }

            }

            return false;
        }
    }


    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$3 */
    class C03723 implements AdapterView.OnItemSelectedListener {
        C03723() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$4 */
    class C03734 implements DialogInterface.OnClickListener {
        C03734() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        }
    }

    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$6 */
    class C03746 implements LocationListener {
        C03746() {
        }

        public void onLocationChanged(Location location) {
            MainActivity.this.mobileLocation = location;
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }

    /* renamed from: com.enthuons.ecommerce.startup.MainActivity$5 */
    class C05525 implements BottomNavigationView.OnNavigationItemSelectedListener {
        C05525() {
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.nav_bottom_categories:
                    MainActivity.this.startActivity(new Intent(MainActivity.this, CategoriesActivity.class));
                    break;
                case R.id.nav_bottom_home:
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_bottom_offers:
                    MainActivity.this.startActivity(new Intent(MainActivity.this, ImageSliderActivity.class));
                    break;
                case R.id.action_location:
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
                    break;
                case R.id.action_lets_chat:
                    //MainActivity.this.startActivity(new Intent(MainActivity.this,ChatBotActivity.class));
                    MainActivity.this.startActivity(new Intent(MainActivity.this,MainActivityBotLIbre.class));
                default:
                    break;
            }
            return false;
        }
    }
}
