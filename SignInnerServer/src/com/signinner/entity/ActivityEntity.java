package com.signinner.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;


public class ActivityEntity{
	
	public ActivityEntity(Integer id, String name, String position,String time,
			 Object startTime, Object endTime) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.position = position;
		this.startTime = (Timestamp) startTime;
		this.endTime = (Timestamp) endTime;
	}

	public ActivityEntity() {
	}
	
	public ActivityEntity(Integer id) {
		this.id=id;
	}
	public ActivityEntity(Integer id, String name, String time,
			String position, int publisher_id, String publisher_name,
			int school, int major, int qiandaoStatus, Timestamp startTime,
			Timestamp endTime) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.position = position;
		this.publisher_id = publisher_id;
		this.publisher_name = publisher_name;
		this.school = school;
		this.major = major;
		this.qiandaoStatus = qiandaoStatus;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public ActivityEntity(Integer id, String name, String position,String time,Object startTime,
			Object endTime,String signedJson,Integer signId) {
		super();
		this.id = id;
		this.name = name;
		this.position=position;
		this.time=time;
		this.startTime = (Timestamp) startTime;
		this.endTime = (Timestamp) endTime;
		this.signed_json=signedJson;
		this.signId=signId;
	}
	
	
	
	Integer id; //����
	String name; //�����
	String time;  //�ʱ��
	String position;//��ص�
	int publisher_id; //������id
	String publisher_name; //����������
	int school; //����ѧУid
	int major;//����רҵid
	int qiandaoStatus; //0δ��ʼ  //1��ǩ��  //2δǩ��
	Timestamp startTime; //ǩ���Ŀ�ʼʱ��
	Timestamp endTime; //ǩ���Ľ���ʱ��
	String signed_json; //��ǩ����Ա�ı��
	Integer signId; //ǩ�����
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public int getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}
	public String getPublisher_name() {
		return publisher_name;
	}
	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}
	public int getSchool() {
		return school;
	}
	public void setSchool(int school) {
		this.school = school;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
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

	public String getSigned_json() {
		return signed_json;
	}

	public void setSigned_json(String signed_json) {
		this.signed_json = signed_json;
	}

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}
	
	
}
