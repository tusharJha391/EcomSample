package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import java.util.List;

public class AdapterServiceMan extends RecyclerView.Adapter<AdapterServiceMan.MyViewHolder> {

    List<ModelServiceMan> modelServiceManList;
    Context context;

    public AdapterServiceMan(List<ModelServiceMan> modelServiceManList, Context context) {
        this.modelServiceManList = modelServiceManList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_layout_content,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ModelServiceMan serviceMan = modelServiceManList.get(i);
        myViewHolder.name.setText(serviceMan.getName());
        myViewHolder.address.setText(serviceMan.getAddress_text());
        myViewHolder.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Snackbar.make(view, "Call" + " " + serviceMan.getName() + " ?",
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction("Call", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                            }
                        }).show();*/
                Snackbar snackbar = Snackbar.make(view, "Call" + " " + serviceMan.getName() + " ?",
                        Snackbar.LENGTH_LONG)
                        .setAction("Call", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return modelServiceManList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView name,address;
       ImageView phoneNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_service);
            address = (TextView) itemView.findViewById(R.id.address_serviceman);
            phoneNumber = (ImageView) itemView.findViewById(R.id.phone_num);

        }
    }
}
