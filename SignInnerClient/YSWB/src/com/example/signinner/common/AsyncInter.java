package com.example.signinner.common;
/**
 * �첽�ӿ�
 * @author sqhome
 *
 */
public interface AsyncInter {

	//�첽����ǰ
	public void onPreExecute();
	//�첽������
	public void onPostExecute();
	//�첽������
	public void doInBackground();
	//�첽�����ж�
	public void interruptTask();
}
