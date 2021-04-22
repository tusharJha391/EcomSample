package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allandroidprojects.sampleecomapp.R;

public class CategoriesActivity extends AppCompatActivity {
    Context context = this;
    private LinearLayoutManager layoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.categories_fragment);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_categories);
        this.layoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.layoutManager);
        this.mRecyclerView.setAdapter(new MyAdapterCategories(CategoriesItem.getCategories(), this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
