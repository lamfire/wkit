package demo.action;

import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.UserPrincipal;
import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;

import java.util.ArrayList;
import java.util.List;

public class LoginAction extends ServletAction{

	private String username;
	private String password;
	private String captcha;

	@Override
	public ActionForward execute() {
		if(StringUtils.equals(username,password)){
			UserPrincipal user = new UserPrincipal();
			user.setName(username);
			user.setPassword(password);
			List<String> permissions = new ArrayList<String>();
			permissions.add("READ");
			permissions.add("WRITE");
			user.addPermissions(permissions);
			getActionContext().setUserPrincipal(user);

			return redirect("/main.jsp");
		}

		return forward("/login.jsp");
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
