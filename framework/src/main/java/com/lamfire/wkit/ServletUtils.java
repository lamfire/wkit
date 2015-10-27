package com.lamfire.wkit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;

/**
 * 请求信息工具 
 * 2010-10-15上午11:23:51 
 * @author lamfire
 */
@SuppressWarnings("unchecked")
public final class ServletUtils {

	static final Logger logger = Logger.getLogger(ServletUtils.class);

	/**
	 * 打印全部信息
	 * 
	 * @param request
	 */
	public static void printAll(HttpServletRequest request) {
		printHeaders(request);
		printCookies(request);
		printParameters(request);
		printSession(request);
	}

	/**
	 * 打印Session中的信息
	 * 
	 * @param request
	 */
	public static void printSession(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		HttpSession session = request.getSession();
		if (session != null) {
			buffer.append("\n\nSession{");
			buffer.append(String.format("\n SESSION_ID : %s", session.getId()));
			Enumeration<String> sessNames = session.getAttributeNames();
			while (sessNames.hasMoreElements()) {
				String name = sessNames.nextElement();
				Object val = session.getAttribute(name);
				buffer.append(String.format("\n %16s : %s", name, val));
			}
			buffer.append("\n}");
		}

		logger.info(buffer.toString());
	}

	/**
	 * 打印请求参数
	 * 
	 * @param request
	 */
	public static void printParameters(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\nRequest Parameters{");
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			String[] val = request.getParameterValues(name);
			buffer.append(String.format("\n %16s : %s", name, StringUtils.join(val, ",")));
		}
		buffer.append("\n}");
		logger.info(buffer.toString());
	}

	/**
	 * 打印请求头信息
	 * 
	 * @param request
	 */
	public static void printHeaders(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nRequest Headers{");
		while (headers.hasMoreElements()) {
			String name = headers.nextElement();
			String val = request.getHeader(name);
			buffer.append(String.format("\n %16s : %s", name, val));
		}
		buffer.append("\n}");

		logger.info(buffer.toString());
	}

	public static void printCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			return;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nRequest Cookies{");
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String val = cookie.getValue();
			buffer.append(String.format("\n %16s : %s", name, val));
		}
		buffer.append("\n}");

		logger.info(buffer.toString());
	}

	/**
	 * 
	 * @param request
	 * @param servletContext
	 * @return
	 * @throws java.io.IOException
	 */
	public static HttpServletRequest wrapHttpServletRequest(HttpServletRequest request, ServletContext servletContext,String tempDir,long maxSizeLimit) throws IOException {
		if (isMultiPart(request)) {
			MultiPartRequestImpl multi = new MultiPartRequestImpl();
			multi.setMaxSize(maxSizeLimit);
			request = new MultiPartRequestWrapper(multi, request, tempDir);
		}
		return new WKitRequestWrapper(request);
	}

	/**
	 * 验证请求是否为MultiPart
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMultiPart(HttpServletRequest request) {
		String content_type = request.getContentType();
		return (content_type != null) && (content_type.indexOf("multipart/form-data") != -1);
	}

	/**
	 * 得到ServletPath
	 * @param request
	 * @return
	 */
	public static String getServletPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();

		if ((null != servletPath) && (!"".equals(servletPath))) {
			return servletPath;
		}

		String requestUri = request.getRequestURI();
		int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
		int endIndex = request.getPathInfo() == null ? requestUri.length() : requestUri.lastIndexOf(request.getPathInfo());

		if (startIndex > endIndex) {
			endIndex = startIndex;
		}

		return requestUri.substring(startIndex, endIndex);
	}

	public static String getApplicationPath(ServletContext context){
		String path = context.getRealPath("/");
		return path;
	}
	
	public static String getApplicationClassPath(ServletContext context){
		String path = context.getRealPath("/WEB-INF/classes/");
		return path;
	}
	
	public static InputStream getApplicationClassPathResourceAsStream(ServletContext context,String name){
		return context.getResourceAsStream("/WEB-INF/classes/"+name);
	}
	
	public static Map<String, Cookie> getCookieMap(HttpServletRequest request){
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

		// 加载COOKIES
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	public static String getRealRemoteAddr(HttpServletRequest request) {
		WKitRequestWrapper wkRequest;
		if(request instanceof WKitRequestWrapper){
			wkRequest = (WKitRequestWrapper)request;
		}else{
			wkRequest=new WKitRequestWrapper(request);
		}
		return wkRequest.getRealRemoteAddr();
	}
}
