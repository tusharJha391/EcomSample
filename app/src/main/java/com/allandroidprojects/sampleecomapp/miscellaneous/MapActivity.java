package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.allandroidprojects.sampleecomapp.R;

public class MapActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        setContentView((int) R.layout.map_layout);
        super.onCreate(savedInstanceState, persistentState);
    }
}
