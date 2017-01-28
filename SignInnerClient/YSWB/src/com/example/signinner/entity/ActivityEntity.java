package com.example.signinner.entity;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;



public class ActivityEntity {

	String id; //活动编号
	String name; //活动名称
	String time;  //活动时间
	String position;//活动地点
	String publisher_id; //发布者id
	String publisher_name; //发布者名称
	String schoolId; //所属学校id
	String majorId;//所属专业id
	int qiandaoStatus; //1已签到  //2未签到
	Timestamp startTime; //签到的开始时间
	Timestamp endTime; //签到的结束时间
	String signedJson; //已签到人员编号序列
	String signId; //签到编号
	public ActivityEntity(String optString,String userKey) {
		// TODO Auto-generated constructor stub
		try {
			JSONObject object = new JSONObject(optString);
			id = object.getString("id");
			name = object.getString("name");
			time = object.getString("time");
			position = object.getString("position");
			publisher_id = object.getString("publisher_id");
			publisher_name = object.getString("publisher_name");
			schoolId = object.getString("school");
			majorId = object.getString("major");
			startTime =Timestamp.valueOf(object.getString("startTime").replace('T', ' '));
			endTime = Timestamp.valueOf(object.getString("endTime").replace('T', ' '));
			System.out.println(startTime);
			signedJson=object.getString("signed_json");
			
			//判断已签到编号中是否有该用户，若有则将签到状态置1，即已签到
			if(signedJson.indexOf("["+userKey+"]")==-1){
				//未签到
				this.qiandaoStatus=2;
			}else{
				//已签到
				this.qiandaoStatus=1;
			}
			signId=object.getString("signId");
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public int getQiandaoStatus() {
		return qiandaoStatus;
	}

	public void setQiandaoStatus(int qiandaoStatus) {
		this.qiandaoStatus = qiandaoStatus;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getSignedJson() {
		return signedJson;
	}

	public void setSignedJson(String signedJson) {
		this.signedJson = signedJson;
	}

}
