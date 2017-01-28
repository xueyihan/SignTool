package com.example.signinner.module.signin;

import java.sql.Timestamp;
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

import com.example.signinner.entity.ActivityEntity;
import com.example.yswb.R;

public class CanyuAdapter extends BaseAdapter{

	private List<ActivityEntity> coll;
	private Context ctx;
	private LayoutInflater mInflater;
	
	public CanyuAdapter(Context context, List<ActivityEntity> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
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
		final ActivityEntity entity = coll.get(position);
		ViewHolder viewHolder = null;
		int restTime=0;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.signin_canyu_card,null);

			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_name);
			viewHolder.time = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_time);
			viewHolder.position = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_position);
			viewHolder.resttime = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_resttime);
			
			//获取当前时间
			Timestamp currentTime=new Timestamp(System.currentTimeMillis());
			Timestamp endTime=entity.getEndTime();
			Timestamp startTime=entity.getStartTime();
			long current,end;
			current=currentTime.getTime();
			end=endTime.getTime();
			if(currentTime.compareTo(endTime)<0&&currentTime.compareTo(startTime)>0){
				//签到时间中
				System.out.println("你好："+(end-current));
				restTime=(int)(end-current)/60000;
				
				if(entity.getQiandaoStatus()==2){
					//未签到，设置签到按钮
					viewHolder.resttime = (TextView) convertView
							.findViewById(com.example.yswb.R.id.canyu_qiandao_resttime);
					TextView t=(TextView) convertView
							.findViewById(com.example.yswb.R.id.canyu_qiandao_button);
					t.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String signId=entity.getSignId();
							Intent intent=new Intent();
							intent.setClass(ctx, QiandaoActivity.class);
							intent.putExtra("signId", signId);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							ctx.getApplicationContext().startActivity(intent);
						}
					});
				}
				if(entity.getQiandaoStatus()==1){
					//已签到
					LinearLayout l=(LinearLayout) convertView.findViewById(R.id.canyu_qiandao_status);
					l.removeAllViews();
					TextView t=new TextView(ctx);
					t.setText("已签到");
					t.setGravity(Gravity.CENTER);
					t.setHeight(140);
					t.setWidth(LayoutParams.WRAP_CONTENT);
					t.setTextSize(25);
					t.setTextColor(0xffcccccc);
					l.addView(t);
				}
			}
			if(currentTime.compareTo(endTime)>0||currentTime.compareTo(startTime)<0){
				//最近一次签到已结束，或签到未开始
				LinearLayout l=(LinearLayout) convertView.findViewById(R.id.canyu_qiandao_status);
				l.removeAllViews();
				TextView t=new TextView(ctx);
				t.setText("未开始");
				t.setGravity(Gravity.CENTER);
				t.setHeight(135);
				t.setWidth(LayoutParams.WRAP_CONTENT);
				t.setTextSize(25);
				t.setTextColor(0xffcccccc);
				l.addView(t);
			}
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.name.setText("名称："+entity.getName());
		viewHolder.position.setText("    地点："+entity.getPosition());
		viewHolder.time.setText("时间："+entity.getTime());
		viewHolder.resttime.setText("剩余"+restTime+"分钟");
		return convertView; 
	}
	
	static class ViewHolder {
		public TextView name;
		public TextView time;
		public TextView position;
		public TextView resttime;
	}
	
}