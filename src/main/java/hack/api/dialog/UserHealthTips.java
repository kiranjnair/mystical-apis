package hack.api.dialog;

import java.util.List;

public class UserHealthTips {
	String userid;
	List<String> healthTips;

	public UserHealthTips() {
		// TODO Auto-generated constructor stub
	}

	public UserHealthTips(String userid, List<String> healthTips) {
		this.userid=userid;
		this.healthTips=healthTips;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<String> getHealthTips() {
		return healthTips;
	}

	public void setHealthTips(List<String> healthTips) {
		this.healthTips = healthTips;
	}

	@Override
	public String toString() {
		return "UserHealthTips [userid=" + userid + ", healthTips=" + healthTips + "]";
	}
	

}
