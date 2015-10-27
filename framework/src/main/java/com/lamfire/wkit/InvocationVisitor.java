package com.lamfire.wkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lamfire.logger.Logger;
import com.lamfire.wkit.action.Action;
import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;
import com.lamfire.wkit.action.StreamAction;
import com.lamfire.wkit.action.TextAction;

public final class InvocationVisitor {
	static final Logger LOGGER = Logger.getLogger(InvocationVisitor.class);

	public void visitServletAction(ServletAction action) {
		ActionForward forward = action.execute();
		if (forward == null)
			return;

		ActionContext context = ActionContext.getActionContext();

		// redirect
		if (forward.isRedirect()) {
			context.sendRedirect(forward.getUrlPath());
			return;
		}

		// forward
		HttpServletRequest request = context.getHttpServletRequest();
		if (request instanceof WKitRequestWrapper) {
			((WKitRequestWrapper) request).setAction(action);
		}
		context.forward(forward.getUrlPath());

	}

	public void visitStreamAction(StreamAction action) throws Exception{
		HttpServletResponse response = ActionContext.getActionContext().getHttpServletResponse();
		StreamAction sa = (StreamAction) action;
		sa.execute(response.getOutputStream());

	}

	public void visitTextAction(TextAction action) throws Exception {
		HttpServletResponse response = ActionContext.getActionContext().getHttpServletResponse();
		action.execute(response.getWriter());
	}

	public void visit(Action action) throws Exception {
		if (action instanceof ServletAction) {
			visitServletAction((ServletAction) action);
			return;
		}

		if (action instanceof StreamAction) {
			visitStreamAction((StreamAction) action);
			return;
		}

		if (action instanceof TextAction) {
			visitTextAction((TextAction) action);
			return;
		}
	}
}
