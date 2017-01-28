package com.signinner.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "signinner", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private Integer school;
	private Integer major;
	private Integer number;
	private Integer year;
	private Integer vertification;
	private String personId;
	private Integer identity;
	private String name;
	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username) {
		this.username = username;
	}

	/** full constructor */
	public User(String username, String password, Integer school,
			Integer major, Integer number, Integer year, Integer vertification,
			String personId, Integer identity,String name) {
		this.username = username;
		this.password = password;
		this.school = school;
		this.major = major;
		this.number = number;
		this.year = year;
		this.vertification = vertification;
		this.personId = personId;
		this.identity = identity;
		this.setName(name);
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

	@Column(name = "username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "year")
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "vertification")
	public Integer getVertification() {
		return this.vertification;
	}

	public void setVertification(Integer vertification) {
		this.vertification = vertification;
	}

	@Column(name = "person_id", length = 50)
	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "identity")
	public Integer getIdentity() {
		return this.identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}