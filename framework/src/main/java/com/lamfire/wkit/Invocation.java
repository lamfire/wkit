package com.lamfire.wkit;


public abstract interface Invocation {

	public abstract void accept(InvocationVisitor visitor)throws Exception;

}
