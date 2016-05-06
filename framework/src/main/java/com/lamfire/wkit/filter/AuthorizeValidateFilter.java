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
import com.lamfire.utils.StringUtils;

public class AuthorizeValidateFilter implements Filter{
	static final Logger LOGGER = Logger.getLogger(AuthorizeValidateFilter.class);
	private String sessionKey;
	private String redirectUrl;
	
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

        String uri = req.getRequestURI();
        if(StringUtils.contains(uri,redirectUrl)){
            chain.doFilter(request, response);
            return;
        }

		if(req.getSession().getAttribute(sessionKey) != null){
			chain.doFilter(request, response);
			return;
		}
		
		if(isExternalUrl(redirectUrl)){
			res.sendRedirect(redirectUrl);
			return;
		}
		
		res.sendRedirect(req.getContextPath()+redirectUrl);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.sessionKey = filterConfig.getInitParameter("auth.session.key");
		LOGGER.info("auth session key : " + this.sessionKey);
		
		this.redirectUrl = filterConfig.getInitParameter("auth.redirect.url");
		LOGGER.info("auth redirect url : " + this.redirectUrl);
	}
}
