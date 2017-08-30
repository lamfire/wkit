package com.lamfire.wkit;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.ErrorListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(ActionContext.class);
	private static final long serialVersionUID = 3268310591678711007L;
	public static final String USER_PRINCIPAL_IN_SESSION = "WKIT_SECURITY_CONTEXT";

	static final ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();

	protected static synchronized ActionContext createActionContext(ServletContext context, HttpServletRequest request, HttpServletResponse response,FilterConfig initConfig) {
		ActionContext ac = new ActionContext(context, request, response,initConfig);
		actionContext.set(ac);
		return ac;
	}
	
	public static ActionContext getInstance() {
		return (ActionContext) actionContext.get();
	}

	public static ActionContext getActionContext() {
		return (ActionContext) actionContext.get();
	}

	private static ApplicationContextMap contextMap;

	private RequestMap requestMap;
	private SessionMap sessionMap;
	private ParameterMap parameterMap;
	private Map<String, Cookie> cookieMap;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	private FilterConfig initConfig;

	public String getRemoteAddress() {
		return this.request.getRemoteAddr();
	}

	public HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	public HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	public ServletContext getServletContext() {
		return this.context;
	}

	private ActionContext(ServletContext context, HttpServletRequest request, HttpServletResponse response,FilterConfig initConfig) {
		this.request = request;
		this.response = response;
		this.context = context;
		this.initConfig = initConfig;
	}

	public OutputStream getOutputStream() throws IOException {
		return this.response.getOutputStream();
	}

	public void setResponseHeader(String key, String value) {
		this.response.addHeader(key, value);
	}

	public String getRequestHeader(String key) {
		return this.request.getHeader(key);
	}

	public void setCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		this.response.addCookie(cookie);
	}

	public void setCookie(Cookie cookie) {
		if (cookie != null) {
			this.response.addCookie(cookie);
		}
	}

	public Cookie getCookie(String key) {
		return getCookieMap().get(key);
	}

	public String getCookieValue(String key) {
		Cookie cookie = getCookieMap().get(key);
		if (cookie == null)
			return null;
		return cookie.getValue();
	}

	public Cookie[] getCookies() {
		return this.request.getCookies();
	}

	public Map<String, Cookie> getCookieMap() {
		if (cookieMap == null) {
			cookieMap = ServletUtils.getCookieMap(request);
		}
		return cookieMap;
	}

	public Map<String, Object> getSession() {
		if (this.sessionMap == null) {
			sessionMap = new SessionMap(this.request);
		}
		return this.sessionMap;
	}

	public Map<String, Object> getContext() {
		if (contextMap == null) {
			contextMap = new ApplicationContextMap(context);
		}
		return contextMap;
	}

	public Map<String, Object> getRequest() {
		if (requestMap == null) {
			requestMap = new RequestMap(this.request);
		}
		return this.requestMap;
	}

	public Map<String, Object> getParameters() {
		if (parameterMap == null) {
			parameterMap = new ParameterMap(this.request);
		}
		return this.parameterMap;
	}
	
	public static boolean isExternalUrl(String path){
		String url = path.toLowerCase();
		Pattern pattern = Pattern.compile("^(http://|https://){1}[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	public void sendRedirect(String path) {
		if (isExternalUrl(path)) {
			sendRedirectExternal(path);
			return;
		}

		try {
			this.response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	void handlePermissionDenied(Set<String> permissions){
		ErrorListener error = ActionRegistry.getInstance().getErrorListener();
		if(error == null){
			return;
		}
		error.onPermissionDenied(this,request,response,this.getUserPrincipal(),permissions);
	}

	void handleActionException(Exception e){
		ErrorListener error = ActionRegistry.getInstance().getErrorListener();
		if(error == null){
			return;
		}
		error.onActionException(this,request,response,e);
	}

	void handleNotAuthorized(){
		ErrorListener error = ActionRegistry.getInstance().getErrorListener();
		if(error == null){
			return;
		}
		error.onNotAuthorized(this,request,response);
	}

	public void forward(String path) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	private void sendRedirectExternal(String url) {
		try {
			this.response.sendRedirect(url);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}


	public String getApplicationUrl(){
		int port = request.getServerPort();
		if(port == 80){
			return String.format("%s://%s%s",request.getScheme(),request.getServerName(),request.getContextPath());
		}
		return String.format("%s://%s:%d%s",request.getScheme(),request.getServerName(),request.getServerPort(),request.getContextPath());
	}

	public String getApplicationPath() {
		String path = context.getRealPath("/");
		return path;
	}
	
	public String getApplicationPath(String res) {
		String path = context.getRealPath(res);
		return path;
	}

	public String getApplicationClassPath() {
		String path = context.getRealPath("/WEB-INF/classes/");
		return path;
	}

	public InputStream getApplicationClassPathResourceAsStream(String name) {
		return context.getResourceAsStream("/WEB-INF/classes/" + name);
	}

	public void login(String username, Collection<String> permissions){
		UserPrincipal principal = new UserPrincipal();
		principal.setName(username);
		principal.addPermissions(permissions);
		login(principal);
	}

	public void login(UserPrincipal principal){
		getSession().put(USER_PRINCIPAL_IN_SESSION,principal);
	}

	public void logout(){
		getSession().clear();
	}

	public UserPrincipal getUserPrincipal(){
		return (UserPrincipal)getSession().get(USER_PRINCIPAL_IN_SESSION);
	}

	public String getInitParameter(String name){
		return initConfig.getInitParameter(name);
	}

	public Enumeration getInitParameterNames(){
		return initConfig.getInitParameterNames();
	}
}
