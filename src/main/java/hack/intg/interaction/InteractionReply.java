package hack.intg.interaction;

import hack.interaction.model.InteractionData;
import hack.intg.IntgReply;

public class InteractionReply implements IntgReply {
	private String 	rawJson;
	private InteractionData interactionData;
	public String getRawJson() {
		return rawJson;
	}
	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}
	public InteractionData getInteractionData() {
		return interactionData;
	}
	public void setInteractionData(InteractionData interactionData) {
		this.interactionData = interactionData;
	}

	
	

}
