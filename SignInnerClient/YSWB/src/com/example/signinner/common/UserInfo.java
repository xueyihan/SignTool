package com.example.signinner.common;

import com.example.signinner.module.login.LoginActivity;
import com.example.signinner.module.login.LuruActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class UserInfo {
	
	static public String getUserKey(Context ctx){
		SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
		String userKey=userInfoSharedPreferences.getString("userKey", null);
		return userKey;
	}
	
	static public void setPersonId(String personId,Context ctx){
		String userKey=getUserKey(ctx);
		if(userKey!="0"){
			SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
			Editor e=userInfoSharedPreferences.edit();
			e.putString("personId", personId);
			e.commit();
		}
	}
	static public String getPersonId(Context ctx){
		SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
		String personId=userInfoSharedPreferences.getString("personId", null);
		return personId;
	}
	
	
	static public void setUserInfo(String userKey,String school,String major,String number,String year,String vertification,String person_id,String identity,String name,Context ctx){
		SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
		Editor e=userInfoSharedPreferences.edit();
		e.putString("userKey", userKey);
		e.putString("school", school);
		e.putString("major", major);
		e.putString("number", number);
		e.putString("year", year);
		e.putString("vertification", vertification);
		e.putString("person_id", person_id);
		e.putString("identity", identity);
		e.putString("name", name);
		e.commit();
	}
	
	static public void setInfo(String school,String major,String number,String year,String vertification,String name,Context ctx){
		SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
		Editor e=userInfoSharedPreferences.edit();
		e.putString("school", school);
		e.putString("major", major);
		e.putString("number", number);
		e.putString("year", year);
		e.putString("vertification", vertification);
		e.putString("name", name);
		e.commit();
	}
	
	
	static public String getName(Context ctx){
		SharedPreferences userInfoSharedPreferences=ctx.getApplicationContext().getSharedPreferences("signinner", 0);
		String name=userInfoSharedPreferences.getString("name", null);
		return name;
	}
	
}