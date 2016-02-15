package com.example.secondapp.bean;

import java.io.Serializable;

public class OrderId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int orderid;
	
	public OrderId(){
		
	}
	public OrderId(int orderid){
		OrderId.orderid = orderid;
	}
}
