package com.example.signinner.module.login;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.yswb.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InforActivity extends Activity {
	
	
	TextView cancel;
	TextView next;
	Activity a;
	EditText info_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infor);
		
		//获取UI
		next=(TextView) findViewById(R.id.next_button);
		cancel=(TextView) findViewById(R.id.infor_cancel_button);
		info_name=(EditText) findViewById(R.id.info_name);
		a=this;
		
		//添加监听事件
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=info_name.getText().toString();
				if(name!=""){
					String userKey=UserInfo.getUserKey(a.getApplicationContext());
					if(userKey==null){
						Toast.makeText(a.getApplicationContext(), userKey, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(a.getApplicationContext(), userKey, Toast.LENGTH_LONG).show();
						info(userKey, name);
					}
				
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(a.getApplicationContext(), MainActivity.class);
				a.startActivity(intent);
			}
		});
	}
	
	
	private void info(final String userKey,final String name){
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
					params.add(new BasicNameValuePair("name",name));
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/user/info.action", params,a.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true, a);
		task.execute();
	}
	
	
	
	
	public void jsonResolveList(String result){
		try {
			JSONObject j = new JSONObject(result);
			JSONObject jsonObject=j.getJSONObject("msg");
			String stateString = jsonObject.getString("state");
			if(stateString.equals("true")){
				//注册成功 进入个人信息界面
				//String userId=jsonObject.getString("userId");
				System.out.println("个人信息成功");
				//保存信息到本地
				UserInfo.setInfo(null, null, null, null, null, info_name.getText().toString(), a.getApplicationContext());
				Intent intentLuru=new Intent();
				intentLuru.setClass(a.getApplicationContext(), LuruActivity.class);
				a.startActivity(intentLuru);
				
				
			}else{
				Toast.makeText(a.getApplicationContext(), "失败", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
