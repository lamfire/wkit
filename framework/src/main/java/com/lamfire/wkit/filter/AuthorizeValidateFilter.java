package com.lamfire.wkit.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lamfire.logger.Logger;

public class AuthorizeValidateFilter implements Filter{
	static final Logger LOGGER = Logger.getLogger(AuthorizeValidateFilter.class);
	private String key;
	private String url;
	
	public void destroy() {
		
	}
	
	static boolean isExternalUrl(String path){
		String url = path.toLowerCase();
		Pattern pattern = Pattern.compile("^(http://|https://){1}[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		if(req.getSession().getAttribute(key) != null){
			chain.doFilter(request, response);
			return;
		}
		
		if(isExternalUrl(url)){
			res.sendRedirect(url);
			return;
		}
		
		res.sendRedirect(req.getContextPath()+url);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.key = filterConfig.getInitParameter("auth.key");
		LOGGER.info("set 'auth.key':" + this.key);
		
		this.url = filterConfig.getInitParameter("auth.url");
		LOGGER.info("set 'auth.url':" + this.url);
	}
}
