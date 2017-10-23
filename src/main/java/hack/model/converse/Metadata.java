package hack.model.converse;

import java.util.Arrays;

public class Metadata {

	public String[] inputContexts;
	public String[] outputContexts;
	public String intentName;
	public String intentId;
	public String webhookUsed;
	public String webhookForSlotFillingUsed;
	public String[] contexts;
	public String[] getInputContexts() {
		return inputContexts;
	}
	public void setInputContexts(String[] inputContexts) {
		this.inputContexts = inputContexts;
	}
	public String[] getOutputContexts() {
		return outputContexts;
	}
	public void setOutputContexts(String[] outputContexts) {
		this.outputContexts = outputContexts;
	}
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getIntentId() {
		return intentId;
	}
	public void setIntentId(String intentId) {
		this.intentId = intentId;
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
	public String[] getContexts() {
		return contexts;
	}
	public void setContexts(String[] contexts) {
		this.contexts = contexts;
	}
	@Override
	public String toString() {
		return "Metadata [inputContexts=" + Arrays.toString(inputContexts) + ", outputContexts="
				+ Arrays.toString(outputContexts) + ", intentName=" + intentName + ", intentId=" + intentId
				+ ", webhookUsed=" + webhookUsed + ", webhookForSlotFillingUsed=" + webhookForSlotFillingUsed
				+ ", contexts=" + Arrays.toString(contexts) + "]";
	}
	

}
