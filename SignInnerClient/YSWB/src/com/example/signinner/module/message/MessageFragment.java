package com.example.signinner.module.message;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.yswb.R;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

public class MessageFragment extends LazyFragment {
	private IndicatorViewPager indicatorViewPager;
	private LayoutInflater inflate;
	
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_message);
		Resources res = getResources();
	}
}
