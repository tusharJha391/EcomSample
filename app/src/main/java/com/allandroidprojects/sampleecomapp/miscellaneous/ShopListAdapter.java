package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder> {

    private List<ShopData> shopDataList;
    ShopRecyclerView shopRecyclerView;
    Context mContext;
    public LocationDetailAdapterListener locationDetailAdapterListener;

    public ShopListAdapter(List<ShopData> shopDataList, LocationDetailAdapterListener locationDetailAdapterListener) {
        this.shopDataList = shopDataList;
        this.locationDetailAdapterListener = locationDetailAdapterListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_list_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        ShopData shopData = shopDataList.get(i);
        myViewHolder.shop_image.setImageResource(shopData.getShopImageId());
        myViewHolder.shop_name.setText(shopData.getShopName());
        myViewHolder.shop_rate.setRating(shopData.getRatingBar());
        myViewHolder.shop_address.setText(shopData.getShopAddress());
        myViewHolder.shop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDetailAdapterListener.onClickShopLocation(view,i);

            }
        });
        myViewHolder.shop_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDetailAdapterListener.onRowClick(view,i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView shop_image;
        TextView shop_name;
        TextView shop_location;
        RatingBar shop_rate;
        TextView shop_address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_image = (ImageView) itemView.findViewById(R.id.shop_image);
            shop_name = (TextView) itemView.findViewById(R.id.shop_name);
            shop_location = (TextView) itemView.findViewById(R.id.shop_map);
            shop_rate = (RatingBar) itemView.findViewById(R.id.ratingBar);
            shop_address = (TextView) itemView.findViewById(R.id.shop_address);
            shop_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    locationDetailAdapterListener.onClickShopLocation(view, getAdapterPosition());

                }


            });
            shop_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    locationDetailAdapterListener.onRowClick(view,getAdapterPosition());
                }
            });
        }

        /*@Override
        public void onClick(View view) {
            *//*shopRecyclerView = new ShopRecyclerView();
            //shopRecyclerView.getLocationFromAddress(view.getContext(), String.valueOf(shop_address.getText()));
            Intent intent = new Intent(mContext, ShopDetailScreen.class);
            intent.putExtra("lat",shopRecyclerView.getLocationFromAddress(view.getContext(),String.valueOf(shop_address.getText())).latitude);
            intent.putExtra("lon",shopRecyclerView.getLocationFromAddress(view.getContext(),String.valueOf(shop_address.getText())).longitude);
            mContext.startActivity(intent);*//*
            locationDetailAdapterListener.onRowClick(view,getAdapterPosition());
        }*/
    }
    public interface LocationDetailAdapterListener {
        void onClickShopLocation(View v, int position);
        void onRowClick(View v,int position);

    }

}

