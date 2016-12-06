package com.lamfire.wkit;

public class MappingException extends Exception {

	private static final long serialVersionUID = -452312265434517612L;

	public MappingException(){}

	public MappingException(String m){
		super(m);
	}

	public MappingException(Exception e){
		super(e);
	}
}
