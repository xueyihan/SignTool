package com.example.signinner.module.signin;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.entity.ActivityEntity;
import com.example.yswb.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.fragment.LazyFragment;

public class FaqiFragment extends LazyFragment {
	
	ImageView plus;
	LazyFragment a;
	PullToRefreshListView listview;
	ListView thislist;
	String userKey;
	List<ActivityEntity> coll;
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_signin_faqi);
		//获取UI
		listview=(PullToRefreshListView)findViewById(R.id.faqi_qiandao_listview);
		thislist=listview.getRefreshableView();
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
			   		intent.setClass(a.getApplicationContext(), FaqiAddActivity.class);
			   		a.startActivity(intent);
				}
		});
		thislist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("fwafawwfaw");
				Intent intent=new Intent();
				intent.setClass(a.getApplicationContext(), FaqiDetailsActivity.class);
				intent.putExtra("id", coll.get(position).getId());
				intent.putExtra("name", coll.get(position).getName());
				intent.putExtra("position", coll.get(position).getPosition());
				intent.putExtra("time", coll.get(position).getTime());
				a.startActivity(intent);
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
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/activity/getFaqiActivity.action", params,a.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true,getActivity());
		task.execute();
	}
	
	private void jsonResolveList(String result) {
		coll=new ArrayList<ActivityEntity>();
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
		listview.setAdapter(new FaqiAdapter(a.getActivity(), coll));
		
		
		
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
