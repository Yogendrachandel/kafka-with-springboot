package com.learn.model;

public class User {
	
	private String name;
	private String dep;
	
	
	public User() {
	}
	
	
	public User(String name, String dep) {
		super();
		this.name = name;
		this.dep = dep;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}


	@Override
	public String toString() {
		return "User [name=" + name + ", dep=" + dep + "]";
	}
	
	

}
