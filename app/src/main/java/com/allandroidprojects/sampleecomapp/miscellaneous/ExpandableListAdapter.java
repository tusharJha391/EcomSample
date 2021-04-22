package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    ExpandableListView expandList;
    private Context mContext;
    private HashMap<ExpandedMenuModel, List<String>> mListDataChild;
    private List<ExpandedMenuModel> mListDataHeader;

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<String>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }

    public int getGroupCount() {
        Log.d("GROUPCOUNT", String.valueOf(this.mListDataHeader.size()));
        return this.mListDataHeader.size();
    }

    public int getChildrenCount(int i) {
        if (i == 3 || i == 4) {
            return 0;
        } else {
            return ((List) Objects.requireNonNull(this.mListDataChild.get(this.mListDataHeader.get(i)))).size();
        }
    }

    public Object getGroup(int i) {
        return this.mListDataHeader.get(i);
    }

    public Object getChild(int i, int i1) {
        return ((List) this.mListDataChild.get(this.mListDataHeader.get(i))).get(i1);
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public long getChildId(int i, int i1) {
        return (long) i1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(i);
        if (view == null) {
            view = (ViewGroup) ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listheader, null);
        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) view.findViewById(R.id.iconimage);
        lblListHeader.setTypeface(null, 0);
        lblListHeader.setText(headerTitle.getIconName());
        headerIcon.setImageResource(headerTitle.getIconImg());
        return view;
    }

    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String childText = (String) getChild(i, i1);
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.list_submenu, null);
        }
        ((TextView) view.findViewById(R.id.submenu)).setText(childText);
        return view;
    }

    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
