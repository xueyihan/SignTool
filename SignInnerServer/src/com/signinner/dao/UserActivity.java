package com.signinner.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserActivity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_activity", catalog = "signinner")
public class UserActivity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer activityId;
	private Integer userId;
	private Integer state;
	private Integer number;

	// Constructors

	/** default constructor */
	public UserActivity() {
	}

	/** minimal constructor */
	public UserActivity(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public UserActivity(Integer id, Integer activityId, Integer userId,
			Integer state, Integer number) {
		this.id = id;
		this.activityId = activityId;
		this.userId = userId;
		this.state = state;
		this.number = number;
	}

	public UserActivity(String userKey, String id) {
		// TODO Auto-generated constructor stub
		this.activityId=Integer.valueOf(id);
		this.userId=Integer.valueOf(userKey);
		this.state=0;
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

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}