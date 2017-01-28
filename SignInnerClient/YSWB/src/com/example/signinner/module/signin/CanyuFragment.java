package com.example.signinner.module.signin;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;







import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.entity.ActivityEntity;
import com.example.yswb.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.fragment.LazyFragment;

public class CanyuFragment extends LazyFragment {  
    
   PullToRefreshListView listview;
   ImageView plus;
   LazyFragment a;
   String userKey;
   @Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
	   super.onCreateViewLazy(savedInstanceState);
	   setContentView(R.layout.fragment_signin_canyu);
	   //获取UI
	   listview=(PullToRefreshListView)findViewById(R.id.canyu_qiandao_listview);
	   plus=(ImageView) this.getActivity().findViewById(R.id.signin_plus_button);
	   a=this;
	   
	   //获取用户登录信息
	   userKey=UserInfo.getUserKey(a.getApplicationContext());
	   
	   //添加课程事件
	   plus.setOnClickListener(new OnClickListener() {
		   	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		   		Intent intent=new Intent();
		   		intent.setClass(getActivity(), CanyuAddActivity.class);
		   		startActivity(intent);
			}
	   });
   }
   
   private void getData(final String userKey){
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
				if (NetHandler.netJudge(a.getApplicationContext())==false){
					System.out.println("你好网络未连接");
				}
				else{
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userKey",userKey));
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/activity/getCanyuActivity.action", params,a.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true,getActivity());
		task.execute();
	}
	
	private void jsonResolveList(String result) {
		List<ActivityEntity> coll=new ArrayList<ActivityEntity>();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONObject msg=jsonObject.getJSONObject("msg");
				JSONArray array = msg.getJSONArray("data");
				for(int i=0; i<array.length(); i++){
					coll.add(new ActivityEntity(array.optString(i),userKey));
				}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listview.setAdapter(new CanyuAdapter(a.getActivity(), coll));
		
		
		
	}
	
	@Override
		protected void onResumeLazy() {
			// TODO Auto-generated method stub
			super.onResumeLazy();
			if(userKey!="0")
				   //用户已登录 获取数据
				   getData(userKey);
		}
	
}
