package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

public class CoustomGridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;

    public CoustomGridAdapter(Context mContext, String[] gridViewString, int[] gridViewImageId) {
        this.mContext = mContext;
        this.gridViewString = gridViewString;
        this.gridViewImageId = gridViewImageId;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridViewAndroid;
        String name;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            gridViewAndroid = new View(mContext);
            gridViewAndroid = layoutInflater.inflate(R.layout.gridview_layout, null);
            TextView textView = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageView = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textView.setText(gridViewString[i]);
            imageView.setImageResource(gridViewImageId[i]);

        } else {
            gridViewAndroid = (View) view;
        }
        return gridViewAndroid;

    }

}




