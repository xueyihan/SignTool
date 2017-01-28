package com.example.signinner.common;

import com.example.yswb.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * 异步操作类
 * @author sqhome
 *
 */
public class MyAsyncTask extends AsyncTask<Void, Void, Void> implements Runnable{

	//时间控制线程
	private Thread thread;
	//进度条
	private ProgressDialog dialog;
	//异步操作
	private AsyncInter function;
	//异步操作限时
	private int MAX_TIME = 20;
	//是否存在进度条
	private boolean existDialog;
	//上下文
	private Context context;
	//现在运行时间
	private float time = 0;
	private static final String TAG = "MyAsyncTask";
	
	/**
	 * 构造函数
	 * @param func 异步操作
	 * @param existDialog 是否存在进度条
	 * @param context 上下文
	 */
	public MyAsyncTask(AsyncInter func, boolean existDialog, Context context) {

		Log.d(TAG, "MyAsyncTask construct");
		
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
        dialog.setMessage(context.getText(R.string.loading));  
        dialog.setIndeterminate(false);
        dialog.setCanceledOnTouchOutside(false);
        function = func;
        
        thread = new Thread(this);
        
        this.existDialog = existDialog;
        
        this.context = context;
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCancelled(Void result) {
		Log.d(TAG, "MyAsyncTask onCancelled result");
		super.onCancelled(result);
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();

		Log.d(TAG, "MyAsyncTask onCancelled");
		if(time < MAX_TIME)
			return;
	    	dialog.cancel();
	    	function.interruptTask();
	    	Toast.makeText(context, R.string.connect_timeout, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyAsyncTask doInBackground");
		function.doInBackground();
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyAsyncTask onPreExecute");
		super.onPreExecute();
		if(existDialog)
		{
			dialog.show();
		}
		thread.start();
		function.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyAsyncTask onPostExecute");
		super.onPostExecute(result);
		if(existDialog)
		{
			dialog.cancel();
		}
		function.onPostExecute();
		if(!Thread.interrupted())
			thread.interrupt();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int i = 0;
			time = 0;
            do
            {
            	i++;
                Thread.sleep(500);
                time += 0.5f;
                if(time > MAX_TIME)
                {
                	this.cancel(false);
                	break;
                }
                System.out.println("{" + 0.5 * i + "s}");
            }while(Thread.interrupted()==false);
        } catch (Exception e) {
            // TODO: handle exception
           e.printStackTrace();
        }
	}
}
