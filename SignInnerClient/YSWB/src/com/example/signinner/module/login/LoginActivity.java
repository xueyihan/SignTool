package com.example.signinner.module.login;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.UserInfo;
import com.example.yswb.R;
import com.example.yswb.R.layout;
import com.example.yswb.R.menu;
import com.example.signinner.common.NetHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	RadioButton student;
	RadioButton teacher;
	private TextView registerButton;
	private Button loginButton;
	private EditText username;
	private EditText password;
	private Activity a;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//获取控件
		registerButton=(TextView) findViewById(R.id.register_button);
		username=(EditText) findViewById(R.id.login_username);
		password=(EditText) findViewById(R.id.login_password);
		loginButton=(Button) findViewById(R.id.login_button);
		student=(RadioButton) findViewById(R.id.register_student);
		teacher=(RadioButton) findViewById(R.id.register_teacher);
		a=this;
		student.setChecked(true);
		//监听事件
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent registerIntent=new Intent();
				registerIntent.setClass(getApplicationContext(), RegisterActivity.class);
				startActivity(registerIntent);
			}
		});
		
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login(username.getText().toString(),password.getText().toString());
			}
		});
	}
	
	//请求登录函数
	private void login(final String username,final String password){
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
					params.add(new BasicNameValuePair("username", username));
					params.add(new BasicNameValuePair("password", password));
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/user/login.action", params,a.getApplicationContext());
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
				JSONObject user=jsonObject.getJSONObject("user");
				//保存用户基本信息
				UserInfo.setUserInfo(user.getString("id"),
						user.getString("school"), 
						user.getString("major"),
						user.getString("number"),
						user.getString("year"),
						user.getString("vertification"),
						user.getString("personId"),
						user.getString("identity"),
						user.getString("name"),
						a.getApplicationContext());
				Intent intent=new Intent();
				intent.setClass(a.getApplicationContext(), MainActivity.class);
				a.startActivity(intent);
				a.finish();
			}else{
				Toast.makeText(a.getApplicationContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
