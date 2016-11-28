package demo.action;

import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.UserPrincipal;
import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ActionSupport;
import com.lamfire.wkit.action.Captcha;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

import java.io.OutputStream;

/**
 * Created by lamfire on 16/5/18.
 */
@ACTION
public class MainAction extends ActionSupport {


    @MAPPING(path = "/")
    public ActionForward index() {
        return redirect("/login.jsp");
    }

    @MAPPING(path = "/cap")
    public void cap(OutputStream output){
        Captcha captcha = new Captcha();
        captcha.outputCaptcha(output);
    }

    @MAPPING(path = "/main.jsp",permissions = "ADMIN")
    public ActionForward main(OutputStream output){
       return forward("/main.jsp");
    }


    @MAPPING(path = "/login")
    public ActionForward login(@PARAM(value = "username") String username, @PARAM(value = "password")String password) {
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