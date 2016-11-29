package com.lamfire.wkit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
import com.lamfire.utils.*;

public class FilterDispatcher implements Filter {
	private static final Logger logger = Logger.getLogger(FilterDispatcher.class);
	private static final String INIT_PATAMETER_CHARSET = "charset";
	private static final String INIT_PATAMETER_DEBUG = "debug";
	private static final String INIT_PATAMETER_REQUEST_AUTHENTICATED = "request.authenticated";
	private static final String INIT_PATAMETER_EXCLUDE_SUFFIX = "exclude.suffixes";
	private static final String INIT_PATAMETER_EXCLUDE_PATHS = "exclude.paths";
	private static final String INIT_PATAMETER_AUTHORIZE_URL = "authorize.url";
	private static final String[] DEFAULT_EXCLUDE_SUFFIXES = {"css","js","jpg","png","gif","ico"};
	
	private static final Set<String> ExcludeSuffixes = new HashSet<String>();
	private static final Set<String> ExcludePaths = new HashSet<String>();

	private static String CHARSET = "utf-8";
	private FilterConfig filterConfig;
	
	private Dispatcher dispatcher;
	private boolean debug = false;
	private boolean requestAuthenticated = false;
	private String authorizeUrl = "/login";

	public void destroy() {
		dispatcher = null;
	}

	protected HttpServletRequest prepareDispatcherAndWrapRequest(HttpServletRequest request, HttpServletResponse response,ServletContext context) throws ServletException {
		Dispatcher du = Dispatcher.getInstance();

		if (du == null) {
			Dispatcher.setInstance(this.dispatcher);
		} else {
			this.dispatcher = du;
		}
	
		try {
			this.dispatcher.prepare(request, response);
			request = this.dispatcher.wrapRequest(request, context);
		} catch (IOException e) {
			String message = "Could not wrap servlet request with MultipartRequestWrapper!";
			logger.error(message, e);
			throw new ServletException(message, e);
		}

		return request;
	}

	/**
	 * 请求是否在白名单内
	 * @param servlet
	 * @return
	 */
	private boolean isExcludes(String servlet){
		//root
		if(StringUtils.isBlank(servlet)){
			return true;
		}

		//ExcludeSuffixes
		String suffix = FilenameUtils.getExtension(servlet);
		if(StringUtils.isNotBlank(suffix) && ExcludeSuffixes.contains(suffix)){
			return true;
		}

		//ExcludePaths
		if(ExcludePaths.contains(servlet)){
			return true;
		}

		//match path
		for(String path : ExcludePaths){
			if(servlet.startsWith(path)){
				return true;
			}
		}

		return false;
	}

	private boolean isJavaServerPageRequest(HttpServletRequest request) {
		String servlet = request.getServletPath();
		if (StringUtils.isEndWithIgnoreCase(servlet,".jsp") || StringUtils.contains(servlet,".jsp?")) {
			return true;
		}
		return false;
	}

	public void service(HttpServletRequest request, HttpServletResponse response, FilterChain chain,ActionContext actionContext) throws ActionException {
		String servletPath = request.getServletPath();
		//excludes
		if(isExcludes(servletPath)){
			doChain(request, response, chain);
			return;
		}



		//request authorized
		if(requestAuthenticated && !StringUtils.equals(authorizeUrl,servletPath)){
			String user = request.getRemoteUser();
			if(user == null){
				logger.error("Not Authorezed : " + servletPath);
				actionContext.handleNotAuthorized();
				return;
			}
		}

		//lookup mapper
		ActionMapper mapper = ActionRegistry.getInstance().lookup(servletPath);
		if(mapper == null){
			doChain(request, response, chain);
			return;
		}




		//mapping and authorized
		if(!mapper.isNonePermissionAuthorities()){
			String user = request.getRemoteUser();
			if(user == null){
				logger.error("Not Authorezed : " + servletPath);
				actionContext.handleNotAuthorized();
				return;
			}
		}



		//check permission
		if(!this.dispatcher.hasPermissions(actionContext,mapper)){
			logger.error("permission denied : " + servletPath);
			actionContext.handlePermissionDenied(mapper.getPermissions());
			return;
		}

		//invoke action

		this.dispatcher.serviceAction(actionContext,mapper);

	}

