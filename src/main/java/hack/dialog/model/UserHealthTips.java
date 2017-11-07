package hack.dialog.model;

import java.util.Date;

public class UserHealthTips {
	String userid;
	private Date createdTime;
	String healthTips;

	public UserHealthTips() {
		// TODO Auto-generated constructor stub
	}

	public UserHealthTips(String userid, String healthTips,Date createdTime) {
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

	

	public String getHealthTips() {
		return healthTips;
	}

	public void setHealthTips(String healthTips) {
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
