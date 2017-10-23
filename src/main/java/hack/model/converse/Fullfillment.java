package hack.model.converse;

import java.util.Arrays;

public class Fullfillment {
	private String speech;
	private Message[] messages;
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public Message[] getMessages() {
		return messages;
	}
	public void setMessages(Message[] messages) {
		this.messages = messages;
	}
	@Override
	public String toString() {
		return "Fullfillment [speech=" + speech + ", messages=" + Arrays.toString(messages) + ", getSpeech()="
				+ getSpeech() + ", getMessages()=" + Arrays.toString(getMessages()) + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