	/**
	 * 令命转发
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		ServletContext context = getServletContext();


		//wrap request
		try {
			request = prepareDispatcherAndWrapRequest(request, response,context);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		long startTime = System.currentTimeMillis();
		boolean success = true;

		//create action context
		ActionContext actionContext = dispatcher.createActionContext(request, response, getServletContext());

		try {
			service(request,response,chain,actionContext);
		}catch (ActionException e) {
			success =false;
			logger.error(e.getMessage(),e);
			actionContext.handleActionException(e);
		}

		if(this.debug){
    		String addr = ActionContext.getActionContext().getRemoteAddress();
            String dateTime = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd hh:mm:ss");
    		String debufMsg = String.format("[%s] %s %s %s %s",dateTime,addr,String.valueOf(success),String.valueOf(System.currentTimeMillis() - startTime),request.getServletPath());
    		logger.debug(debufMsg);
		}
	}
	

	
	protected void doChain(ServletRequest req, ServletResponse res, FilterChain chain){
		try {
			chain.doFilter(req, res);
		} catch (Exception e1) {
			logger.error(e1.getMessage(),e1);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		
		//parameter charset
		String charset = this.filterConfig.getInitParameter(INIT_PATAMETER_CHARSET);
		if(StringUtils.isNotBlank(charset)){
			CHARSET = charset;
			logger.info("change charset to '" + CHARSET +"'");
		}
		
		//parameter debug
		String debugVal = this.filterConfig.getInitParameter(INIT_PATAMETER_DEBUG);
		if(StringUtils.isNotBlank(debugVal)){
			this.debug = Boolean.valueOf(debugVal);
			logger.info("application run on debug molde :" + this.debug +"");
		}

		//request authenticated
		String authenticatedVal = this.filterConfig.getInitParameter(INIT_PATAMETER_REQUEST_AUTHENTICATED);
		if(StringUtils.isNotBlank(authenticatedVal)){
			this.requestAuthenticated = Boolean.valueOf(authenticatedVal);
			logger.info("http request authenticated :" + this.requestAuthenticated);
		}

		//authorizeUrl
		String authUrlVal = this.filterConfig.getInitParameter(INIT_PATAMETER_AUTHORIZE_URL);
		if(StringUtils.isNotBlank(authUrlVal)){
			this.authorizeUrl = authUrlVal;
			logger.info("authorize url :" + this.authorizeUrl);
		}
		
		//parameter exclude suffixes
		Sets.addAll(ExcludeSuffixes,DEFAULT_EXCLUDE_SUFFIXES);
		String suffix = this.filterConfig.getInitParameter(INIT_PATAMETER_EXCLUDE_SUFFIX);
		if(StringUtils.isNotBlank(suffix)){
			logger.info("exclude suffixes :" + suffix);
			String[] excludeSuffixes = StringUtils.split(suffix, ',');
			Sets.addAll(ExcludeSuffixes,excludeSuffixes);
		}
		
		//parameter exclude paths
		String paths = this.filterConfig.getInitParameter(INIT_PATAMETER_EXCLUDE_PATHS);
		if(StringUtils.isNotBlank(paths)){
			logger.info("exclude paths :" + paths);
			Sets.addAll(ExcludePaths,StringUtils.split(paths, ','));
		}

		//init dispatcher
		Config appConfig = ApplicationConfiguretion.getConfig(filterConfig);
		this.dispatcher = new Dispatcher();
		this.dispatcher.setMultipartLimitSize(appConfig.getMultipartLimit());
		this.dispatcher.setMultipartSaveDir(appConfig.getMultipartTempDir());
		this.dispatcher.setDefaultEncoding(CHARSET);
		Dispatcher.setInstance(this.dispatcher);
		
		//mapping package
        ActionRegistry.getInstance().registerAll(appConfig.getActionRoot());
	}

	public ServletContext getServletContext() {
		return this.filterConfig.getServletContext();
	}
	
}
