package com.example.signinner.module.login;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.signinner.common.AsyncInter;
import com.example.signinner.common.MyAsyncTask;
import com.example.signinner.common.NetHandler;
import com.example.signinner.common.UserInfo;
import com.example.signinner.module.signin.CameraPreview;
import com.example.yswb.R;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LuruActivity extends Activity {
	
	ImageView back;
	private Activity a;
	private Camera mCamera;
	private CameraPreview mPreview;
	protected static final String TAG = "main";
	private static String mApiKey="3427e5d837e92e4b2c5728a10919f231"; 
	private static String mApiSecret="G1BSh5PHnpHX5ujbDw8IfFDN50X2gFBs";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luru);
		a=this;
		back=(ImageView) findViewById(R.id.back_button);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCamera!=null) {mCamera.release();mCamera=null;}
				a.finish();
			}
		});
		
		
		
		mCamera = getCameraInstance();
		mCamera.setDisplayOrientation(90);
		
		List<Size> list = mCamera.getParameters().getSupportedPictureSizes();
		mCamera.getParameters().setPictureSize(list.get(5).width, list.get(5).height);
		mCamera.getParameters().setPreviewSize(list.get(5).width, list.get(5).height);
		for(int i=0;i<list.size();i++)
		{System.out.println("大小宽"+list.get(i).width);
		System.out.println("大小高"+list.get(i).height);}
		// 创建预览类，并与Camera关联，最后添加到界面布局中
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 在捕获图片前进行自动对焦
				mCamera.getParameters().setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
				if(mCamera.getParameters().getFocusMode()==Parameters.FOCUS_MODE_AUTO||mCamera.getParameters().getFocusMode()==Parameters.FOCUS_MODE_MACRO){
					System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
					mCamera.autoFocus(new AutoFocusCallback() {
						@Override
						public void onAutoFocus(boolean success, Camera camera) {
							// 从Camera捕获图片
							System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
						}
					});		
				}
				mCamera.takePicture(null, null, mPicture);
			}
		});
	}

	/** 检测设备是否存在Camera硬件 */
	@SuppressWarnings("unused")
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// 存在
			return true;
		} else {
			// 不存在
			return false;
		}
	}

	/** 打开一个Camera */
	@SuppressLint("NewApi")
	public Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(1); 
		} catch (Exception e) {
			Log.d(TAG, "打开Camera失败失败");
		}
		return c; 
	}

	private PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(final byte[] data, Camera camera) {
			 new Thread(new Runnable() {
					public void run() {
						//将生成照片旋转
						Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
						Matrix m=new Matrix();
						int width = bm.getWidth();  
		                int height = bm.getHeight();
		                m.setRotate(270);
		                bm = Bitmap.createBitmap(bm, 0, 0, width, height,m, true);
		                ByteArrayOutputStream output = new ByteArrayOutputStream();
		                bm.compress(CompressFormat.JPEG, 100,output);
		                byte[] d=output.toByteArray();
						
		                //将照片存入sd卡
						File pictureFile = new File("/sdcard/" + System.currentTimeMillis()+".jpg");
						
						//face++API
						HttpRequests httpRequests = new HttpRequests("3427e5d837e92e4b2c5728a10919f231", "G1BSh5PHnpHX5ujbDw8IfFDN50X2gFBs ", true, true);
						JSONObject result = null;
						try {
							output.close();
							FileOutputStream fos = new FileOutputStream(pictureFile);
							fos.write(d);
							fos.close();
							System.out.println(Charset.forName("UTF-8").name());
							System.out.println("FacePlusPlus API Test:");
							//detection/detect
							result = httpRequests.detectionDetect(new PostParameters().setImg(d)); //发送照片人脸识别
							System.out.println("result:"+result);
							if(result.getJSONArray("face").length()==0){
								//未检测出人脸
								Looper.prepare();
								Toast.makeText(a.getApplicationContext(), "未检测出人脸，请重新录入人脸信息", Toast.LENGTH_LONG).show();
								//先停止camera资源占用
								mCamera.release();
								mCamera=null;
								System.out.println("未检测出人脸");
								Intent intent=new Intent();
								intent.setClass(getApplicationContext(),LuruActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getApplicationContext().startActivity(intent);
								a.finish();
								Looper.loop();
							}else if(result.getJSONArray("face").length()>1){
								//多个人脸
								Looper.prepare();
								Toast.makeText(a.getApplicationContext(), "检测出多个人脸信息，请重新录入", Toast.LENGTH_LONG).show();
								//先停止camera资源占用
								mCamera.release();
								mCamera=null;
								System.out.println("检测出多个人脸");
								Intent intent=new Intent();
								intent.setClass(getApplicationContext(),LuruActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getApplicationContext().startActivity(intent);
								a.finish();
								Looper.loop();
							}else{
								//检测出一个人脸
								
								Looper.prepare();
								//先停止camera资源占用
								mCamera.release();
								mCamera=null;
								System.out.println("检测出一个人脸");
								JSONObject person=httpRequests.personCreate(new PostParameters()); //创建person
								String id=person.getString("person_id");//获取创建的person的id
								if(id!=null){
									//face++信息录入成功,返回后台录入信息，进入主页
									System.out.println("签到personId"+id);
									System.out.println("签到faceId"+result.getJSONArray("face").getJSONObject(0).getString("face_id"));
									httpRequests.personAddFace(new PostParameters().setPersonId(id).setFaceId(result.getJSONArray("face").getJSONObject(0).getString("face_id")));//将人脸信息存入person
									httpRequests.trainVerify(new PostParameters().setPersonId(id)); //训练person
									String userKey=UserInfo.getUserKey(a.getApplicationContext());
									if(userKey!=null){
										System.out.println("你好！！！");
										faceInfo(userKey,id);
									}else{
										//未登录，返回登录界面
										Intent intent=new Intent();
										intent.setClass(a.getApplicationContext(), LoginActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										a.startActivity(intent);
									}
									Looper.loop();
								}else{
									Looper.prepare();
									Toast.makeText(a.getApplicationContext(), "创建失败,请重新录入", Toast.LENGTH_LONG).show();
									//先停止camera资源占用
									mCamera.release();
									mCamera=null;
									a.finish();
									Looper.loop();
								}
							}
						}catch(FaceppParseException e) {
							System.out.println("签到"+e.getMessage());
								if(mCamera!=null){
									mCamera.stopPreview();
									mCamera.release();
									mCamera=null;
								}
								Toast.makeText(a.getApplicationContext(), "服务器繁忙，请稍后再试", Toast.LENGTH_LONG).show();
								Intent intent=new Intent();
								intent.setClass(getApplicationContext(),LuruActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getApplicationContext().startActivity(intent);
								a.finish();
								e.printStackTrace();
								Looper.loop();
							}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			 }).start();
		}
	};

	@Override
	protected void onDestroy() {
		// 回收Camera资源
		if(mCamera!=null){
			mCamera.stopPreview();
			mCamera.release();
			mCamera=null;
		}
		super.onDestroy();
	}
	
	private void faceInfo(final String userKey,final String person_id){
		AsyncInter inter=new AsyncInter() {
			String result="";
			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPostExecute() {
				// TODO Auto-generated method stub
				jsonResolveList(result,person_id);
			}
			
			@Override
			public void interruptTask() {
				// TODO Auto-generated method stub
			}
			@Override
			public void doInBackground() {
				// TODO Auto-generated method stub
				if (NetHandler.netJudge(a.getApplicationContext())==false){
					System.out.println("你好网络未连接");
				}
				else{
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("userKey",userKey));
					params.add(new BasicNameValuePair("person_id",person_id));
					System.out.println(userKey);
					System.out.println(person_id);
					result=NetHandler.netPost(getResources().getString(R.string.server_url)+"/user/luru.action", params,a.getApplicationContext());
				}
			}
		};
		MyAsyncTask task = new MyAsyncTask(inter, false, a);
		task.execute();
	}
	
	
	
	public void jsonResolveList(String result,String person_id){
		try {
			JSONObject j = new JSONObject(result);
			JSONObject jsonObject=j.getJSONObject("msg");
			String stateString = jsonObject.getString("state");
			if(stateString.equals("true")){
				//注册成功 进入个人信息界面
				//String userId=jsonObject.getString("userId");
				System.out.println("录入人脸成功");
				Toast.makeText(a.getApplicationContext(), "信息录入成功，请等待管理员审核！", Toast.LENGTH_LONG).show();
				UserInfo.setPersonId(person_id, a.getApplicationContext());
				Intent intent=new Intent();
				intent.setClass(a.getApplicationContext(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				a.startActivity(intent);
				a.finish();
			}else{
				Toast.makeText(a.getApplicationContext(), "失败", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
