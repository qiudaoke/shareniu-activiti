package com.shareniu.chapter16.variabletype;

import java.io.Serializable;

public class Shareniu implements Serializable {
	private String id;
	private String name;
	private int age;
	public Shareniu() {
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Shareniu [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	public Shareniu(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	
	
}
