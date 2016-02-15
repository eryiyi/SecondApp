package com.example.secondapp.bean;

import java.io.Serializable;

public class ImgTextBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String imgid;
	
	public ImgTextBean(){
		super();
	}
	
	public ImgTextBean(String imgid){
		this.imgid = imgid;
	}
}
