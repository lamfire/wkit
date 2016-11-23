package com.lamfire.wkit;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ObjectUtils;
import com.lamfire.wkit.action.Action;

final class WKitRequestWrapper extends AbstractRequestWrapper {
	static final Logger LOGGER = Logger.getLogger(WKitRequestWrapper.class);
	private final Map<String, Object> actionAttributes = new HashMap<String, Object>();

	public WKitRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	private Action action;

	@Override
	public Object getAttribute(String name) {
		Object value = actionAttributes.get(name);
		if(value != null){
			return value;
		}

		if(name.contains(".")){
			return super.getAttribute(name);
		}
		
		try {
			value = ObjectUtils.getPropertyValue(action, name);
			actionAttributes.put(name, value);
			return value;
		} catch (Exception e) {
			return super.getAttribute(name);
		}
	}
	
	

	@Override
	public void setAttribute(String name, Object o) {
		actionAttributes.remove(name);
		super.setAttribute(name, o);
	}



	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isMultipartRequest() {
		String content_type = getContentType();
		if ((content_type != null) && (content_type.indexOf("multipart/form-data") != -1)) {
			return true;
		}
		return false;
	}

	public Principal getUserPrincipal(){
		HttpSession session =  getSession();
		if(session == null){
			return null;
		}
		return (Principal)session.getAttribute(ActionContext.USER_PRINCIPAL_IN_SESSION);
	}

	@Override
	public String getRemoteUser() {
		Principal p = getUserPrincipal();
		if(p == null){
			return null;
		}
		return p.getName();
	}
}
