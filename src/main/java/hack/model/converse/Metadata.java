package hack.model.converse;

public class Metadata {
	
	public String intentId;
	public String intentName;
	public String webhookUsed;
	public String webhookForSlotFillingUsed;
	//public String[] inputContexts;
	//public String[] outputContexts;	
	//public String[] contexts;
	
	public String getIntentId() {
		return intentId;
	}
	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getWebhookUsed() {
		return webhookUsed;
	}
	public void setWebhookUsed(String webhookUsed) {
		this.webhookUsed = webhookUsed;
	}
	public String getWebhookForSlotFillingUsed() {
		return webhookForSlotFillingUsed;
	}
	public void setWebhookForSlotFillingUsed(String webhookForSlotFillingUsed) {
		this.webhookForSlotFillingUsed = webhookForSlotFillingUsed;
	}
	@Override
	public String toString() {
		return "Metadata [intentId=" + intentId + ", intentName=" + intentName + ", webhookUsed=" + webhookUsed
				+ ", webhookForSlotFillingUsed=" + webhookForSlotFillingUsed + "]";
	}

}
