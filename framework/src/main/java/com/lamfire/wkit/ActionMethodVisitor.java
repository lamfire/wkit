package com.lamfire.wkit;

import javax.servlet.http.HttpServletRequest;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.action.ActionForward;
import java.lang.reflect.Method;

public final class ActionMethodVisitor {
	static final Logger LOGGER = Logger.getLogger(ActionMethodVisitor.class);

	public void visit(Action action, Method method, Object[] parameters) throws Exception {
		ActionForward result = (ActionForward)method.invoke(action,parameters);
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
}
