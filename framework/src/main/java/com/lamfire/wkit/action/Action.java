package com.lamfire.wkit.action;

import java.util.Map;

import com.lamfire.wkit.ActionContext;


public abstract class Action{
	protected Map<String, Object> getSession(){
		return ActionContext.getActionContext().getSession();
	}
	
	protected ActionContext getActionContext(){
		return ActionContext.getActionContext();
	}
}
