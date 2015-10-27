package com.lamfire.wkit.action;

public class ActionForward{
	private String urlPath;
	private boolean redirect = false;
	
	public ActionForward(){}
	
	public ActionForward(String url){
		this.urlPath = url;
	}
	
	public ActionForward(String url,boolean redirect){
		this.urlPath = url;
		this.redirect = redirect;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
}
