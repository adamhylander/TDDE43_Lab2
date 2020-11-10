package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> gradients;
    Map<String, List<String>> colors;
    private Adapter listAdapter;
    int expandedParent = -1;
    int checkedItem = -1;
    boolean pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText searchBar = findViewById(R.id.search_bar);
        searchBar.setText("/");

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        dataPump();

        listAdapter = new Adapter(this, gradients, colors);
        expandableListView.setAdapter(listAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) {
                    searchBar.setText("/");
                    searchBar.setBackgroundColor(Color.TRANSPARENT);
                }

                checkTree(searchBar);

            }
        });

        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(expandedParent != -1) expandableListView.collapseGroup(expandedParent);
                String[] arrOfStr = searchBar.getText().toString().split("/");
                if(searchBar.getText().toString().equals("/") || !(((String) listAdapter.getGroup(groupPosition)).equals(arrOfStr[1])))
                    searchBar.setText("/" + (String) listAdapter.getGroup(groupPosition));

                else
                    searchBar.setText("/");

                return true;
            }
        });

        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(checkedItem != childPosition) {
                    System.out.println("FÖRST");
                    searchBar.setText("/" + (String) listAdapter.getGroup(groupPosition) +
                            "/" + (String) listAdapter.getChild(groupPosition, childPosition));
                }
                return true;
            }
        });

    }

    public void checkTree(EditText searchBar) {
        String[] arrOfStr = searchBar.getText().toString().split("/");

        if(arrOfStr.length <= 1) {
            return;
        }

        if(checkedItem != -1) {
            listAdapter.checkItem(expandedParent, checkedItem);
        }

        if(arrOfStr.length > 1) {
            if (colors.keySet().contains(arrOfStr[1])) {
                searchBar.setBackgroundColor(Color.TRANSPARENT);
                expandedParent = gradients.indexOf(arrOfStr[1]);
                expandableListView.expandGroup(expandedParent);
                if (arrOfStr.length > 2) {
                    List<String> a = colors.get(arrOfStr[1]);
                    for(String s : a) {
                        if(s.equals(arrOfStr[2])) {
                            checkedItem = a.indexOf(s);
                            listAdapter.checkItem(expandedParent, checkedItem);
                            pressed = true;
                            searchBar.setBackgroundColor(Color.TRANSPARENT);
                            return;
                        }
                    }
                    searchBar.setBackgroundColor(Color.RED);
                    checkedItem = -1;
                }
            } else {
                if(expandedParent != -1) expandableListView.collapseGroup(expandedParent);
                searchBar.setBackgroundColor(Color.RED);
                expandedParent = -1;
            }
        }
    }

    public void dataPump() {
        gradients = new ArrayList<>();
        colors = new HashMap<>();

        gradients.add("light");
        gradients.add("medium");
        gradients.add("dark");

        List<String> light = new ArrayList<>();
        List<String> medium = new ArrayList<>();
        List<String> dark = new ArrayList<>();

        light.add("white");
        light.add("white yo");

        medium.add("green");
        medium.add("yellow");
        medium.add("red");
        medium.add("blue");

        dark.add("black");
        dark.add("blackity black");

        colors.put(gradients.get(0), light);
        colors.put(gradients.get(1), medium);
        colors.put(gradients.get(2), dark);

    }

}