package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;

public class ItemAdapterGridview extends BaseAdapter {
    private Context mContext;
    private final String[] option_name;
    private final Integer[] womenFashion;

    public ItemAdapterGridview(Context c, String[] option_name, Integer[] womenFashion) {
        this.mContext = c;
        this.womenFashion = womenFashion;
        this.option_name = option_name;
    }
    @Override
    public int getCount() {
        return this.womenFashion.length;
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
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view != null) {
            return view;
        }
        View gridView = new View(this.mContext);
        gridView = inflater.inflate(R.layout.grid_layout, null);
        ((TextView) gridView.findViewById(R.id.grid_item_label)).setText(this.option_name[i]);
        ((ImageView) gridView.findViewById(R.id.grid_item_image)).setImageResource(this.womenFashion[i].intValue());
        ((ImageView) gridView.findViewById(R.id.grid_item_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"this click",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ShopRecyclerView.class);
                mContext.startActivity(intent);

            }
        });
        return gridView;
    }


}
