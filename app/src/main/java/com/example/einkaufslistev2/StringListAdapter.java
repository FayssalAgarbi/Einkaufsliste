package com.example.einkaufslistev2;


import android.content.Context;

import android.database.Cursor;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class StringListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "StringListAdapter";
    private Context mContext;
    DatabaseHelper mDatabaseHelper;


    public StringListAdapter( Context context, int resource,  ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(mContext);
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if(convertView==null){

            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        /** getting the name of the view in question */
        Cursor data = mDatabaseHelper.getItemID(getItem(position).toString());

        //getting the id of the view
        int itemID = -1;
        while(data.moveToNext()){
            itemID=data.getInt(0);
        }
        /** getting the color of the view */
        Cursor color = mDatabaseHelper.getColorState(itemID);
        int colorID = -1;
        while(color.moveToNext()){
            colorID=color.getInt(0);
        }
        /** assigning the Colors to the Views
         */
        switch (colorID){
            case 0:
                convertView.setBackgroundColor(Color.parseColor("#3195F9"));
                break;
            case 1:
                convertView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                convertView.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                convertView.setBackgroundColor(Color.RED);
                break;
        }
        return super.getView(position, convertView, parent);
    }
}




















