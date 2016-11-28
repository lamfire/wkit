package com.lamfire.wkit.action;

import java.util.HashMap;
import java.util.Map;

public class ActionForward {
	private String urlPath;
	private boolean redirect = false;
	private final Map<String,Object> attrs = new HashMap<String, Object>();
	
	public ActionForward(){}
	
	public ActionForward(String url){
		this.urlPath = url;
	}
	
	public ActionForward(String url, boolean redirect){
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

	public void setAttr(String key,Object value){
		attrs.put(key,value);
	}

	public void clearAttrs(){
		attrs.clear();
	}

	public Object removeAttr(String key){
		return attrs.remove(key);
	}

	public Map<String, Object> getAttrs() {
		return attrs;
	}
}
