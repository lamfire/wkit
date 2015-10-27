package demo.action;

import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;

public class CaptchaCheckAction extends ServletAction{

	private String input;
	private String captcha;
	
	@Override
	public ActionForward execute() {
		captcha = (String)getSession().get(CaptchaAction.SESSION_KEY);
		return forward("/actions/captcha_result.jsp");
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
