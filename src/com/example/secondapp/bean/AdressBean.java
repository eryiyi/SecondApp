package com.example.secondapp.bean;

import java.io.Serializable;

public class AdressBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String adress;
	public String phonenumber;
	public int id;
	public int state;
	
	public AdressBean(){
		super();
	}

	public AdressBean(String name, String adress, String phonenumber){
		this.name = name;
		this.adress = adress;
		this.phonenumber = phonenumber;
	}
}
