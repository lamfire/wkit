package demo.action;

import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.UserPrincipal;
import com.lamfire.wkit.action.ErrorActionSupport;
import com.lamfire.wkit.anno.ACTION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@ACTION
public class AppErrorAction extends ErrorActionSupport {

    @Override
    public void onPermissionDenied(ActionContext context, HttpServletRequest request, HttpServletResponse response, UserPrincipal user, Set<String> permissions) {
        super.onPermissionDenied(context,request,response,user,permissions);
    }

    @Override
    public void onActionException(ActionContext context, HttpServletRequest request, HttpServletResponse response, Exception e) {
        super.onActionException(context,request,response,e);
    }

    @Override
    public void onNotAuthorized(ActionContext context, HttpServletRequest request, HttpServletResponse response) {
        //super.onNotAuthorized(context, request, response);
        context.sendRedirect("/login.jsp");
    }
}
