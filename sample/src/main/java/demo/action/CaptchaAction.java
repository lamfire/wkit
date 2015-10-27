package demo.action;

public class CaptchaAction extends com.lamfire.wkit.action.CaptchaAction{

	@Override
	public void init() {
		setBaseText("0123456789qwertyupasdfghjkmnbvcxz");
		this.setHeight(36);
		this.setWidth(80);
	}

	
}
