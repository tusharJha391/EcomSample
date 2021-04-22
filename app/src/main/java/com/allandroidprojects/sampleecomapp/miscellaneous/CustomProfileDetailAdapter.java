package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.model.ProfileUserModel;

import java.util.ArrayList;

public class CustomProfileDetailAdapter extends RecyclerView.Adapter<CustomProfileDetailAdapter.MyViewAdapter> {
    private ArrayList<ProfileUserModel> profileUserModelArrayList;
    private static ClickListener clickListener;

    CustomProfileDetailAdapter(ArrayList<ProfileUserModel> profileUserModelArrayList) {
        this.profileUserModelArrayList = profileUserModelArrayList;

    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_detail_list,viewGroup,false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter myViewAdapter, int i) {





        ProfileUserModel profileUserModel = profileUserModelArrayList.get(i);
        myViewAdapter.mImageDetailRecycler.setImageResource(profileUserModel.getImageProfileDetailId());
        myViewAdapter.mProfileDetailName.setText(profileUserModel.getProfileDetailCategories());



    }

    @Override
    public int getItemCount() {
        return profileUserModelArrayList.size();
    }

    public void setClickListener(ClickListener clickListener) {
        CustomProfileDetailAdapter.clickListener = clickListener;
    }

    public static class MyViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageDetailRecycler;
        TextView mProfileDetailName;
        MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mImageDetailRecycler = itemView.findViewById(R.id.item_profile_image);
            mProfileDetailName = itemView.findViewById(R.id.profile_list);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        //void onItemLongClick(int position, View v);
    }
}
