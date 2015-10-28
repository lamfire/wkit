package com.lamfire.wkit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.Action;

final class Dispatcher {

	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class);

	private static ThreadLocal<Dispatcher> dispacherInstance = new ThreadLocal<Dispatcher>();
	private static ThreadLocal<ActionContext> actionContextInstance = new ThreadLocal<ActionContext>();
	private static InvocationVisitor visitor = new InvocationVisitor();

	private String defaultEncoding;
	private String multipartSaveDir;
	private long multipartLimitSize = 10000000;
	private String actionRoot;

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

	static ActionContext createActionContext(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		ActionContext ac = ActionContext.createActionContext(context, request, response);
		setActionContext(ac);
		return ac;
	}

	public void serviceAction(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws Exception{

		String servletPath = request.getServletPath();
		ActionContext ac = createActionContext(request, response, context);

		Action action = null;
		try {
			// init action
			ActionMapper mapper = ActionMapper.getMapper(actionRoot, servletPath);
			action  = mapper.newAction(ac.getParameters());
			action.init();
			
			// execute service
			action.accept(visitor);
		} catch (Exception e) {
			throw e;
		} finally {
			if (action != null) {
				action.destroy();
			}
		}

	}

	protected Action getAction(String servletPath) throws ClassNotFoundException{
		try {
			ActionMapper mapper = ActionMapper.getMapper(actionRoot, servletPath);
			return mapper.newAction();
		} catch (Exception e) {
			throw new ActionException("Action not found:" + servletPath);
		}
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

}