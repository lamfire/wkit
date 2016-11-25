package demo.action;

import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.UserPrincipal;
import com.lamfire.wkit.action.ActionResult;
import com.lamfire.wkit.action.ServletAction;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

/**
 * Created by lamfire on 16/5/18.
 */
@ACTION
public class MainAction extends ServletAction {


    @MAPPING(path = "/login")
    public ActionResult login(@PARAM(value = "username") String username, @PARAM(value = "password")String password) {
        UserPrincipal user = new UserPrincipal();
        user.setName(username);

        if(StringUtils.equals("admin",username)) {
            user.addPermissions("ADMIN");
        }

        if(StringUtils.equals(username,password)){
            getActionContext().login(user);
            return redirect("/main.jsp");
        }

        this.setErrorMessage("username or password failed.");
        return forward("/login.jsp");
    }



}
