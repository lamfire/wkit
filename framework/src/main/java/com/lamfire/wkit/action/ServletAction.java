package com.lamfire.wkit.action;

import com.lamfire.wkit.ActionContext;


public abstract class ServletAction extends Action{
	
	public abstract ActionForward execute( );

	protected void setMessage(String message) {
		ActionContext.getActionContext().getRequest().put("message", message);
	}
	
	protected void setErrorMessage(String message) {
		ActionContext.getActionContext().getRequest().put("errorMessage", message);
	}

	protected ActionForward redirect(String url) {
		return new ActionForward(url,true);
	}
	
	protected ActionForward forward(String url) {
		return new ActionForward(url,false);
	}
	
}
