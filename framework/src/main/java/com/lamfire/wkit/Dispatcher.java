package com.lamfire.wkit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.Action;
import org.apache.commons.lang.StringUtils;

final class Dispatcher {

	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class);

	private static ThreadLocal<Dispatcher> dispacherInstance = new ThreadLocal<Dispatcher>();
	private static ThreadLocal<ActionContext> actionContextInstance = new ThreadLocal<ActionContext>();
	private static final ActionMethodVisitor visitor = new ActionMethodVisitor();

	private String defaultEncoding;
	private String multipartSaveDir;
	private long multipartLimitSize = 10000000;
	private String actionRoot;
	private String permissionDeniedPage = "/permission_denied.jsp";

	public static Dispatcher getInstance() {
		return (Dispatcher) dispacherInstance.get();
	}

	static void setInstance(Dispatcher instance) {
		dispacherInstance.set(instance);
	}

	static void setActionContext(ActionContext context) {
		actionContextInstance.set(context);
	}

	public static ActionContext getActionContext() {
		return actionContextInstance.get();
	}

    static synchronized ActionContext createActionContext(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		ActionContext ac = ActionContext.createActionContext(context, request, response);
		setActionContext(ac);
		return ac;
	}

	void forwardPermissionDenied(ActionContext context,ActionMapper mapper){
		HttpServletRequest request = context.getHttpServletRequest();
		request.setAttribute("url",request.getServletPath());
		request.setAttribute("permissions",StringUtils.join(mapper.getPermissions(),","));
		context.forward(permissionDeniedPage);
	}

	boolean hasPermissions(ActionContext context,ActionMapper mapper){
		//permission
		if(!mapper.isNonePermissionAuthorities()){
			UserPrincipal u = context.getUserPrincipal();
			LOGGER.debug("[UserPrincipal] : " + u);
			if(u == null || ! u.hashPermissions(mapper.getPermissions())){
				return false;
			}
		}

		return true;
	}

	boolean hasPermissions(ActionContext context,String servletPath){
		ActionMapper mapper = ActionRegistry.getInstance().lookup(servletPath);
		if(mapper == null){
			return true;
		}
		return hasPermissions(context,mapper);
	}

	public void serviceAction(ActionContext context) throws Exception{

		String servletPath = context.getHttpServletRequest().getServletPath();

		Action action = null;
		try {
			// init action
			ActionMapper mapper = getActionMapper(servletPath);
            if(mapper == null){
                throw new ActionException("Not found action : " + servletPath);
            }

			//permission
			if(!hasPermissions(context,mapper)){
				//permission denied
				forwardPermissionDenied(context,mapper);
				return;
			}

			action  = mapper.newAction();
			Method method = mapper.getActionMethod();
			Object[] params = mapper.resolveMethodArguments(context,context.getParameters());
			
			// execute service
			visitor.visit(action,method,params);
		} catch (Exception e) {
			throw e;
		} finally {
			if (action != null) {
				action.destroy();
			}
		}

	}

    private ActionMapper  getActionMapper(String servletPath){
        ActionRegistry registry = ActionRegistry.getInstance();
        ActionMapper mapper = registry.lookup(servletPath);
        return mapper;
    }

	private String getMultipartSaveDir(ServletContext servletContext) {
		String saveDir = this.multipartSaveDir.trim();

		if (saveDir == null && "".equals(saveDir)) {
			File tempdir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			LOGGER.info("Unable to find 'request.multipart.tempdir' property setting. Defaulting to javax.servlet.context.tempdir");

			if (tempdir != null) {
				saveDir = tempdir.toString();
				this.multipartSaveDir = saveDir;
			}

			return this.multipartSaveDir;
		}

		File multipartSaveDir = new File(saveDir);
		if ((!multipartSaveDir.exists()) && (!multipartSaveDir.mkdirs())) {
			String logMessage = "Could not find create multipart save directory '" + saveDir + "'.";
			LOGGER.warn(logMessage);
		}

        if(LOGGER.isDebugEnabled()){
		    LOGGER.debug("saveDir=" + saveDir);
        }

		return saveDir;
	}

	public void prepare(HttpServletRequest request, HttpServletResponse response) {
		String encoding = "utf-8";
		if (this.defaultEncoding != null) {
			encoding = this.defaultEncoding;
		}
		try {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn(e.getMessage());
		}
	}

	public HttpServletRequest wrapRequest(HttpServletRequest request, ServletContext servletContext) throws IOException {
		if ((request instanceof WKitRequestWrapper)) {
			return request;
		}

		String content_type = request.getContentType();
		if ((content_type != null) && (content_type.indexOf("multipart/form-data") != -1)) {
			MultiPartRequestImpl multi = new MultiPartRequestImpl();
			multi.setMaxSize(this.multipartLimitSize);
			request = new MultiPartRequestWrapper(multi, request, getMultipartSaveDir(servletContext));
		}

		request = new WKitRequestWrapper(request);

		return request;
	}

	public void setMultipartLimitSize(long limit) {
		this.multipartLimitSize = limit;
	}

	public void setMultipartSaveDir(String dir) {
		this.multipartSaveDir = dir;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public void setActionRoot(String actionRoot) {
		this.actionRoot = actionRoot;
	}

	public void setPermissionDeniedPage(String permissionDeniedPage) {
		this.permissionDeniedPage = permissionDeniedPage;
	}
}
