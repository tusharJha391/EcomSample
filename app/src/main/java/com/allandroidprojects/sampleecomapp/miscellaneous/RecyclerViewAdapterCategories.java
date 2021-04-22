package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.model.Categories;

import java.util.List;

public class RecyclerViewAdapterCategories extends  RecyclerView.Adapter<RecyclerViewAdapterCategories.ViewHolder> {
    private Context context;
    private List<Categories> categoriesList;


    public RecyclerViewAdapterCategories(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterCategories.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View gridView=LayoutInflater.from(context).inflate(R.layout.gridview_recycler, viewGroup, false);
        return new ViewHolder(gridView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterCategories.ViewHolder viewHolder, int i) {
        viewHolder.headerTitle.setText(categoriesList.get(i).getCategoryHeader());
        if (i==0) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(0).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(0).getCategItemImage()));
        } else if (i==1) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(1).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(1).getCategItemImage()));
        } else if (i==2) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(2).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(2).getCategItemImage()));
        } else if (i==3) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(3).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(3).getCategItemImage()));
        } else if (i==4) {

            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(4).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(4).getCategItemImage()));
        } else if (i==5) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(5).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(5).getCategItemImage()));

        } else if (i==6) {

            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(6).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(6).getCategItemImage()));
        } else  if (i==7) {
            viewHolder.gridView.setAdapter(new ItemAdapterGridview(context,
                    categoriesList.get(i).getCategoryData().get(7).getCategItemName(),
                    categoriesList.get(i).getCategoryData().get(7).getCategItemImage()));

        }
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView headerTitle;
        public GridView gridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.header_subcategories);
            gridView = (GridView) itemView.findViewById(R.id.gridview_android_example);

        }


    }
}
