package com.example.signinner.module.signin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.example.signinner.entity.ActivitySearchEntity;
import com.example.signinner.module.login.LuruActivity;
import com.example.yswb.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FaqiDetailsActivity extends Activity {

	ImageView back;
	String userKey;
	Activity a;
	String name;
	String id;
	String position;
	String time;
	Intent data;
	EditText activity_name;
	EditText activity_time;
	EditText activity_position;
	RelativeLayout activity_group;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_faqi);
		
		//获取UI
		back=(ImageView) findViewById(R.id.back_button);
		activity_name=(EditText) findViewById(R.id.activity_name);
		activity_time=(EditText) findViewById(R.id.activity_time);
		activity_position=(EditText) findViewById(R.id.activity_position);
		activity_group=(RelativeLayout) findViewById(R.id.activity_group);
		a=this;
		
		//获取数据
		data =getIntent();
		id=data.getStringExtra("id");
		name=data.getStringExtra("name");
		position=data.getStringExtra("position");
		time=data.getStringExtra("time");
		activity_name.setText(name);
		activity_time.setText(time);
		activity_position.setText(position);
		//添加监听事件
		back.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				a.finish();
			}
		});
		activity_group.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(a.getApplicationContext(), ActivityMember.class);
				a.startActivity(intent);
			}
		});
		
	}
	
	
	
	private void getData(){
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
					System.out.println("网络未连接");
				}
				else{
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					userKey=UserInfo.getUserKey(a.getApplicationContext());
					if(userKey!=null){
						params.add(new BasicNameValuePair("userKey",userKey));
						result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/activity/getAll.action", params,a.getApplicationContext());
					}
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true, a);
		task.execute();
		
	}


	private void jsonResolveList(String result) {
		List<ActivitySearchEntity> coll=new ArrayList<ActivitySearchEntity>();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONObject msg=jsonObject.getJSONObject("msg");
			JSONArray array = msg.getJSONArray("data");
			for(int i=0; i<array.length(); i++){
				coll.add(new ActivitySearchEntity(array.optString(i),userKey));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
