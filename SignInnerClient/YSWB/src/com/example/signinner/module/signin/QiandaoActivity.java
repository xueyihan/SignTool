package com.example.signinner.module.signin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.module.login.LuruActivity;
import com.example.signinner.module.login.MainActivity;
import com.example.yswb.R;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class QiandaoActivity extends Activity {
	
	private ImageView back;
	private Activity a;
	private Camera mCamera;
	private CameraPreview mPreview;
	protected static final String TAG = "main";
	int cameraCount = 0;
	String signId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qiandao);
		//��ȡ����
		Bundle extras=getIntent().getExtras();
		signId=extras.getString("signId");
		
		//��ȡUI
		back=(ImageView) findViewById(R.id.back_button);
		a=this;
		
		//�����¼�
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCamera!=null) {mCamera.release();mCamera=null;}
				a.finish();
			}
		});
		
		//cameraCount = Camera.getNumberOfCameras(); // get cameras number 
		//Toast.makeText(this, "dwadwa"+cameraCount, Toast.LENGTH_LONG).show();
		mCamera = getCameraInstance();
		mCamera.setDisplayOrientation(90);//���÷���
		// ����Ԥ���࣬����Camera�����������ӵ����沼����
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);


		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �ڲ���ͼƬǰ�����Զ��Խ�
				System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
				/*mCamera.autoFocus(new AutoFocusCallback() {
					
					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						// ��Camera����ͼƬ
						System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");*/
						mCamera.takePicture(null, null, mPicture);
				/*	}
				});	*/			
			}
		});
	}

	/** ����豸�Ƿ����CameraӲ�� */
	@SuppressWarnings("unused")
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// ����
			return true;
		} else {
			// ������
			return false;
		}
	}

	/** ��һ��Camera */
	@SuppressLint("NewApi")
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(1); 
		} catch (Exception e) {
			Log.d(TAG, "��Cameraʧ��ʧ��");
		}
		return c; 
	}

	private PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(final byte[] data, Camera camera) {
			new Thread(new Runnable() {
				public void run() {
					//��������Ƭ��ת
					Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
					Matrix m=new Matrix();
					int width = bm.getWidth();  
	                int height = bm.getHeight();
	                m.setRotate(270);
	                bm = Bitmap.createBitmap(bm, 0, 0, width, height,m, true);
	                ByteArrayOutputStream output = new ByteArrayOutputStream();
	                bm.compress(CompressFormat.PNG, 100,output);
	                byte[] d=output.toByteArray();
					
					
					
					
					//replace api_key and api_secret here (note)
					HttpRequests httpRequests = new HttpRequests("3427e5d837e92e4b2c5728a10919f231", "G1BSh5PHnpHX5ujbDw8IfFDN50X2gFBs ", true, true);
					JSONObject result = null,verify=null;
					try {
						output.close();
						System.out.println(Charset.forName("UTF-8").name());
						System.out.println("FacePlusPlus API Test:");
						
						//����ʶ��
						result = httpRequests.detectionDetect(new PostParameters().setImg(d));//����Ƭ���ͽ�������ʶ��
						System.out.println(result);
						
						
						if(result.getJSONArray("face").length()==0){
							//δ��������
							Looper.prepare();
							Toast.makeText(a.getApplicationContext(), "δ����������������¼��������Ϣ", Toast.LENGTH_LONG).show();
							//��ֹͣcamera��Դռ��
							mCamera.release();
							mCamera=null;
							
							System.out.println("δ��������");
							Intent intent=new Intent();
							intent.setClass(getApplicationContext(),QiandaoActivity.class);
							intent.putExtra("signId", signId);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getApplicationContext().startActivity(intent);
							a.finish();
							Looper.loop();
						}else if(result.getJSONArray("face").length()>1){
							//�������
							Looper.prepare();
							Toast.makeText(a.getApplicationContext(), "�������������Ϣ��������¼��", Toast.LENGTH_LONG).show();

							
							//��ֹͣcamera��Դռ��
							mCamera.release();
							mCamera=null;
							
							System.out.println("�����������");
							Intent intent=new Intent();
							intent.setClass(getApplicationContext(),QiandaoActivity.class);
							intent.putExtra("signId", signId);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getApplicationContext().startActivity(intent);
							a.finish();
							Looper.loop();
						}else{
							//����һ������
							String personId=UserInfo.getPersonId(a.getApplicationContext());
							System.out.println("ǩ��personId"+personId);
							verify=httpRequests.recognitionVerify(new PostParameters().setPersonId(personId).setFaceId(
										result.getJSONArray("face").getJSONObject(0).getString("face_id")));//����ʶ��
							double confidence=verify.getDouble("confidence");
							boolean is_same_person=verify.getBoolean("is_same_person");
							System.out.println(verify);
							System.out.println(confidence);
							System.out.println(is_same_person);
							if(confidence>50){
								//ʶ������Ŷȴ���50%
								if(is_same_person){
									//�Ǳ���
									//��ֹͣcamera��Դռ��
									mCamera.release();
									mCamera=null;
									Looper.prepare();
									String userKey=UserInfo.getUserKey(a.getApplicationContext());
									sign(userKey);
								}else{
									//���Ǳ���
									//��ֹͣcamera��Դռ��
									mCamera.release();
									mCamera=null;
									Looper.prepare();
									Toast.makeText(a.getApplicationContext(), "���Ǳ���", Toast.LENGTH_LONG).show();
									Intent intent=new Intent();
									intent.setClass(getApplicationContext(),QiandaoActivity.class);
									intent.putExtra("signId", signId);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									getApplicationContext().startActivity(intent);
									a.finish();
									Looper.loop();
								}
							}else{
								//ʶ������Ŷ�С��50%������ʶ��
								//��ֹͣcamera��Դռ��
								mCamera.release();
								mCamera=null;
								Looper.prepare();
								Toast.makeText(a.getApplicationContext(), "������ʶ��", Toast.LENGTH_LONG).show();
								Intent intent=new Intent();
								intent.setClass(getApplicationContext(),QiandaoActivity.class);
								intent.putExtra("signId", signId);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getApplicationContext().startActivity(intent);
								a.finish();
								Looper.loop();
							}
						}
					} catch(FaceppParseException e) {
						if(mCamera!=null){
							mCamera.release();
							mCamera=null;
						}
						Looper.prepare();
						Intent intent=new Intent();
						intent.setClass(getApplicationContext(),QiandaoActivity.class);
						intent.putExtra("signId", signId);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(intent);
						a.finish();
						Toast.makeText(a.getApplicationContext(), "����������,������ʶ��", Toast.LENGTH_LONG).show();
						Looper.loop();
						
						e.printStackTrace();} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
		 }).start();
		}
	};

	@Override
	protected void onDestroy() {
		// ����Camera��Դ
		if(mCamera!=null){
			mCamera.stopPreview();
			mCamera.release();
			mCamera=null;
		}
		super.onDestroy();
	}
	
	
	
	
	private void sign(final String userKey){
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
					System.out.println("�������δ����");
				}
				else{
					String wserviceName = Context.WIFI_SERVICE;
					WifiManager wm = (WifiManager) getSystemService(wserviceName);
					List<ScanResult> wifiList = wm.getScanResults();
					JSONArray json=new JSONArray();
					for (int i = 0; i < wifiList.size(); i++) {
						try {
							json.put(i, wifiList.get(i).BSSID);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userKey",userKey));
					params.add(new BasicNameValuePair("wifiJson",json.toString()));
					params.add(new BasicNameValuePair("signId",signId));
					System.out.println("���userkey"+userKey);
					System.out.println("���json"+json.toString());
					System.out.println("���signId"+signId);
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/sign/sign.action", params,a.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, true , a);
		task.execute();
	}
	
	
	
	public void jsonResolveList(String result){
		try {
			JSONObject j = new JSONObject(result);
			JSONObject jsonObject=j.getJSONObject("msg");
			String signResult = jsonObject.getString("signResult");
			if(signResult.equals("true")){
				Toast.makeText(a.getApplicationContext(), "ǩ���ɹ�", Toast.LENGTH_LONG).show();
				a.finish();
			}else{
				Toast.makeText(a.getApplicationContext(), "ʧ��", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
