package hack.dialog.model;

import java.util.Date;
import java.util.List;

public class UserHealthTips {
	String userid;
	private Date createdTime;
	List<String> healthTips;

	public UserHealthTips() {
		// TODO Auto-generated constructor stub
	}

	public UserHealthTips(String userid, List<String> healthTips,Date createdTime) {
		this.userid=userid;
		this.healthTips=healthTips;
		this.createdTime = createdTime;
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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "UserHealthTips [userid=" + userid + ", createdTime=" + createdTime + ", healthTips=" + healthTips + "]";
	}


}
