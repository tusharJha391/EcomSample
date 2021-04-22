package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import java.util.List;

public class MyAdapterCategories extends RecyclerView.Adapter<MyAdapterCategories.ViewHolder> {
    /* renamed from: c */
    private Context context;
    private List<SampleModelCategories> values;
    GridView_categories gridView_categories = new GridView_categories();

    public class MyChildViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        private MyChildViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        ImageView arrow_down;
        ImageView category_image;
        TextView category_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.category_image = (ImageView) itemView.findViewById(R.id.image_recycler);
            this.category_name = (TextView) itemView.findViewById(R.id.text_recycler);
            this.arrow_down = (ImageView) itemView.findViewById(R.id.icon_downArrow);
        }

        public void onClick(View view) {
            Context context = view.getContext();
            /*StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("position = ");
            stringBuilder.append(getLayoutPosition());
            Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_SHORT).show();*/
            if (getLayoutPosition() < values.size()) {
               // MyAdapterCategories.this.context.startActivity(new Intent(MyAdapterCategories.this.context, GridView_categories.class));
                Intent intent = new Intent(MyAdapterCategories.this.context, GridView_categories.class);
                intent.putExtra("name",getLayoutPosition());
                intent.putExtra(GridView_categories.whichActivityFrom,MyAdapterCategories.class.getSimpleName());
                MyAdapterCategories.this.context.startActivity(intent);
            }
        }
    }

    public MyAdapterCategories(List<SampleModelCategories> values, Context c) {
        this.values = values;
        this.context = c;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.cardview_categories, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SampleModelCategories s = (SampleModelCategories) this.values.get(i);
        viewHolder.category_image.setImageResource(s.getImageCategories());
        viewHolder.category_name.setText(s.getCategoriesName());
        viewHolder.arrow_down.setImageResource(s.getIconArrow());
    }

    public int getItemCount() {
        return this.values.size();
    }





}
