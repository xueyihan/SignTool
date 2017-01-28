package com.example.signinner.module.signin;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.signinner.module.me.MeFragment;
import com.example.signinner.module.message.MessageFragment;
import com.example.signinner.module.publish.PublishFragment;
import com.example.yswb.R;
import com.shizhefei.fragment.LazyFragment;

public class SigninFragment extends LazyFragment {
	
	private CanyuFragment canyuFragment;
	private FaqiFragment faqiFragment;
	private RadioGroup STabRg;
	private RadioButton radioDefault;
	
	
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_signin);
		initView();
	}
	
	public void initView(){
		canyuFragment=new CanyuFragment();
		
		STabRg = (RadioGroup) findViewById(R.id.tab_menu);
		final RadioButton radioDefault = (RadioButton)findViewById(R.id.select1);
		
	    getChildFragmentManager().beginTransaction().replace(R.id.main_content, canyuFragment).commit();
	    radioDefault.setChecked(true);
	    STabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {


		      @Override
		      public void onCheckedChanged(RadioGroup group, int checkedId) {
		    	
		        // TODO Auto-generated method stub
		        switch (checkedId) {
		        case R.id.select1:
		        	canyuFragment = new CanyuFragment();
		        	getChildFragmentManager().beginTransaction().replace(R.id.main_content, canyuFragment).commit();
		        	break;
		        case R.id.select2:
		        	faqiFragment =new FaqiFragment();
		        	getChildFragmentManager().beginTransaction().replace(R.id.main_content, faqiFragment).commit();
		          break;
		        default:
		          break;
		        }

		      }
		    });
	}

}
