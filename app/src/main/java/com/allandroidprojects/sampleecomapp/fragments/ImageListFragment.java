/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.allandroidprojects.sampleecomapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.product.ItemDetailsActivity;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.allandroidprojects.sampleecomapp.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    public static final String STRING_TEXT_NAME = "ItemName";
    public static final String STRING_TEXT_DESCRIPTION = "ItemDescription";
    public static final String STRING_TEXT_PRICE = "ItemPrice";
    private static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
      /*  if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }*/
        String[] items=null;
        String[] itemNameOffer=null;
        String[] itemNameDsecription=null;
        String[] itemNamePrice=null;
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            items =ImageUrlUtils.getOffersUrls();
            itemNameOffer=ImageUrlUtils.getItemOfferName();
            itemNameDsecription = ImageUrlUtils.getOfferDescription();
            itemNamePrice=ImageUrlUtils.getOfferPrice();

        }else if (ImageListFragment.this.getArguments().getInt("type") == 2){
            items =ImageUrlUtils.getElectronicsUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 3){
            items =ImageUrlUtils.getLifeStyleUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 4){
            items =ImageUrlUtils.getHomeApplianceUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 5){
            items =ImageUrlUtils.getBooksUrls();
        }else {
            items = ImageUrlUtils.getImageUrls();
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, items,itemNameOffer,itemNameDsecription,itemNamePrice));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, items,itemNameOffer,itemNameDsecription,itemNamePrice));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private String[] mValue1;
        private String[] mValue2;
        private String[] mValue3;
        /*private ArrayList<String> mValue1;
        private ArrayList<String> mValue2;
        private ArrayList<String> mValue3;*/
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView value1;
            public final TextView value2;
            public final TextView value3;



            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                value1 = (TextView) view.findViewById(R.id.item_name);
                value2 = (TextView) view.findViewById(R.id.item_description);
                value3 = (TextView) view.findViewById(R.id.item_price);

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, String[] items, String[] item1,String[] item2,String[] item3) {
            mValues = items;
            mValue1 = item1;
            mValue2 = item2;
            mValue3 = item3;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(@NotNull final ViewHolder holder, final int position) {
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
            final Uri uri = Uri.parse(mValues[position]);
            holder.mImageView.setImageURI(uri);
            if (mValue1 != null) {
                final String nameProduct = mValue1[position];
                holder.value1.setText(nameProduct);
            }
            if (mValue2 != null) {
                final String nameDescription = mValue2[position];
                holder.value2.setText(nameDescription);
            }
            if (mValue3 != null) {
                final String namePrice = mValue3[position];
                holder.value3.setText("₹" + namePrice);
            }

            /*holder.value1.setText(mValue1[position]);
            holder.value2.setText(mValue2[position]);
            holder.value3.setText(mValue3[position]);*/
            /*if (holder.getAdapterPosition() == 0) {
                holder.value1.setText("Leather Shoes");
                holder.value2.setText("Men Brown shoe");
                holder.value3.setText("Rs. 1,999");
            }
            if (holder.getAdapterPosition() == 1) {
                holder.value1.setText("Men Belt");
                holder.value2.setText("Genuine leather");
                holder.value3.setText("Rs. 1,099");
            }
            if (holder.getAdapterPosition() ==2) {
                holder.value1.setText("Watch");
                holder.value2.setText("Men formal watch");
                holder.value3.setText("Rs. 5,999");

            }
            if (holder.getAdapterPosition() == 3) {
                holder.value1.setText("Jackets");
                holder.value2.setText("Black Leather jacket");
                holder.value3.setText("Rs. 4,999");
            }*/
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mValues[position]);
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    intent.putExtra(STRING_TEXT_NAME, mValue1[position]);
                    intent.putExtra(STRING_TEXT_DESCRIPTION,mValue2[position]);
                    intent.putExtra(STRING_TEXT_PRICE,mValue3[position]);
                    mActivity.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.addWishlistImageUri(mValues[position]);
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_red_24dp);
                    notifyDataSetChanged();
                    Toast.makeText(mActivity,"Item added to wishlist.",Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }
    }
}
