package com.signinner.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.signinner.dao.User;
import com.signinner.service.UserService;


@ParentPackage("json-default")
@Namespace("/user")
public class UserAction extends ActionSupport {
	
	//自动注入
	@Autowired
	private UserService userService;
	
	//请求参数
	String userKey;
	String username;
	String password;
	int identity;
	int school;
	int major;
	int number;
	int year;
	String person_id;
	String name;
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public void setSchool(int school) {
		this.school = school;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	//返回json数据
	private Map<String, Object> msg;
	public Map<String, Object> getMsg() {
		return msg;
	}
	
	//注册
		@Action(value="qwe",results=@Result(name=SUCCESS,location="/index.jsp"))
		public String qwe(){
			msg=new HashMap<String, Object>();
			msg.put("qwe", "qwew");
			return SUCCESS;
		}
	
	//注册
	@Action(value="register",results=@Result(type="json"))
	public String register(){
		ActionContext a= ActionContext.getContext();
		if(username!=null&&password!=null&&(identity==1||identity==2)){
			try{
				int state=userService.register(username,password,identity);
				msg = new HashMap<String, Object>();
				if(state!=-1){
					msg.put("state", true);
					msg.put("userKey", state);
				}else
					msg.put("state", false);
				return SUCCESS;
			
			}catch(RuntimeException re){
				msg = new HashMap<String, Object>();
				msg.put("state", "出现错误");
				return SUCCESS;
			}
			
		}else{
			msg = new HashMap<String, Object>();
			msg.put("state", false);
			return SUCCESS;
			
		}
	}
	
	
	//个人信息
	@Action(value="info",results=@Result(type="json"))
	public String setInfo() throws UnsupportedEncodingException{
		int state=userService.setUserInfo(userKey,name);
		msg = new HashMap<String, Object>();
		if(state==1){
			msg.put("state", true);
		}else{
			msg.put("state", false);
		}
		return SUCCESS;
	}
	
	//录入人脸
	@Action(value="luru",results=@Result(type="json"))
	public String luru(){
		int state=userService.luru(userKey, person_id);
		msg = new HashMap<String, Object>();
		if(state==1){
			msg.put("state", true);
		}else{
			msg.put("state", false);
		}
		return SUCCESS;
	}
	
	
	
	//登录
		@Action(value="login",results=@Result(type="json"))
		public String login(){
			User user=userService.login(username, password);
			msg = new HashMap<String, Object>();
			if(user!=null){
				msg.put("state", true);
				msg.put("userKey", user.getId());
				msg.put("user", user);
			}else{
				msg.put("state", false);
			}
			return SUCCESS;
		}
	
	
}
