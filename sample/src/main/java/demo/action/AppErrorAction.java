package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.Parameters;
import com.lamfire.wkit.UserPrincipal;
import com.lamfire.wkit.action.ActionSupport;
import com.lamfire.wkit.action.ErrorAction;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Set;

@ACTION
public class AppErrorAction extends ErrorAction {

    @Override
    public void onPermissionDenied(ActionContext context, HttpServletRequest request, HttpServletResponse response, UserPrincipal user, Set<String> permissions) {
        super.onPermissionDenied(context,request,response,user,permissions);
    }

    @Override
    public void onActionException(ActionContext context, HttpServletRequest request, HttpServletResponse response, Exception e) {
        super.onActionException(context,request,response,e);
    }
}
