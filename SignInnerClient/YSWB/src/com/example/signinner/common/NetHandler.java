package com.example.signinner.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class NetHandler {
private Context context = null;
	

/** 
 * �����Ƿ���� 
 *  
 * @param activity 
 * @return 
 */  
public static boolean isNetworkAvailable(Context context) {  
	String TAG="isisNetworkAvailable";
	
    ConnectivityManager connectivity = (ConnectivityManager) context  
            .getSystemService(Context.CONNECTIVITY_SERVICE);  
    if (connectivity == null) {  
    } else {  
        NetworkInfo[] info = connectivity.getAllNetworkInfo();  
        if (info != null) {  
            for (int i = 0; i < info.length; i++) {  
                if (info[i].getState() == NetworkInfo.State.CONNECTED) { 
                	String MSG="Context="+context;
                	Log.d(TAG, MSG);
                    return true;  
                }  
            }  
        }  
    }  
    String MSG="Context="+context;
	Log.d(TAG, MSG);
    return false;  
}  


/** 
 * GPS�Ƿ�� 
 *  
 * @param context 
 * @return 
 */  
public static boolean isGpsEnabled(Context context) {  
	String TAG="isGpsEnabled";
    LocationManager locationManager = ((LocationManager) context  
            .getSystemService(Context.LOCATION_SERVICE));  
    List<String> accessibleProviders = locationManager.getProviders(true);
    String MSG="accessibleProviders="+accessibleProviders;
	Log.d(TAG, MSG);
    return accessibleProviders != null && accessibleProviders.size() > 0;  
}  

/** 
 * wifi�Ƿ�� 
 */  
public static boolean isWifiEnabled(Context context) { 
	String TAG="isWifiEnabled";
    ConnectivityManager mgrConn = (ConnectivityManager) context  
            .getSystemService(Context.CONNECTIVITY_SERVICE);  
    TelephonyManager mgrTel = (TelephonyManager) context  
            .getSystemService(Context.TELEPHONY_SERVICE);  
   Boolean a=(mgrConn.getActiveNetworkInfo() != null && mgrConn  
            .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel  
            .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS;
   String MSG="wifi�Ƿ��"+a;
    Log.d(TAG, MSG);
    return a;
}  

/** 
 * �жϵ�ǰ�����Ƿ���wifi���� 
 * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {  
 *  
 * @param context 
 * @return boolean 
 */  
public static boolean isWifi(Context context) { 
	String TAG="isWifi";
    ConnectivityManager connectivityManager = (ConnectivityManager) context  
            .getSystemService(Context.CONNECTIVITY_SERVICE);  
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
    if (activeNetInfo != null  
            && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) { 
    	String  MSG="��ǰ�����Ƿ���wifi����=true";
    	Log.d(TAG, MSG);
        return true;  
    }  
	String  MSG="��ǰ�����Ƿ���wifi����=false";
	Log.d(TAG, MSG);
    return false;  
}  

/** 
 * �жϵ�ǰ�����Ƿ�3G���� 
 *  
 * @param context 
 * @return boolean 
 */  
public static boolean is3G(Context context) { 
	String TAG="is3G";
    ConnectivityManager connectivityManager = (ConnectivityManager) context  
            .getSystemService(Context.CONNECTIVITY_SERVICE);  
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
    if (activeNetInfo != null  
            && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
    	String MSG="��ǰ�����Ƿ�3G����=true";
    	Log.d(TAG, MSG);
        return true;  
    }
    String MSG="��ǰ�����Ƿ�3G����=true";
	Log.d(TAG, MSG); 
    return false;  
}  



	/**
	 * �ж��Ƿ�������
	 * @return
	 */
	public static boolean netJudge(Context context)
	{
		String TAG="netJudge";
		boolean connect = true;
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if( cm == null ){
		   return false;
		}
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null ){
		   return false;
		}
		if(!netinfo.isConnected()){
		   connect = false;
		}
		
		final NetworkInfo[] infos = cm.getAllNetworkInfo();

        for (final NetworkInfo info : infos) {
            if (info != null) {
                if (info.isConnectedOrConnecting()) {
                    final android.net.NetworkInfo.State networkState = info.getState();
                    if (android.net.NetworkInfo.State.CONNECTED == networkState
                            || android.net.NetworkInfo.State.CONNECTING == networkState) {
                        // if (Logging.isEnabled)
                        {
                            
                            try {
                                 InetAddress.getByName( "192.168.1.27").isReachable(1000);
                                 String MSG="��������=true";
                                 Log.d(TAG, MSG);
                            } catch (UnknownHostException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                String MSG="��������=false";
                                Log.d(TAG, MSG);
                            	return false;
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                String MSG="��������=false";
                                Log.d(TAG, MSG);
                            	return false;
                            }
                        }
                        
                    }
                }
            }
        }
        return connect;
	}
	
	/**
	 * netGet����
	 * @param url
	 * @return
	 */
	public static String netGet(String url, Context context)
	{
		String result = "";
		String TAG="netGet";
		if(!netJudge(context))
			return result;
		HttpClient httpClient = new DefaultHttpClient();//����һ��Http�ͻ��˶���;
		HttpGet httpGet  = new HttpGet(url);//����һ���������
		try
		{
			HttpResponse httpResponse = httpClient.execute(httpGet);//ʹ��Http�ͻ��˷����������
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				result = result + line;
			}
//            result = result.substring(0, result.lastIndexOf("}")+1);
			byte[] b = result.getBytes();
			result = new String(b, "UTF-8");
			System.out.println("result = " + result);//������
			
		}
	    catch(Exception e)
		{
//	    	String MSG="netGet="+result+"url="+url;
//			Log.d(TAG, MSG);  
	    	e.printStackTrace();
		}
//		String MSG="netGet="+result+"url="+url;
//		Log.d(TAG, MSG);
       
		return result;
	}
	/**
	 * Post����
	 * @param url
	 * @param params
	 * @return
	 */
	public static String netPost(String url, List<NameValuePair> params, Context context)
	{		
		String result = "";
		try {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(params,HTTP.UTF_8);//�Բ������б������
            //����һ��post�������
            
            HttpPost httpPost = new HttpPost(url);
            System.out.println("���"+url);
            httpPost.setEntity(requestHttpEntity);
            InputStream inputStream=null;
            //����һ��http�ͻ��˶���
            HttpClient httpClient = new DefaultHttpClient();//��������
            try {
            	System.out.println("��÷�������");
                HttpResponse httpResponse = httpClient.execute(httpPost);//������Ӧ
                System.out.println("��÷�������");
                HttpEntity httpEntity = httpResponse.getEntity();//ȡ����Ӧ
                //�ͻ����յ���Ӧ����Ϣ��
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                
                String line = "";
                while((line = reader.readLine()) != null){
                    result = result + line;
                }
//                byte[] b = result.getBytes();
//                result = new String(b, "gb2312");
//                String re = result.substring(0, result.lastIndexOf("}"));
//                System.out.println(re);
                //System.out.println("result = " + result);
               
            } catch (Exception e) {
                e.printStackTrace();
            } finally{//���һ��Ҫ�ر�������
                try{
                    inputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("post result = " + result);
		 return result;
    }
	public static String netPostJson(String url, JSONObject params) { 
		String result = ""; 
		try {  
		    HttpClient httpClient = new DefaultHttpClient();  
		    HttpPost httpPost = new HttpPost(url);   
		    httpPost.setEntity(new StringEntity(params.toString()));    
            InputStream inputStream=null; 
		    try {
                HttpResponse httpResponse = httpClient.execute(httpPost);//������Ӧ
                HttpEntity httpEntity = httpResponse.getEntity();//ȡ����Ӧ
                //�ͻ����յ���Ӧ����Ϣ��
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                
                String line = "";
                while((line = reader.readLine()) != null){
                    result = result + line;
                }
               
            } catch (Exception e) {
                e.printStackTrace();
            } finally{//���һ��Ҫ�ر�������
                try{
                    inputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("post result = " + result);
		 return result;
		}  
    /**
     * getFile����
     * @param uri
     * @return
     */
	public static String getFile(String uri){
    	String s = "";
    	String TAG="getFile";
        try { 
            URL url = new URL(uri); 
            URLConnection conexion = url.openConnection(); 
            conexion.connect(); 
            InputStream input = new BufferedInputStream(url.openStream()); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				s = s + line;
			
            reader.close();
            input.close(); 
        } }
        catch (Exception e) { 
            Log.e("error",e.getMessage().toString()); 
            System.out.println(e.getMessage().toString()); 
            String MSG="message="+e.getMessage().toString()+"url"+uri;
            Log.d(TAG, MSG);
        } 
        String MSG="url"+uri;
        Log.d(TAG, MSG);
        return s; 

	}
/**
 * getPic����
 * @param url
 * @return
 */
	public static Bitmap getPic(String url) { 
		String TAG="getPic";
		if(url == null)
			return null;
		
	    URL myFileUrl = null;   
	    Bitmap bitmap = null;   
	    try {   
	    	myFileUrl = new URL(url);   
	    } catch (MalformedURLException e) {   
	    e.printStackTrace();   
	    }   
	    try {   
	    	HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();   
	    	conn.setDoInput(true);   
	    	conn.connect();   
	    	InputStream is = conn.getInputStream();   
	    	bitmap = BitmapFactory.decodeStream(is);   
	    	is.close();   
	    	conn.disconnect();
	    } catch (IOException e) {  
	    	String MSG="bitmap="+bitmap+"URL="+url+"e="+e;
	 	    Log.d(TAG, MSG);
	    e.printStackTrace();   
	    }
	    String MSG="bitmap="+bitmap+"URL="+url;
	    Log.d(TAG, MSG);
	    return bitmap;   
    }
	
	public static boolean downLoadFile(String url, String dir, Context context){
		if(!netJudge(context))
			return false;
		String newFilename = url.substring(url.lastIndexOf("/")+1);
		newFilename = dir + newFilename;
		File file = new File(newFilename);
		//���Ŀ���ļ��Ѿ����ڣ���ɾ�����������Ǿ��ļ���Ч��
		if(file.exists())
		{
		    file.delete();
		}
		try {
		         // ����URL   
		         URL uri = new URL(url);   
		         // ������   
		         URLConnection con = uri.openConnection();
		         //����ļ��ĳ���
		         int contentLength = con.getContentLength();
		         System.out.println("���� :"+contentLength);
		         // ������   
		         InputStream is = con.getInputStream();  
		         // 1K�����ݻ���   
		         byte[] bs = new byte[1024];   
		         // ��ȡ�������ݳ���   
		         int len;   
		         // ������ļ���   
		         OutputStream os = new FileOutputStream(newFilename);   
		         // ��ʼ��ȡ   
		         while ((len = is.read(bs)) != -1) {   
		             os.write(bs, 0, len);   
		         }  
		         // ��ϣ��ر���������   
		         os.close();  
		         is.close();
		         return true;
		} catch (Exception e) {
		        e.printStackTrace();
		}
		return false;
	}

}
