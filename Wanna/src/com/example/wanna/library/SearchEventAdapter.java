package com.example.wanna.library;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchEventAdapter extends BaseAdapter {

//	String[] searchEventItems;
	Context ctxt;
	LayoutInflater searchEventInflater;
	ArrayList<String[]> eventItemsList;
	
	public SearchEventAdapter(ArrayList<String[]> eventItemsList, Context ctxt){
		this.eventItemsList = eventItemsList;
//		searchEventItems = eventItemsList.
		this.ctxt = ctxt;
		searchEventInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eventItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return eventItemsList.get(position)[0];
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
			convertView = searchEventInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			TextView eventItem = (TextView)convertView.findViewById(android.R.id.text1);
			eventItem.setText(eventItemsList.get(position)[1]);
		}
		return convertView;
	}

}
