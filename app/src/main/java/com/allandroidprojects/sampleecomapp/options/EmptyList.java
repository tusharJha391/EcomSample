package com.allandroidprojects.sampleecomapp.options;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allandroidprojects.sampleecomapp.R;

public class EmptyList extends Fragment {

    public EmptyList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_message_layout_action,container,false);
        Button button = (Button) view.findViewById(R.id.bAddNew);

        return view;
    }

    public static EmptyList newInstance() {

        Bundle args = new Bundle();

        EmptyList fragment = new EmptyList();
        fragment.setArguments(args);
        return fragment;
    }
}
