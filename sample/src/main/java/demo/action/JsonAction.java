package demo.action;

import java.io.OutputStream;

import com.lamfire.json.JSON;
import com.lamfire.wkit.action.StreamAction;

public class JsonAction extends StreamAction{

	private String username;
	private String type;
	private String province,city,area;
	private String password;
	private int status;
	private String remark;
	

	@Override
	public void execute(OutputStream output) {
		JSON js = new JSON();
		js.put("username", username);
		js.put("type", type);
		js.put("province", province);
		js.put("city", city);
		js.put("area", area);
		js.put("password", password);
		js.put("status", status);
		js.put("remark", remark);
		
		this.write(output, js.toString());
	}
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	

}
