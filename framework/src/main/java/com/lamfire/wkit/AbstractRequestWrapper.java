package com.lamfire.wkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

abstract class AbstractRequestWrapper extends HttpServletRequestWrapper{

	public AbstractRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getRemoteAddr() {
		return getRealRemoteAddr();
	}

	private static String findAddress(String forwardFor){
		if(forwardFor == null || forwardFor.length() == 0){
			return null;
		}
		String [] addresses = forwardFor.split(",");

		for(String addr : addresses){
			if(!"unknown".equalsIgnoreCase(addr)){
				return addr;
			}
		}
		
		return null;
	}
	
	public String getRealRemoteAddr() {
		String addr;
		String forwardedFor =  super.getHeader("X-Forwarded-For");
		if((addr = findAddress(forwardedFor)) != null){
			return addr.trim();
		}
		
		forwardedFor = super.getHeader("Proxy-Client-IP");;
		if((addr = findAddress(forwardedFor)) != null){
			return addr.trim();
		}
		
		forwardedFor = super.getHeader("WL-Proxy-Client-IP");
		if((addr = findAddress(forwardedFor)) != null){
			return addr.trim();
		}
		
		return super.getRemoteAddr();
	}
}
