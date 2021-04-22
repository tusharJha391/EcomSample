package com.allandroidprojects.sampleecomapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.allandroidprojects.sampleecomapp.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class ShoppingCartFragment extends Fragment {
    public static Context mContext;
    LinearLayout layoutCartItems;
    LinearLayout layoutCartPayments;
    LinearLayout layoutCartNoItems;
    Button bStartShopping;


    public ShoppingCartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart_list,container,false);
        if (container != null) {
            mContext=container.getContext();
        }
        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        ArrayList<String> cartlistImageUri =imageUrlUtils.getCartListImageUri();
        layoutCartItems = (LinearLayout) view.findViewById(R.id.layout_items);
        layoutCartPayments = (LinearLayout) view.findViewById(R.id.layout_payment);
        layoutCartNoItems = (LinearLayout) view.findViewById(R.id.layout_cart_empty);
        Button bStartShopping = (Button) view.findViewById(R.id.bAddNew);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri));



        return view;
    }

    public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartFragment.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<String> mCartlistImageUri;
        private RecyclerView mRecyclerView;

        public SimpleStringRecyclerViewAdapter(ArrayList<String> mCartlistImageUri, RecyclerView mRecyclerView) {
            this.mCartlistImageUri = mCartlistImageUri;
            this.mRecyclerView = mRecyclerView;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_cartlist_item, viewGroup, false);
            return new ShoppingCartFragment.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mCartlistImageUri.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
            }
        }
    }

    protected void setCartLayout() {


        if(MainActivity.notificationCountCart >0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //startActivity(new Intent(mContext,MainActivity.class));
                    clearAllFragments();
                }
            });
        }
    }
    public void clearAllFragments() {

        try {
            FragmentManager aFragmentManager = getActivity().getSupportFragmentManager();

            aFragmentManager.popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
