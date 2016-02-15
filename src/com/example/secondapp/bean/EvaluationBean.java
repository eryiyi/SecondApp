package com.example.secondapp.bean;

import java.io.Serializable;

public class EvaluationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public String date;
	public String evaluation;
	public int grade;
	
	public EvaluationBean(){
		super();
	}

	public EvaluationBean(String username, String date, String evaluation, int grade){
		this.username = username;
		this.date = date;
		this.evaluation = evaluation;
		this.grade = grade;
	}
}
