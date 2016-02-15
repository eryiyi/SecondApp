package com.example.secondapp.bean;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public User(int uid, String username, String mobile){
		this.uid = uid;
		this.username = username;
		this.mobile = mobile;
	}
	public int uid;
	public String username;
	public String mobile;
	
	public User(){
		super();
	}
}
