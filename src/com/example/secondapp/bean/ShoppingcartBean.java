package com.example.secondapp.bean;

import java.io.Serializable;

public class ShoppingcartBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String fruitimage;
	public String fruitname;
	public String fruitprice;
	public int fruitcount;
	public int shopid;
	public int cartid;
	public int fruitid;
	public String dateline;
	
	public ShoppingcartBean(){
		super();
	}
	
	public ShoppingcartBean(String fruitname){
		this.fruitname = fruitname;
	}
	
	public ShoppingcartBean(String fruitimage, String fruitname, String fruitprice, int fruitcount, int cartid ){
		this.fruitimage = fruitimage;
		this.fruitname = fruitname;
		this.fruitprice = fruitprice;
		this.fruitcount = fruitcount;
		this.cartid = cartid;
	}
}
