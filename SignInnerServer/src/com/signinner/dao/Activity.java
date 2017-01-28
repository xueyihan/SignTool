package com.signinner.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Activity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "activity", catalog = "signinner")
public class Activity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer publisherId;
	private String publisherName;
	private String name;
	private String time;
	private String position;
	private Integer school;
	private Integer major;
	private Integer signId;

	// Constructors

	/** default constructor */
	public Activity() {
	}

	/** minimal constructor */
	public Activity(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Activity(Integer id, Integer publisherId, String publisherName,
			String name, String time, String position, Integer school,
			Integer major, Integer signId) {
		this.id = id;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.name = name;
		this.time = time;
		this.position = position;
		this.school = school;
		this.major = major;
		this.signId = signId;
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

	@Column(name = "publisher_id")
	public Integer getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	@Column(name = "publisher_name", length = 50)
	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "time", length = 50)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "position", length = 50)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "school")
	public Integer getSchool() {
		return this.school;
	}

	public void setSchool(Integer school) {
		this.school = school;
	}

	@Column(name = "major")
	public Integer getMajor() {
		return this.major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	@Column(name = "sign_id")
	public Integer getSignId() {
		return this.signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

}