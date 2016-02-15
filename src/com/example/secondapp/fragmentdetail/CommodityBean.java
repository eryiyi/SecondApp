package com.example.secondapp.fragmentdetail;

import java.io.Serializable;

public class CommodityBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int comimage;
	public String comname;
	public String comprice;
	public String comsupprice;
	public int addshopcart;
	
	public CommodityBean(){
		super();
	}
	
	public CommodityBean(String comname){
		this.comname = comname;
	}
	
	public CommodityBean(int comimage, String comname, String comprice, String comsupprice, int addshopcart){
		this.comimage = comimage;
		this.comname = comname;
		this.comprice = comprice;
		this.comsupprice = comsupprice;
		this.addshopcart = addshopcart;
	}
}
