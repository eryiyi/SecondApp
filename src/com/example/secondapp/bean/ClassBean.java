package com.example.secondapp.bean;

import java.io.Serializable;

public class ClassBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int typeid;
	public String typelogo;
	public String typename;
	public String example;
	
	public ClassBean(){
		super();
	}
	
	public ClassBean(int typeid, String typelogo, String typename, String example){
		this.typeid = typeid;
		this.typelogo = typelogo;
		this.typename = typename;
		this.example = example;
	}
}
