package com.example.signinner.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * 数据存储
 * @author sqhome
 *
 */
public class CacheHandler {  
	
	private static final String TAG = "DataCache";
	private String SDPATH = Environment.getExternalStorageDirectory() + "/";

	private static long MAX_LEN = 5 * 1024 * 1024;
	private static float percent = 2 / 3;
	/**
	 * 写入缓存
	 * @param context 上下文
	 * @param cacheName 缓存文件名
	 * @param fieldName 缓存字段名称
	 * @param data 存入的数据
	 * @return 是否写入成功
	 */
	public static boolean writeCache(Context context, String cacheName,String fieldName, String data){
		Log.d(TAG, "writeCache");
		SharedPreferences preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(fieldName, data);
		editor.commit();
		return true;
	}

	/**
	 * 是否存在该缓存
	 * @param context 上下文
	 * @param cacheName 缓存文件名
	 * @param fieldName 缓存字段名称
	 * @return
	 */
	public static boolean judgeCache(Context context, String cacheName, String fieldName) {
		Log.d(TAG, "judgeCache");
		SharedPreferences preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
		if(preferences.contains(fieldName))
			return true;
		return false;
	}
	
	public static void clearCache(Context context, String cacheName){
		SharedPreferences preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 读缓存
	 * @param context 上下文
	 * @param cacheName 缓存文件名称
	 * @param fieldName 缓存字段名称
	 * @return 是否读成功
	 */
	public static String readCache(Context context, String cacheName, String fieldName){
		Log.d(TAG, "readCache");
		SharedPreferences preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
		String result = preferences.getString(fieldName, "");
		return result;
	}
	public InputStream getInputStreamFromURL(String urlStr) {  
        HttpURLConnection urlConn = null;  
        InputStream inputStream = null;  
        try {  
            URL url = new URL(urlStr);  
            urlConn = (HttpURLConnection)url.openConnection();  
            inputStream = urlConn.getInputStream();  
              
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return inputStream;  
    }  
	public int downFile(String urlStr, String path, String fileName){  
        InputStream inputStream = null;  
        try {  
                inputStream = getInputStreamFromURL(urlStr);  
                File resultFile = write2SDFromInput(path, fileName, inputStream);  
                if(resultFile == null){  
                    return -1;  
                }  
         
        }   
        catch (Exception e) {  
            e.printStackTrace();  
            return -1;  
        }  
        finally{  
            try {  
                inputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return 0;  
    }  
	
	public File write2SDFromInput(String path,String fileName,InputStream input){  
        File file = null;  
        int FILESIZE = 4 * 1024;  
        OutputStream output = null;  
        try {  
            createSDDir(path);  
            file = createSDFile(path + fileName);  
            
            output = new FileOutputStream(file);  
                            byte[] buffer = new byte[FILESIZE];  
  
            /*真机测试，这段可能有问题，请采用下面网友提供的  
                            while((input.read(buffer)) != -1){  
                output.write(buffer);  
            }  
                            */  
  
                           /* 网友提供 begin */  
                           int length;  
                           while((length=(input.read(buffer))) >0){  
                                 output.write(buffer,0,length);  
                           }  
                           /* 网友提供 end */  
  
            output.flush();  
        }   
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try {  
                output.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return file;  
    }  
	public File createSDFile(String fileName) throws IOException{  
        File file = new File(SDPATH + fileName);  
        file.createNewFile();  
        return file;  
    }  

    public File createSDDir(String dirName){  
        File dir = new File(SDPATH + dirName);  
        dir.mkdir();  
        return dir;  
    } 
    
    /**
     * 写入文件
     * @param path  文件路径
     * @param writestr 写入的内容
     * @throws IOException
     */
    public static void writeFile(String path,String writestr) throws IOException{  
    		
    	
    	  try{   
    	  
    		  File file = new File(path);
    		  if(!file.exists())
    			  file.createNewFile();
    	        FileOutputStream fout = new FileOutputStream(path);  
    	  
    	        byte [] bytes = writestr.getBytes();   
    	  
    	        fout.write(bytes);   
    	  
    	        fout.close();   
    	      }   
    	  
    	        catch(Exception e){   
    	        e.printStackTrace();   
    	       }   
    	}   
    	  
    	/**读数据
    	 * 写文件  
    	 * @param path 文件路径
    	 * @return 文件内容
    	 * @throws IOException
    	 */
    	public static String readFile(String path) throws IOException{   
    	  String res="";   
    	  try{   
    	         BufferedReader reader;
    	         FileInputStream fis = new FileInputStream(path);
    	         
    	         BufferedInputStream in = new BufferedInputStream(fis);
    	         in.mark(4);
    	        byte[] first3bytes = new byte[3];
    	        in.read(first3bytes);
    	        in.reset();
    	        
    	        //UTF-8编码
    	        if(first3bytes[0] == (byte)0xEF 
    	        		&& first3bytes[1] == (byte)0xBB
    	        		&& first3bytes[2] == (byte)0xBF){
    	        		reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
    	        }
    	        //UTF-16be编码
    	        else if(first3bytes[0] == (byte)0xFE 
    	        		&& first3bytes[1] == (byte)0xFF){
    	        		reader = new BufferedReader(new InputStreamReader(in, "utf-16be"));
    	        }
    	        //UTF-16be编码
    	        else if(first3bytes[0] == (byte)0xFF 
    	        		&& first3bytes[1] == (byte)0xFF){
    	        		reader = new BufferedReader(new InputStreamReader(in, "utf-16le"));
    	        }
    	        //GBK编码
    	        else {
    	        		reader = new BufferedReader(new InputStreamReader(in, "GBK"));
    	        }
    	        
    	        String str = reader.readLine();
    	        while(str != null){
    	        		res += str + "\n";
    	        		str = reader.readLine();
    	        }
    	        reader.close();
    	     }   
    	     catch(Exception e){   
    	         e.printStackTrace();   
    	     }   
    	     return res;   
    	} 
    	
    	public static boolean picJudge(String name, Context context)
    	{
    		File cache = context.getCacheDir();
    		try {
    			File file = new File(cache.getAbsolutePath() + "/" + name.substring(name.lastIndexOf("/") + 1 ));
    			
    			if(file.exists())
    			{
    				return true;
    			}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return false;
    		}
    		return false;
    	}
    	
    	public static boolean picWrite(Bitmap img, String name, Context context)
    	{
    		if(img == null)
    			return false;
    		if(picJudge(name, context))
    			return false;
    		
    		File cache = context.getCacheDir();
    		
    		String filename = name.substring(name.lastIndexOf("/") + 1 );
    		if(!filename.endsWith(".png")){
    			filename = filename.substring(0, filename.lastIndexOf(".")) + ".png";
    		}

    		Log.d(TAG, "picWrite " + filename);
    		{
    			try {
    				File pic = new File(cache.getAbsolutePath() + "/" + filename);
    				cache.createNewFile();
    			    FileOutputStream picOut = new FileOutputStream(pic);
    		        BufferedOutputStream bos = new BufferedOutputStream(picOut);
    		        img.compress(Bitmap.CompressFormat.PNG, 100, bos);
    		        bos.flush();
    		        bos.close();
    		        pic.setLastModified(System.currentTimeMillis());
    		        
    		        int cacheSize = 0;
    		        File[] files = cache.listFiles();  
    	        	for(int i = 0; i < files.length; i++)
    	        	{
    	        		System.out.println(files[i].getName() + "的最后修改时间是：" + files[i].lastModified());
    	        		System.out.println(files[i].getName() + "的长度为：" + files[i].length());
    	        		cacheSize += files[i].length();
    	        	}
    	        	System.out.println("缓存总量达到: " + cacheSize / 1024 / 1024 + "M");
    	        	long time = files[0].lastModified();
    	        	if(cacheSize > MAX_LEN)
    	        	{
    	        		while(cacheSize > MAX_LEN * percent){
    			        	int deleteFile = 0;
    			        	files = cache.listFiles();
    		        		for(int i = 1; i < files.length; i++)
    			        	{
    		        			String fileName = files[i].getName();
    			        		if(fileName.endsWith(".png") && time > files[i].lastModified())
    			        		{
    			        			time = files[i].lastModified();
    			        			deleteFile = i;
    			        		}
    			        	}
    		        		System.out.println("删除了文件: " + files[deleteFile].getName());
    		        		System.out.println("文件删除了么？" + files[deleteFile].delete());
    	        		}
    	        	}
    		        return true;
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			}
    		}
    	}

    	public static boolean picWrite(Bitmap img, String name, Context context, int percent)
    	{
    		if(img == null)
    			return false;
    		if(picJudge(name, context))
    			return false;
    		
    		File cache = context.getCacheDir();
    		
    		String filename = name.substring(name.lastIndexOf("/") + 1 );
    		
    		try {
    				File pic = new File(cache.getAbsolutePath() + "/" + filename);
    				cache.createNewFile();
    			    FileOutputStream picOut = new FileOutputStream(pic);
    		        BufferedOutputStream bos = new BufferedOutputStream(picOut);
    		        img.compress(Bitmap.CompressFormat.JPEG, percent, bos);
    		        bos.flush();
    		        bos.close();
    		        
    		        int cacheSize = 0;
    		        File[] files = cache.listFiles();  
    	        	for(int i = 0; i < files.length; i++)
    	        	{
    	        		System.out.println(files[i].getName() + "的最后修改时间是：" + files[i].lastModified());
    	        		cacheSize += files[i].length();
    	        	}
    	        	long time = files[0].lastModified();
    	        	int deleteFile = 0;
    	        	if(cacheSize > MAX_LEN)
    	        	{
    	        		for(int i = 1; i < files.length; i++)
    		        	{
    		        		if(time > files[i].lastModified())
    		        		{
    		        			time = files[i].lastModified();
    		        			deleteFile = i;
    		        		}
    		        	}
    	        		System.out.println("删除了文件: " + files[deleteFile].getName());
    	        		files[deleteFile].delete();
    	        	}
    		        return true;
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			}
    	}
    	
    	public static Bitmap picRead(String name, Context context)
    	{
    		String filename = name.substring(name.lastIndexOf("/") + 1 );

    		if(!filename.endsWith(".png")){
    			filename = filename.substring(0, filename.lastIndexOf(".")) + ".png";
    		}
    		File cache = context.getCacheDir();

    		Log.d(TAG, "picRead " + filename);
    		if(true)
    		{
    			File file = new File(cache.getAbsolutePath() + "/" + filename);
    			if(file.exists())
    			{
    				System.out.println(filename);
    				file.setLastModified(System.currentTimeMillis());
    				
    				FileInputStream is;
    				try {
    					is = new FileInputStream(cache.getAbsolutePath() + "/" + filename);
    					Bitmap bitmap = BitmapFactory.decodeStream(is);
    					is.close();
    					return bitmap;
    				} catch (OutOfMemoryError e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					
    				}catch (FileNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				
    			}
    		}
    		return null;
    	}
    	
    	public static boolean picWriteSd(Bitmap img, String name, Context context)
    	{
    		boolean sdCardExist = Environment.getExternalStorageState()
    				.equals(android.os.Environment.MEDIA_MOUNTED); 
    		if(!sdCardExist){
    			return false;
    		}
    		if(img == null)
    			return false;
    		if(picJudgeSd(name, context))
    			return false;

    		File cache = context.getExternalCacheDir();

    		String filename = name.substring(name.lastIndexOf("/") + 1 );
    		if(!filename.endsWith(".png")){
    			filename = filename.substring(0, filename.lastIndexOf(".")) + ".png";
    		}

    		Log.d(TAG, "picWrite " + filename);
    		
    		try {
    				File pic = new File(cache.getAbsolutePath() + "/" + filename);
    				cache.createNewFile();
    				System.out.println(pic.getAbsolutePath());
    			    FileOutputStream picOut = new FileOutputStream(pic);
    		        BufferedOutputStream bos = new BufferedOutputStream(picOut);
    		        img.compress(Bitmap.CompressFormat.PNG, 100, bos);
    		        bos.flush();
    		        bos.close();
    		        
//    		        int cacheSize = 0;
//    		        File[] files = cache.listFiles();  
//    	        	for(int i = 0; i < files.length; i++)
//    	        	{
//    	        		cacheSize += files[i].length();
//    	        	}
//    	        	long time = files[0].lastModified();
//    	        	int deleteFile = 0;
//    	        	if(cacheSize > MAX_LEN)
//    	        	{
//    	        		for(int i = 1; i < files.length; i++)
//    		        	{
//    		        		if(time > files[i].lastModified())
//    		        		{
//    		        			time = files[i].lastModified();
//    		        			deleteFile = i;
//    		        		}
//    		        	}
//    	        		
//    	        		files[deleteFile].delete();
//    	        	}
    		        return true;
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			}
    	}
    	
    	public static Bitmap picReadSd(String name, Context context)
    	{

    		boolean sdCardExist = Environment.getExternalStorageState()
    				.equals(android.os.Environment.MEDIA_MOUNTED); 
    		if(!sdCardExist){
    			Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
    			return null;
    		}

    		String filename = name.substring(name.lastIndexOf("/") + 1 );
    		if(!filename.endsWith(".png")){
    			filename = filename.substring(0, filename.lastIndexOf(".")) + ".png";
    		}

    		Log.d(TAG, "picRead " + filename);
    		
    		File cache = context.getExternalCacheDir();
    		System.out.println("cache read : " + cache.getAbsolutePath());
    		
    		if(true)
    		{
    			File file = new File(cache.getAbsolutePath() + "/" + filename);
    			if(file.exists())
    			{
    				System.out.println(filename);
    				file.setLastModified(System.currentTimeMillis());
    				
    				FileInputStream is;
    				try {
    					is = new FileInputStream(cache.getAbsolutePath() + "/" + filename);
    					Bitmap bitmap = BitmapFactory.decodeStream(is);
    					is.close();
    					return bitmap;
    				} catch (FileNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				
    			}
    		}
    		return null;
    	}
    	
    	public static boolean picJudgeSd(String name, Context context)
    	{
    		File cache = context.getExternalCacheDir();
//    		System.out.println("cache judge : " + cache.getAbsolutePath());
    		boolean sdCardExist = Environment.getExternalStorageState()
    				.equals(android.os.Environment.MEDIA_MOUNTED); 
    		if(!sdCardExist)
    			return false;
    		File file;
    		try {
    			file = new File(cache.getAbsolutePath() + "/" + name);
//    			System.out.println(file.getAbsolutePath());
    			if(file.exists())
    			{
    				System.out.println("true");
    				return true;
    			}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return false;
    		}
    		
    		return false;
    	}
    	
    	public static boolean writeSerializable(Context context, String name, Serializable object){  
            FileOutputStream fos = null;  
            ObjectOutputStream oos = null;  
            try {  
                fos = context.openFileOutput(name, Context.MODE_PRIVATE);  
                oos = new ObjectOutputStream(fos);  
                oos.writeObject(object);  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (fos != null){  
                    try {  
                        fos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                        return false;
                    }  
                }  
                if (oos != null){  
                    try {  
                        oos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                        return false;
                    }  
                }  
            }
            return true;
      }  
    	
    	public static Serializable readSerializable(Context context, String name){
    		Log.d(TAG, "readSerializable");
    			FileInputStream fis = null;  
            ObjectInputStream ois = null;  
            File f = new File(context.getCacheDir() + name);
            long time = f.lastModified();
            long MS_DAY = 24 * 60 * 60 * 1000;
			if((time - System.currentTimeMillis()) / MS_DAY  > 0){
            	f.delete();
            	return null;
            }
            try {  
                fis = context.openFileInput(name);  
                ois = new ObjectInputStream(fis);  
                return (Serializable) ois.readObject();  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (fis != null){  
                    try {  
                        fis.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
                if (ois != null){  
                    try {  
                        ois.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            return null;  
    	}
}  
  
