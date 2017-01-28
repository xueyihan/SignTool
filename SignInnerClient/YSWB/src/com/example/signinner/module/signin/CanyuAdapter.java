package com.example.signinner.module.signin;

import java.sql.Timestamp;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signinner.entity.ActivityEntity;
import com.example.yswb.R;

public class CanyuAdapter extends BaseAdapter{

	private List<ActivityEntity> coll;
	private Context ctx;
	private LayoutInflater mInflater;
	
	public CanyuAdapter(Context context, List<ActivityEntity> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO �Զ����ɵķ������
		return coll.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO �Զ����ɵķ������
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO �Զ����ɵķ������
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		final ActivityEntity entity = coll.get(position);
		ViewHolder viewHolder = null;
		int restTime=0;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.signin_canyu_card,null);

			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_name);
			viewHolder.time = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_time);
			viewHolder.position = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_position);
			viewHolder.resttime = (TextView) convertView
					.findViewById(com.example.yswb.R.id.canyu_qiandao_resttime);
			
			//��ȡ��ǰʱ��
			Timestamp currentTime=new Timestamp(System.currentTimeMillis());
			Timestamp endTime=entity.getEndTime();
			Timestamp startTime=entity.getStartTime();
			long current,end;
			current=currentTime.getTime();
			end=endTime.getTime();
			if(currentTime.compareTo(endTime)<0&&currentTime.compareTo(startTime)>0){
				//ǩ��ʱ����
				System.out.println("��ã�"+(end-current));
				restTime=(int)(end-current)/60000;
				
				if(entity.getQiandaoStatus()==2){
					//δǩ��������ǩ����ť
					viewHolder.resttime = (TextView) convertView
							.findViewById(com.example.yswb.R.id.canyu_qiandao_resttime);
					TextView t=(TextView) convertView
							.findViewById(com.example.yswb.R.id.canyu_qiandao_button);
					t.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String signId=entity.getSignId();
							Intent intent=new Intent();
							intent.setClass(ctx, QiandaoActivity.class);
							intent.putExtra("signId", signId);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							ctx.getApplicationContext().startActivity(intent);
						}
					});
				}
				if(entity.getQiandaoStatus()==1){
					//��ǩ��
					LinearLayout l=(LinearLayout) convertView.findViewById(R.id.canyu_qiandao_status);
					l.removeAllViews();
					TextView t=new TextView(ctx);
					t.setText("��ǩ��");
					t.setGravity(Gravity.CENTER);
					t.setHeight(140);
					t.setWidth(LayoutParams.WRAP_CONTENT);
					t.setTextSize(25);
					t.setTextColor(0xffcccccc);
					l.addView(t);
				}
			}
			if(currentTime.compareTo(endTime)>0||currentTime.compareTo(startTime)<0){
				//���һ��ǩ���ѽ�������ǩ��δ��ʼ
				LinearLayout l=(LinearLayout) convertView.findViewById(R.id.canyu_qiandao_status);
				l.removeAllViews();
				TextView t=new TextView(ctx);
				t.setText("δ��ʼ");
				t.setGravity(Gravity.CENTER);
				t.setHeight(135);
				t.setWidth(LayoutParams.WRAP_CONTENT);
				t.setTextSize(25);
				t.setTextColor(0xffcccccc);
				l.addView(t);
			}
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.name.setText("���ƣ�"+entity.getName());
		viewHolder.position.setText("    �ص㣺"+entity.getPosition());
		viewHolder.time.setText("ʱ�䣺"+entity.getTime());
		viewHolder.resttime.setText("ʣ��"+restTime+"����");
		return convertView; 
	}
	
	static class ViewHolder {
		public TextView name;
		public TextView time;
		public TextView position;
		public TextView resttime;
	}
	
}