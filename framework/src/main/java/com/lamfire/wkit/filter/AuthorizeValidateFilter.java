package com.lamfire.wkit.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
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
    private static final String AUTH_SESSION_KEY = "auth.session.key";
    private static final String AUTH_REDIRECT_URL = "auth.redirect.url";
    private static final String AUTH_EXCLUDE_PATHS = "auth.exclude.paths";
    private static final String AUTH_EXCLUDE_REGEX = "auth.exclude.regex";

    private final Set<String> excludePaths = new HashSet<String>();
    private String excludeRegex;
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

    boolean isExcludeServletPath(String servletPath){
        for(String path : excludePaths){
            if(StringUtils.isStartWith(servletPath,path)){
                return true;
            }
        }

        if(StringUtils.isBlank(excludeRegex)){
            return false;
        }

        Pattern pattern = Pattern.compile(excludeRegex);
        Matcher matcher = pattern.matcher(servletPath);
        return matcher.find();
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

        //ignore redirect url
        String servletPath = req.getServletPath();
        if(StringUtils.equalsIgnoreCase(servletPath,redirectUrl)){
            chain.doFilter(request, response);
            return;
        }

        //ignore exclude paths
        if(isExcludeServletPath(servletPath)){
            chain.doFilter(request, response);
            return;
        }


        //auth session
		if(req.getSession().getAttribute(sessionKey) != null){
			chain.doFilter(request, response);
			return;
		}

        //false
		if(isExternalUrl(redirectUrl)){
			res.sendRedirect(redirectUrl);
			return;
		}
		
		res.sendRedirect(req.getContextPath()+redirectUrl);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.sessionKey = filterConfig.getInitParameter(AUTH_SESSION_KEY);
		LOGGER.info("auth session key : " + this.sessionKey);
		
		this.redirectUrl = filterConfig.getInitParameter(AUTH_REDIRECT_URL);
		LOGGER.info("auth redirect url : " + this.redirectUrl);

        String excludes =  filterConfig.getInitParameter(AUTH_EXCLUDE_PATHS);
        LOGGER.info("auth exclude paths : " + excludes);
        if(StringUtils.isNotBlank(excludes)){
            String [] paths  = StringUtils.split(excludes,',');
            for(String path : paths){
                this.excludePaths.add(path);
            }
        }

        this.excludeRegex =  filterConfig.getInitParameter(AUTH_EXCLUDE_REGEX);
        LOGGER.info("auth exclude regex : " + excludeRegex);
	}
}
