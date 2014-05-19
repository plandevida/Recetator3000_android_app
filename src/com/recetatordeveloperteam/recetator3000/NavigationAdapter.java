package com.recetatordeveloperteam.recetator3000;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class NavigationAdapter extends BaseAdapter {
	
	Activity activity;
	ArrayList<Pair<String, Integer>> arrayitems;

	@SuppressLint("Recycle")
	public NavigationAdapter(Activity a) {
		this.activity = a;
		TypedArray icons = a.getResources().obtainTypedArray(R.array.panel_icons);
		String[] titulos = a.getResources().getStringArray(R.array.panel_array);
		
		ArrayList<Pair<String, Integer>> navItems = new ArrayList<Pair<String, Integer>>();
		for (int i=0; i<titulos.length; i++) {
			navItems.add(new Pair<String, Integer>(titulos[i], icons.getResourceId(i, -1)));
		}
		this.arrayitems = navItems;
	}

	@Override
	public int getCount() {
		return arrayitems.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayitems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vista;
		if (convertView==null) {
			Pair<String, Integer> item = arrayitems.get(position);
			vista = activity.getLayoutInflater().inflate(R.layout.drawer_list_item, null);
			TextView tituloItem = (TextView) vista.findViewById(R.id.title_item);
			tituloItem.setText(item.first);
			ImageView iconoItem = (ImageView) vista.findViewById(R.id.icon_item);
			iconoItem.setImageResource(item.second);
		} else {
			vista = convertView;
		}
		return vista;
	}

}
