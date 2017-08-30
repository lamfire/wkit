package com.lamfire.wkit.action;

import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public interface ErrorListener extends Action {

    void onPermissionDenied(ActionContext context, HttpServletRequest request , HttpServletResponse response, UserPrincipal user,Set<String> permissions);


    void onActionException(ActionContext context, HttpServletRequest request , HttpServletResponse response,Exception e);


    void onNotAuthorized(ActionContext context, HttpServletRequest request , HttpServletResponse response);

}
