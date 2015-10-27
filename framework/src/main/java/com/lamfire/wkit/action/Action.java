package com.lamfire.wkit.action;

import java.util.Map;

import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.Invocation;
import com.lamfire.wkit.InvocationVisitor;


public abstract class Action implements Invocation{
	public void destroy() {

	}
	
	public void accept(InvocationVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	public void init() {

	}
	
	protected Map<String, Object> getSession(){
		return ActionContext.getActionContext().getSession();
	}
	
	protected ActionContext getActionContext(){
		return ActionContext.getActionContext();
	}
}
