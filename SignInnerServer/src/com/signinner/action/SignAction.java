package com.signinner.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.signinner.dao.User;
import com.signinner.entity.ActivityEntity;
import com.signinner.service.ActivityService;
import com.signinner.service.SignService;
import com.signinner.service.UserService;


@ParentPackage("json-default")
@Namespace("/sign")
public class SignAction extends ActionSupport {
	
	//自动注入
		@Autowired
		SignService signService;
		
	//请求参数
		String userKey;
		String wifiJson;
		String signId;
		String activityId;
		String startTime;
		String endTime;
		/*public void setUserKey(String userKey) {
			this.userKey = userKey;
		}
		public void setWifiJson(String wifiJson) {
			this.wifiJson = wifiJson;
		}
		public void setSignId(String signId) {
			this.signId = signId;
		}
		public void setActivityId(String activityId) {
			this.activityId = activityId;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}*/
	
	//返回参数
		private Map<String, Object> msg;
		/*public Map<String, Object> getMsg() {
			return msg;
		}*/
	
	
		@Action(value="sign",results=@Result(type="json"))
		public String sign(){
			System.out.println("进入sign:");
			System.out.println("userKey:"+userKey);
			System.out.println("wifiJson:"+wifiJson);
			System.out.println("signId:"+signId);
			boolean sign=signService.sign(userKey, wifiJson, signId);
			msg = new HashMap<String, Object>();
			if(sign){
				msg.put("signResult", true);
			}else{
				msg.put("signResult", false);
			}
			System.out.println("成功返回："+msg);
			return SUCCESS;
		}

		
		@Action(value="faqi",results=@Result(type="json"))
		public String faqi(){
			System.out.println("进入faqi:");
			System.out.println("userKey:"+userKey);
			System.out.println("activityId:"+activityId);
			System.out.println("wifiJson:"+wifiJson);
			System.out.println("startTime:"+startTime);
			System.out.println("endTime:"+endTime);
			boolean faqi=signService.faqi(userKey,activityId, startTime,endTime,wifiJson);
			msg = new HashMap<String, Object>();
			if(faqi){
				msg.put("state", true);
			}else{
				msg.put("state", false);
			}
			System.out.println("成功返回："+msg);
			return SUCCESS;
		}
		
		

	
}
