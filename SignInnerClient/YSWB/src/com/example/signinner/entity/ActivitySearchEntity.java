package com.example.signinner.entity;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;



public class ActivitySearchEntity {

	String id; //活动编号
	String name; //活动名称
	String time;  //活动时间
	String position;//活动地点
	String publisher_id; //发布者id
	String publisher_name; //发布者名称
	String schoolId; //所属学校id
	String majorId;//所属专业id
	public ActivitySearchEntity(String optString,String userKey) {
		// TODO Auto-generated constructor stub
		try {
			JSONObject object = new JSONObject(optString);
			id = object.getString("id");
			name = object.getString("name");
			time = object.getString("time");
			position = object.getString("position");
			publisher_id = object.getString("publisherId");
			publisher_name = object.getString("publisherName");
			schoolId = object.getString("school");
			majorId = object.getString("major");
				
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
}