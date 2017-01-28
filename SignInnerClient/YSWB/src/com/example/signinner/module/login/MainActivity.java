package com.example.signinner.module.login;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.module.me.MeFragment;
import com.example.signinner.module.message.MessageFragment;
import com.example.signinner.module.publish.PublishFragment;
import com.example.signinner.module.signin.SigninFragment;
import com.example.yswb.R;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	
	private SigninFragment signinFragment;
	private MessageFragment messageFragment;
	private PublishFragment publishFragment;
	private MeFragment meFragment;
	private RadioGroup myTabRg;
    private RadioButton radioDefault;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%");
		String wserviceName = Context.WIFI_SERVICE;
		WifiManager wm = (WifiManager) getSystemService(wserviceName);
		List<ScanResult> wifiList = wm.getScanResults();
		HashSet<String> set=new HashSet<String>();
		JSONArray json=new JSONArray();
		for (int i = 0; i < wifiList.size(); i++) {
			ScanResult result = wifiList.get(i);
			try {
				json.put(i, wifiList.get(i).BSSID);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("ÄãºÃ"+json);
		
		initView();
	}
	public void initView() {
		messageFragment = new MessageFragment();
	    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, messageFragment).commit();
	    myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
	    final RadioButton radioDefault = (RadioButton)findViewById(R.id.select1);
	    radioDefault.setChecked(true);
	    myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {


	      @Override
	      public void onCheckedChanged(RadioGroup group, int checkedId) {
	    	
	        // TODO Auto-generated method stub
	        switch (checkedId) {
	        case R.id.select2:
	          signinFragment = new SigninFragment();
	          getSupportFragmentManager().beginTransaction().replace(R.id.main_content, signinFragment).commit();
	          break;
	        case R.id.select1:
	        	  messageFragment =new MessageFragment();
	          getSupportFragmentManager().beginTransaction().replace(R.id.main_content, messageFragment).commit();
	          break;
	       
	        case R.id.select4:
	        	meFragment =new MeFragment();
	        	getSupportFragmentManager().beginTransaction().replace(R.id.main_content, meFragment)
	              .commit();
	          break;
	        default:
	          break;
	        }

	      }
	    });

	}
}
