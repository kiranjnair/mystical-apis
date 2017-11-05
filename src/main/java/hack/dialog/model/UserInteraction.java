
package hack.dialog.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import hack.interaction.model.InteractionData;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserInteraction {

	private String userid;
	private Date createdTime;
	private InteractionData interactionData;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public InteractionData getInteractionData() {
		return interactionData;
	}
	public void setInteractionData(InteractionData interactionData) {
		this.interactionData = interactionData;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@Override
	public String toString() {
		return "UserInteraction [userid=" + userid + ", createdTime=" + createdTime + ", interactionData="
				+ interactionData + "]";
	}



}
