package com.example.signinner.module.login;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.yswb.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	RadioButton student;
	RadioButton teacher;
	EditText username;
	EditText password1;
	EditText password2;
	ImageView back;
	TextView next;
	Activity a;
	int identity;//1学生 2老师
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//获取UI
		username=(EditText) findViewById(R.id.register_username);
		password1=(EditText) findViewById(R.id.register_password1);
		password2=(EditText) findViewById(R.id.register_password2);
		next=(TextView) findViewById(R.id.next_button);
		back=(ImageView) findViewById(R.id.back_button);
		student=(RadioButton) findViewById(R.id.register_student);
		teacher=(RadioButton) findViewById(R.id.register_teacher);
		a=this;
		
		student.setChecked(true);
		//添加监听事件
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				identity=1;
				if(student.isChecked()) identity=1;
				if(teacher.isChecked()) identity=2;
				register(username.getText().toString(),password1.getText().toString(),identity);
				
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
	
	
	//注册响应函数
		private void register(final String username,final String password,final int identity){
			AsyncInter inter=new AsyncInter() {
				String result="";
				@Override
				public void onPreExecute() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPostExecute() {
					// TODO Auto-generated method stub
					System.out.println("你好成功");
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
						System.out.println("你好"+username+password);
						params.add(new BasicNameValuePair("username", username));
						params.add(new BasicNameValuePair("password", password));
						params.add(new BasicNameValuePair("identity", String.valueOf(identity)));
						System.out.println("你好aaaa");
						System.out.println("你好"+params.get(2));
						result = NetHandler.netPost(getResources().getString(R.string.server_url)+"/user/register.action", params,a.getApplicationContext());
					}
					System.out.println("你好"+result);
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
				String userKey=jsonObject.getString("userKey");
				if(stateString.equals("true")){
					//注册成功 进入个人信息界面
					//String userId=jsonObject.getString("userId");
					System.out.println("注册成功");
					Toast.makeText(a.getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
					
					SharedPreferences userInfoSharedPreferences=a.getApplicationContext().getSharedPreferences("signinner",0 );
					Editor editor=userInfoSharedPreferences.edit();
					editor.putString("userKey", userKey);
					editor.putInt("identity", identity);
					editor.commit();
					String s=userInfoSharedPreferences.getString("userKey", "0");

					Intent intent=new Intent();
					intent.setClass(a.getApplicationContext(), InforActivity.class);
					a.startActivity(intent);
					
					
					
				}else{
					Toast.makeText(a.getApplicationContext(), "用户名已存在", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
