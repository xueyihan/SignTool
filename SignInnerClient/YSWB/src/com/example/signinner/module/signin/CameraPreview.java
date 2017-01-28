package com.example.signinner.module.signin;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ����һ��Ԥ����
 */
@SuppressWarnings("deprecation")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "main";
	private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // ͨ��SurfaceView���SurfaceHolder
        mHolder = getHolder();
        // ΪSurfaceHolderָ���ص�
        mHolder.addCallback(this);
        // ����Surface��ά���Լ��Ļ����������ǵȴ���Ļ����Ⱦ���潫�������͵����� ��Android3.0֮������
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // ��Surface������֮�󣬿�ʼCamera��Ԥ��
        try {
        	mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Ԥ��ʧ��");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    	
    	mCamera.autoFocus(new AutoFocusCallback() {  
            @Override  
            public void onAutoFocus(boolean success, Camera camera) {  
                if(success){  
                	mCamera.getParameters().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); 
                    camera.cancelAutoFocus();//ֻ�м�������һ�䣬�Ż��Զ��Խ���  
                }  
            }  

        });  
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        /*// Surface�����ı��ʱ�򽫱����ã���һ����ʾ�������ʱ��Ҳ�ᱻ����
        if (mHolder.getSurface() == null){
          // ���SurfaceΪ�գ�����������
          return;
        }

        // ֹͣCamera��Ԥ��
        try {
            mCamera.stopPreview();
        } catch (Exception e){
        	Log.d(TAG, "��Surface�ı��ֹͣԤ������");
        }

        // ��Ԥ��ǰ����ָ��Camera�ĸ������

        // ���¿�ʼԤ��
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Ԥ��Camera����");
        }*/
    }
}
