package com.signinner.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Major entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "major", catalog = "signinner")
public class Major implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer school;

	// Constructors

	/** default constructor */
	public Major() {
	}

	/** minimal constructor */
	public Major(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Major(Integer id, String name, Integer school) {
		this.id = id;
		this.name = name;
		this.school = school;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "school")
	public Integer getSchool() {
		return this.school;
	}

	public void setSchool(Integer school) {
		this.school = school;
	}

}