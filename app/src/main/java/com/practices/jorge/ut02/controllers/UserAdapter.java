package com.practices.jorge.ut02.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.practices.jorge.ut02.R;
import com.practices.jorge.ut02.models.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<User> items;

    public UserAdapter(Activity activity, ArrayList<User> items ) {//< Una lista de ususarios
        this.activity = activity;
        this.items = items;
    }

    public UserAdapter(Activity activity, User item ) { //<-- Un salo item de usuario
        this.activity = activity;
        this.items.add( item );
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = contentView;

        if( contentView == null ) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate(R.layout.content_main, null);
        }

        User item = items.get(position);

        TextView txVwName = (TextView) view.findViewById( R.id.txVwName );//<-- Nombre usuario
        txVwName.setText( item.getName() );

        return view;
    }
}
