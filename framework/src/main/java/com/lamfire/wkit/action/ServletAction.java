package com.lamfire.wkit.action;

import com.lamfire.wkit.ActionContext;


public abstract class ServletAction extends Action{

	protected void setMessage(String message) {
		ActionContext.getActionContext().getRequest().put("message", message);
	}
	
	protected void setErrorMessage(String message) {
		ActionContext.getActionContext().getRequest().put("errorMessage", message);
	}

	protected ActionResult redirect(String url) {
		return new ActionResult(url,true);
	}
	
	protected ActionResult forward(String url) {
		return new ActionResult(url,false);
	}
	
}
