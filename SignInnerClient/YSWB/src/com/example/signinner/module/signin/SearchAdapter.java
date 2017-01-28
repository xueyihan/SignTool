package com.example.signinner.module.signin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signinner.entity.ActivitySearchEntity;
import com.example.signinner.entity.ActivitySearchEntity;
import com.example.yswb.R;

public class SearchAdapter extends BaseAdapter{

	private List<ActivitySearchEntity> coll;
	private Context ctx;
	private LayoutInflater mInflater;
	public HashMap<String, Boolean> selected;
	public SearchAdapter(Context context, List<ActivitySearchEntity> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		selected=new HashMap<String, Boolean>();
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return coll.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		final ActivitySearchEntity entity = coll.get(position);
		ViewHolder viewHolder = null;
		int restTime=0;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.search_activity_card,null);

			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(com.example.yswb.R.id.search_activity_name);
			viewHolder.time = (TextView) convertView
					.findViewById(com.example.yswb.R.id.search_activity_time);
			viewHolder.position = (TextView) convertView
					.findViewById(com.example.yswb.R.id.search_activity_position);
			viewHolder.search_activity_choose_button= (TextView) convertView
					.findViewById(com.example.yswb.R.id.search_activity_choose_button);
			
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.search_activity_choose_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.isSelected()==true){
					v.setSelected(false);
					selected.put(entity.getId(), false);
					System.out.println("这是1");
				}else{
					v.setSelected(true);
					selected.put(entity.getId(), true);
					System.out.println("这是2");
				}
			}
		});
		
		viewHolder.name.setText("名称："+entity.getName());
		viewHolder.position.setText("    地点："+entity.getPosition());
		viewHolder.time.setText("时间："+entity.getTime());
		return convertView;
	}
	
	static class ViewHolder {
		public TextView name;
		public TextView time;
		public TextView search_activity_choose_button;
		public TextView position;
	}
	
}