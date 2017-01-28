package com.signinner.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Unsigned entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "unsigned", catalog = "signinner")
public class Unsigned implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer signId;
	private Integer userId;

	// Constructors

	/** default constructor */
	public Unsigned() {
	}

	/** minimal constructor */
	public Unsigned(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Unsigned(Integer id, Integer signId, Integer userId) {
		this.id = id;
		this.signId = signId;
		this.userId = userId;
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

	@Column(name = "sign_id")
	public Integer getSignId() {
		return this.signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}