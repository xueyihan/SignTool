package com.example.signinner.module.signin;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.module.login.LuruActivity;
import com.example.yswb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FaqiAddActivity extends Activity {

	ImageView back;
	TextView contain_button;
	Activity a;
	EditText name,time,position;
	int school=1,major=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin_faqi_add);
		
		//获取UI
		name=(EditText) findViewById(R.id.activity_name);
		time=(EditText) findViewById(R.id.activity_time);
		position=(EditText) findViewById(R.id.activity_position);
		contain_button=(TextView) findViewById(R.id.contain_button);
		back=(ImageView) findViewById(R.id.back_button);
		a=this;
		
		//添加监听事件
		contain_button.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addFaqiActivity(name.getText().toString(),time.getText().toString(),position.getText().toString(),school,major);
			}
		});
		back.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				a.finish();
			}
		});
		
	}
	
	
	private void addFaqiActivity(final String name, final String time,
			final String position, final int school, final int major) {
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
					String userKey=UserInfo.getUserKey(a.getApplicationContext());
					if(userKey!=null){
						params.add(new BasicNameValuePair("userKey", userKey));
						params.add(new BasicNameValuePair("name", name));
						params.add(new BasicNameValuePair("time", time));
						params.add(new BasicNameValuePair("position", position));
						params.add(new BasicNameValuePair("school", String.valueOf(school)));
						params.add(new BasicNameValuePair("major", String.valueOf(major)));
						params.add(new BasicNameValuePair("publisherName", UserInfo.getName(a.getApplicationContext())));
						result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/activity/publish.action", params,a.getApplicationContext());
					}
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true, a);
		task.execute();
		
	}


	private void jsonResolveList(String result) {
		try {
			JSONObject j = new JSONObject(result);
			JSONObject jsonObject=j.getJSONObject("msg");
			String state=jsonObject.getString("state");
			if(state.equals("true")){
				//添加成功
				Toast.makeText(a.getApplicationContext(), "成功发布课程", Toast.LENGTH_LONG).show();
				a.finish();
				
			}else{
				//添加失败
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
