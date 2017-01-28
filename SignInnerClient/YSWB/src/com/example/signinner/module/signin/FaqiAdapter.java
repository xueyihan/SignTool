package com.example.signinner.module.signin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.entity.ActivityEntity;
import com.example.yswb.R;

public class FaqiAdapter extends BaseAdapter{

	private List<ActivityEntity> coll;
	private Context ctx;
	private LayoutInflater mInflater;
	
	public FaqiAdapter(Context context, List<ActivityEntity> coll) {
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
			convertView = mInflater.inflate(R.layout.signin_faqi_card,null);
			
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(com.example.yswb.R.id.faqi_qiandao_name);
			viewHolder.time = (TextView) convertView
					.findViewById(com.example.yswb.R.id.faqi_qiandao_time);
			viewHolder.position = (TextView) convertView
					.findViewById(com.example.yswb.R.id.faqi_qiandao_position);
			viewHolder.resttime = (TextView) convertView
					.findViewById(com.example.yswb.R.id.faqi_qiandao_resttime);
			
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
				//换算成分钟
				restTime=(int)(end-current)/60000;
			}
			if(currentTime.compareTo(endTime)>0||currentTime.compareTo(startTime)<0){
				//最近一次签到已结束，或签到未开始
				final LinearLayout l=(LinearLayout) convertView.findViewById(R.id.faqi_qiandao_status);
				final Button button=(Button) convertView.findViewById(R.id.faqi_qiandao_button);
				button.setVisibility(View.VISIBLE);
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Timestamp startTime=new Timestamp(System.currentTimeMillis());
						Timestamp endTime=new Timestamp(System.currentTimeMillis()+600000);
						faqiqiandao(UserInfo.getUserKey(ctx.getApplicationContext()), entity.getId(), startTime, endTime);
						button.setVisibility(View.GONE);
						l.findViewById(R.id.faqi_card_zhengzaiqiandao).setVisibility(View.VISIBLE);
					}
				});
				l.findViewById(R.id.faqi_card_zhengzaiqiandao).setVisibility(View.GONE);
				l.removeViewAt(2);
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
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ctx.getApplicationContext(), FaqiDetailsActivity.class);
				intent.putExtra("id", entity.getId());
				intent.putExtra("name", entity.getName());
				intent.putExtra("position", entity.getPosition());
				intent.putExtra("time", entity.getTime());
				ctx.startActivity(intent);
			}
		});
		return convertView; 
	}
	
	static class ViewHolder {
		public TextView name;
		public TextView time;
		public TextView position;
		public TextView resttime;
	}
	
	
	
	private void faqiqiandao(final String userKey,final String activityId,final Timestamp startTime,final Timestamp endTime){
		AsyncInter inter=new AsyncInter() {
			String result="";
			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPostExecute() {
				// TODO Auto-generated method stub
				jsonResolveList(result);
			}
			
			@Override
			public void interruptTask() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void doInBackground() {
				// TODO Auto-generated method stub
				if (NetHandler.netJudge(ctx.getApplicationContext())==false){
					System.out.println("你好网络未连接");
				}
				else{
					//获取wifi环境
					String wserviceName = Context.WIFI_SERVICE;
					WifiManager wm = (WifiManager) ctx.getSystemService(wserviceName);
					List<ScanResult> wifiList = wm.getScanResults();
					JSONArray json=new JSONArray();
					for (int i = 0; i < wifiList.size(); i++) {
						try {
							json.put(i, wifiList.get(i).BSSID);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userKey",userKey));
					params.add(new BasicNameValuePair("activityId",activityId));
					params.add(new BasicNameValuePair("startTime",startTime.toString()));
					params.add(new BasicNameValuePair("endTime",endTime.toString()));
					params.add(new BasicNameValuePair("wifiJson",json.toString()));
					System.out.println("连接");
					result=NetHandler.netPost(ctx.getResources().getString(R.string.server_url)+"/sign/faqi.action", params,ctx.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true, ctx);
		task.execute();
	}

	private void jsonResolveList(String result) {
		try {
			JSONObject j = new JSONObject(result);
			JSONObject jsonObject=j.getJSONObject("msg");
			String state=jsonObject.getString("state");
			if(state.equals("true")){
				//发起签到成功
				Toast.makeText(ctx.getApplicationContext(), "发起签到成功", Toast.LENGTH_LONG).show();
			}else{
				//发起签到失败
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}