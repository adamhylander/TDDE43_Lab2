package com.example.lab2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Checkable;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class Adapter extends BaseExpandableListAdapter {

    Context context;
    List<String> gradients;
    Map<String, List<String>> colors;
    int parentIndex = -1;
    int childIndex = - 1;
    boolean isChecked = false;

    public Adapter(Context context, List<String> gradients, Map<String, List<String>> colors) {
        this.context = context;
        this.gradients = gradients;
        this.colors = colors;
    }

    @Override
    public int getGroupCount() {
        return gradients.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return colors.get(gradients.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gradients.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return colors.get(gradients.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String grads = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent,null);

        }
        TextView parentText = (TextView) convertView.findViewById(R.id.parentText);
        parentText.setText(grads);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String cols = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child,null);

        }
        TextView childText = (TextView) convertView.findViewById(R.id.childText);
        childText.setText(cols);

        if(childPosition == this.childIndex && groupPosition == this.parentIndex) {
            if(isChecked)
                childText.setBackgroundColor(Color.LTGRAY);
            else
                childText.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    public void checkItem(int groupPosition, int childPosition) {
        parentIndex = groupPosition;
        childIndex = childPosition;
        toggleIsChecked();
        notifyDataSetChanged();
    }

    public void toggleIsChecked() {
        isChecked = !isChecked;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
