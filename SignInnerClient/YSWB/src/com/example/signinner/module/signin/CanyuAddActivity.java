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
import android.widget.TextView;
import android.widget.Toast;

public class CanyuAddActivity extends Activity {

	ImageView back;
	TextView contain_button;
	PullToRefreshListView listview;
	String userKey;
	Activity a;
	SearchAdapter searchAdapter;
	HashMap<String, Boolean> h;
	JSONArray selectedId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin_canyu_add);
		
		//获取UI
		contain_button=(TextView) findViewById(R.id.contain_button);
		back=(ImageView) findViewById(R.id.back_button);
		a=this;
		listview=(PullToRefreshListView) findViewById(R.id.search_activity_listview);
		getData();
		//添加监听事件
		contain_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectedId =new JSONArray();
				Iterator iter = h.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					System.out.println("请问"+(String) entry.getKey());
					System.out.println("请问"+String.valueOf(entry.getValue()));
					if((Boolean) entry.getValue())
						selectedId.put((String) entry.getKey());
				}
				send();
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
	
	
	protected void send() {
		AsyncInter inter=new AsyncInter() {
			String result="";
			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onPostExecute() {
				// TODO Auto-generated method stub
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONObject msg=jsonObject.getJSONObject("msg");
					if(msg.getString("state")=="true"){
						Toast.makeText(a.getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
						a.finish();
					}else{
						Toast.makeText(a.getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
						params.add(new BasicNameValuePair("idArray",selectedId.toString()));
						System.out.println("请问"+selectedId);
						result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/activity/addCanyuActivity.action", params,a.getApplicationContext());
					}
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true, a);
		task.execute();
		
	}


	private void getData() {
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
		searchAdapter=new SearchAdapter(a, coll);
		listview.setAdapter(searchAdapter);
		h=searchAdapter.selected;
	}
}
