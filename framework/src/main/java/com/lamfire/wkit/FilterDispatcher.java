package com.lamfire.wkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
	private static final String INIT_PATAMETER_EXCLUDE_SUFFIX = "exclude.suffixes";
	private static final String INIT_PATAMETER_EXCLUDE_PATHS = "exclude.paths";
	private static final String INIT_PATAMETER_PERMISSION_DENIED_PAGE = "permission.denied.page";
	
	private static final String[] DEFAULT_EXCLUDE_SUFFIXES = {"css","js","jpg","png","gif","ico"};
	
	private static final Set<String> ExcludeSuffixes = new HashSet<String>();
	private static String[] excludePaths;

	private static String CHARSET = "utf-8";
	private FilterConfig filterConfig;
	
	private Dispatcher dispatcher;
	private boolean debug = false;
	private String exceptionTemplate;

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
		if(StringUtils.isBlank(suffix)){
			return false;
		}
		if(ExcludeSuffixes.contains(suffix)){
			return true;
		}
		
		//ExcludePaths
		if(excludePaths != null){
			for(String path : excludePaths){
				if(servlet.startsWith(path)){
					return true;
				}
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

		String servletPath = request.getServletPath();

		//resource
		if(isExcludes(servletPath)){
			doChain(request, res,chain);
			return;
		}

		ActionContext actionContext = dispatcher.createActionContext(request, response, context);

		//is jsp request
		if(isJavaServerPageRequest(request)){
			ActionMapper mapper = ActionRegistry.getInstance().lookup(servletPath);
			if(mapper == null){
				doChain(request, res, chain);
				return;
			}

			if(dispatcher.hasPermissions(actionContext,mapper)) {
				doChain(request, res, chain);
				return;
			}
			dispatcher.forwardPermissionDenied(actionContext,mapper);
			return;
		}
		
		long startTime = System.currentTimeMillis();

        boolean success = true;
		try {
			this.dispatcher.serviceAction(actionContext);
		}catch (Exception e) {
            success = false;
			logger.error(e.getMessage(),e);
			if(this.debug){
				writeDebugExceptionInfo(request,response,e);
			}
		}
		if(this.debug){
    		String addr = ActionContext.getActionContext().getRemoteAddress();
            String dateTime = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd hh:mm:ss");
    		String debufMsg = String.format("[%s] %s %s %s %s",dateTime,addr,String.valueOf(success),String.valueOf(System.currentTimeMillis() - startTime),servletPath);
    		logger.debug(debufMsg);
		}
	}
	
	/**
	 * 回写调试异常到页面
	 * @param request
	 * @param response
	 * @param e
	 */
	private void writeDebugExceptionInfo(HttpServletRequest request,HttpServletResponse response,Exception e){
		String servlet = request.getServletPath();
		StringWriter writer = new StringWriter();  
		PrintWriter print  = new PrintWriter(writer, true);
		try{
			e.printStackTrace(print);  
	        String str = writer.toString();  
			String info = String.format(exceptionTemplate, servlet,str,Version.VERSION);
			response.getOutputStream().print(info);
		}catch(Exception ex){
			
		}finally{
			IOUtils.closeQuietly(print);
			IOUtils.closeQuietly(writer);
		}
	}
	
	protected void doChain(ServletRequest req, ServletResponse res, FilterChain chain){
		try {
			chain.doFilter(req, res);
		} catch (Exception e1) {
			logger.error(e1.getMessage(),e1);
		}
	}
	
	private void loadExceptionTemplate(){
	    this.exceptionTemplate = ExceptionPageTemplate.TEMPLATE;
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
			if(this.debug){
				loadExceptionTemplate();
			}
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
			excludePaths = StringUtils.split(paths, ',');
		}

		String permissionDeniedPage = this.filterConfig.getInitParameter(INIT_PATAMETER_PERMISSION_DENIED_PAGE);
		
		//init dispatcher
		Config appConfig = ApplicationConfiguretion.getConfig(filterConfig);
		this.dispatcher = new Dispatcher();
		this.dispatcher.setMultipartLimitSize(appConfig.getMultipartLimit());
		this.dispatcher.setMultipartSaveDir(appConfig.getMultipartTempDir());
		this.dispatcher.setDefaultEncoding(CHARSET);
		this.dispatcher.setActionRoot(appConfig.getActionRoot());

		if(StringUtils.isNotBlank(permissionDeniedPage)) {
			this.dispatcher.setPermissionDeniedPage(permissionDeniedPage);
		}

		Dispatcher.setInstance(this.dispatcher);
		
		//mapping package
        ActionRegistry.getInstance().registerAll(appConfig.getActionRoot());
	}

	public ServletContext getServletContext() {
		return this.filterConfig.getServletContext();
	}
	
}
