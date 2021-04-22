package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Collasping extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterServiceMan adapterServiceMan;
    List<ModelServiceMan> serviceMEN;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_collasping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_serviceman);
        serviceMEN = new ArrayList<>();
        intent = getIntent();
        ImageView image_collapsing = (ImageView) findViewById(R.id.img_collapse);
        TextView name_who = (TextView) findViewById(R.id.collaspe_screen_text);
        image_collapsing.setImageResource(intent.getIntExtra("imageID",R.drawable.carpenter));
        name_who.setText(intent.getStringExtra("nameWho"));
        adapterServiceMan = new AdapterServiceMan(serviceMEN,this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareList();
        recyclerView.setAdapter(adapterServiceMan);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Services");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void prepareList() {
        ModelServiceMan man = new ModelServiceMan("Jit Singh","A359/5, Maidan Garhi Rd, Phase 2, Chattarpur Enclave, Chhattarpur, New Delhi, Delhi 110074");
        serviceMEN.add(man);
        man = new ModelServiceMan("Ravi Maurya","Hansraj Gupta Marg, Hemkunt Colony, Greater Kailash, New Delhi, Delhi 110048, India");
        serviceMEN.add(man);
        man = new ModelServiceMan("Rahul Kumar","Dwarka Kalibari Marg, Sector 12 Dwarka, Dwarka, Delhi, 110078, India");
        serviceMEN.add(man);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collasping_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
