package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;

public class GridViewImageTextActivity extends AppCompatActivity {
    GridView androidGridView;
    Dialog dialog;
    String[] gridViewString = {"Electrician","Plumber","Carpenter","Mechanics","Builder","Trainer"};
    int[] imageServiceMan = {R.drawable.electricity,R.drawable.plumbing,R.drawable.carpenter,R.drawable.car_repair,R.drawable.construction,R.drawable.fitness};
    int[] gridViewImageId = {R.drawable.ic_electrician,R.drawable.ic_plumber,R.drawable.ic_carpenter,R.drawable.ic_mechanic,R.drawable.ic_builder,R.drawable.ic_coach};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_image_text_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Choose Services");
        CoustomGridAdapter adapterViewAndroid = new CoustomGridAdapter(GridViewImageTextActivity.this, gridViewString, gridViewImageId);
        androidGridView = (GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //Toast.makeText(GridViewImageTextActivity.this,"GridView Item: " + gridViewString[+i],Toast.LENGTH_SHORT).show();
                dialog = new Dialog(GridViewImageTextActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.service_detail_box);
                Button textViewCancel = (Button) dialog.findViewById(R.id.cancel_button);
                Button textViewVisit = (Button) dialog.findViewById(R.id.visit_action);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.serviceman_image);
                TextView textViewService = (TextView) dialog.findViewById(R.id.serviceman_name);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setImageDrawable(getDrawable(gridViewImageId[i]));
                }

                textViewService.setText(gridViewString[i]);

                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                textViewVisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GridViewImageTextActivity.this,Collasping.class);
                        intent.putExtra("imageID",imageServiceMan[i]);
                        intent.putExtra("nameWho",gridViewString[i]);
                        //startActivity(new Intent(GridViewImageTextActivity.this,Collasping.class));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
