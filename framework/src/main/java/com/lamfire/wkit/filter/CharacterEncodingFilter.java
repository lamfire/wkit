package com.lamfire.wkit.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lamfire.logger.Logger;

public final class CharacterEncodingFilter implements Filter {

	static final Logger logger = Logger.getLogger(CharacterEncodingFilter.class);
	
	private static String CHARSET = "utf-8";
	private FilterConfig filterConfig;
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			request.setCharacterEncoding(CHARSET);
			response.setCharacterEncoding(CHARSET);
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		
		try{
			chain.doFilter(request, response);
		}catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String charset = this.filterConfig.getInitParameter("charset");
		if(charset != null && charset.length() > 0){
			CHARSET=charset; 
		}
	}

	public ServletContext getServletContext() {
		return this.filterConfig.getServletContext();
	}
	
}
