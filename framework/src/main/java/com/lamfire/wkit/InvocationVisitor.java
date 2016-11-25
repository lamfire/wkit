package com.lamfire.wkit;

import javax.servlet.http.HttpServletRequest;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.action.ActionResult;
import com.lamfire.wkit.action.ServletAction;
import com.lamfire.wkit.action.StreamAction;
import com.lamfire.wkit.action.TextAction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class InvocationVisitor {
	static final Logger LOGGER = Logger.getLogger(InvocationVisitor.class);

	public void visitServletAction(ServletAction action, Method method, Object[] parameters) throws InvocationTargetException, IllegalAccessException {
		ActionResult result = (ActionResult)method.invoke(action,parameters);
		if (result == null) {
			return;
		}

		ActionContext context = ActionContext.getActionContext();

		// redirect
		if (result.isRedirect()) {
			context.sendRedirect(result.getUrlPath());
			return;
		}

		// forward
		HttpServletRequest request = context.getHttpServletRequest();
		if (request instanceof WKitRequestWrapper) {
			((WKitRequestWrapper) request).addAttributes(result.getAttrs());
		}
		context.forward(result.getUrlPath());

	}

	public void visitStreamAction(StreamAction action, Method method, Object[] parameters) throws Exception{
		method.invoke(action,parameters);

	}

	public void visitTextAction(TextAction action, Method method, Object[] parameters) throws Exception {
		method.invoke(action,parameters);
	}

	public void visit(Action action, Method method, Object[] parameters) throws Exception {
		if (action instanceof ServletAction) {
			visitServletAction((ServletAction) action,method,parameters);
			return;
		}

		if (action instanceof StreamAction) {
			visitStreamAction((StreamAction) action,method,parameters);
			return;
		}

		if (action instanceof TextAction) {
			visitTextAction((TextAction) action,method,parameters);
			return;
		}
	}
}
