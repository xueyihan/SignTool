package com.signinner.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sign", catalog = "signinner")
public class Sign implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer activityId;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer signedNum;
	private String signedJson;
	private String wifiJson;

	// Constructors

	/** default constructor */
	public Sign() {
	}

	/** full constructor */
	public Sign(Integer activityId, Timestamp startTime, Timestamp endTime,
			Integer signedNum, String signedJson, String wifiJson) {
		this.activityId = activityId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.signedNum = signedNum;
		this.signedJson = signedJson;
		this.wifiJson = wifiJson;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "activity_id")
	public Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "signed_num")
	public Integer getSignedNum() {
		return this.signedNum;
	}

	public void setSignedNum(Integer signedNum) {
		this.signedNum = signedNum;
	}

	@Column(name = "signed_json", length = 65535)
	public String getSignedJson() {
		return this.signedJson;
	}

	public void setSignedJson(String signedJson) {
		this.signedJson = signedJson;
	}

	@Column(name = "wifi_json", length = 65535)
	public String getWifiJson() {
		return this.wifiJson;
	}

	public void setWifiJson(String wifiJson) {
		this.wifiJson = wifiJson;
	}

}