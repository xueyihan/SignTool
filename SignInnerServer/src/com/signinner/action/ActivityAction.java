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
import com.signinner.service.UserService;


@ParentPackage("json-default")
@Namespace("/activity")
public class ActivityAction extends ActionSupport {
	
	//�Զ�ע��
		@Autowired
		ActivityService activityService;
	//�������
		String userKey;
		String name;
		String time;
		String position;
		String school;
		String major;
		String publisherName;
		String idArray;
		public void setIdArray(String idArray) {
			this.idArray = idArray;
		}
		public void setUserKey(String userKey) {
			this.userKey = userKey;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public void setSchool(String school) {
			this.school = school;
		}
		public void setMajor(String major) {
			this.major = major;
		}
		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}
		
	//���ز���
		private Map<String, Object> msg;
		public Map<String, Object> getMsg() {
			return msg;
		}
	
	//����
		@Action(value="getCanyuActivity",results=@Result(type="json"))
		public String CanyuActivity(){
			System.out.println("����getCanyuActivity:");
			System.out.println("userKey:"+userKey);
			List<ActivityEntity> l=activityService.getCanyuActivity(userKey);
			msg = new HashMap<String, Object>();
			msg.put("data", l);
			System.out.println("�ɹ����أ�"+msg);
			return SUCCESS;
		}

	
		
		
		@Action(value="getFaqiActivity",results=@Result(type="json"))
		public String FaqiActivity(){
			System.out.println("����getFaqiActivity:");
			System.out.println("userKey:"+userKey);
			List<ActivityEntity> l=activityService.getFaqiActivity(userKey);
			msg = new HashMap<String, Object>();
			msg.put("data", l);
			System.out.println("�ɹ����أ�"+msg);
			return SUCCESS;
		}

		
		@Action(value="publish",results=@Result(type="json"))
		public String publish(){
			System.out.println("����publish:");
			System.out.println("userKey:"+userKey);
			boolean publish=activityService.addFaqiActivity(userKey,publisherName,name,time,position,school,major);
			msg=new HashMap<String, Object>();
			msg.put("state", publish);
			return SUCCESS;
		}

		@Action(value="getAll",results=@Result(type="json"))
		public String all(){
			System.out.println("����getAll:");
			System.out.println("userKey:"+userKey);
			List<ActivityEntity> data=activityService.getAll();
			msg=new HashMap<String, Object>();
			msg.put("data", data);
			return SUCCESS;
		}
		
		@Action(value="addCanyuActivity",results=@Result(type="json"))
		public String addCanyuActivity(){
			System.out.println("����addCanyuActivity:");
			System.out.println("userKey:"+userKey);
			System.out.println("idArray:"+idArray);
			boolean state=activityService.addCanyuActivity(userKey,idArray);
			msg=new HashMap<String, Object>();
			msg.put("state", state);
			return SUCCESS;
		}
	
}
