package com.lamfire.wkit;

public class ActionException extends RuntimeException {

	private static final long serialVersionUID = -4528122654331517612L;

	public ActionException(){}
	
	public ActionException(String m){
		super(m);
	}
	
	public ActionException(Exception e){
		super(e);
	}
}
