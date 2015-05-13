package com.wanna.library;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	Context ctxt;
	LayoutInflater listViewInflater;
	ArrayList<String[]> listItemsList;
	
	public ListViewAdapter(ArrayList<String[]> listItemsList, Context ctxt){
		this.listItemsList = listItemsList;
		this.ctxt = ctxt;
		listViewInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItemsList.get(position)[0];
	}

	public Object getItemPrivacy(int position){
		return listItemsList.get(position)[2];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = listViewInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			TextView listItem = (TextView)convertView.findViewById(android.R.id.text1);
			listItem.setText(listItemsList.get(position)[1]);
		}
		return convertView;
	}

}
