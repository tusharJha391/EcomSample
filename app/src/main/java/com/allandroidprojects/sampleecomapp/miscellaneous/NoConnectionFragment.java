package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;

public class NoConnectionFragment extends Fragment {
    Button connectionCheck;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(R.layout.connection_check, container, savedInstanceState);
        return inflater.inflate(R.layout.connection_check,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectionCheck = (Button) view.findViewById(R.id.try_again_button);
        connectionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
