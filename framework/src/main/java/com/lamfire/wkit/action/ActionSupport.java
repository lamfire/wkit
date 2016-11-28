package com.lamfire.wkit.action;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.ActionContext;

import java.io.IOException;
import java.io.OutputStream;


public abstract class ActionSupport extends Action{
	final static Logger LOGGER = Logger.getLogger(ActionSupport.class);

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


	protected void write(OutputStream out , byte[] bytes){
		try {
			out.write(bytes);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	protected void write(OutputStream out , String data){
		try {
			out.write(data.getBytes());
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	protected void write(OutputStream out , String data ,String encoding){
		try {
			out.write(data.getBytes(encoding));
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	
}
