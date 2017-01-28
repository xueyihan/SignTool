package com.example.signinner.common;
/**
 * 异步接口
 * @author sqhome
 *
 */
public interface AsyncInter {

	//异步操作前
	public void onPreExecute();
	//异步操作后
	public void onPostExecute();
	//异步操作中
	public void doInBackground();
	//异步操作中断
	public void interruptTask();
}
