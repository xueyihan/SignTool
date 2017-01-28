package com.example.signinner.module.signin;

import com.example.yswb.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ActivityMember extends Activity {
	Activity a;
	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_member);
		
		back=(ImageView) findViewById(R.id.back_button);
		a=this;
		//Ìí¼Ó¼àÌýÊÂ¼þ
				back.setOnClickListener(new OnClickListener() {
							
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						a.finish();
					}
				});
	}
	
}
