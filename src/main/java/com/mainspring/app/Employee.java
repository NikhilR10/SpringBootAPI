package com.mainspring.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String mail;
	private long mobile;
	private int age;
	
	public Employee() {
		
	}

	public Employee(int id, String name, String mail, long mobile, int age) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.mobile = mobile;
		this.age = age;
	}
	
	public Employee(String name, String mail, long mobile, int age) {
		this.name = name;
		this.mail = mail;
		this.mobile = mobile;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
