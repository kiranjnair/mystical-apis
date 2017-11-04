
package hack.interaction.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserInteraction {

	private String userid;
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
	@Override
	public String toString() {
		return "UserInteraction [userid=" + userid + ", interactionData=" + interactionData + "]";
	}
	


}
