package hack.model.converse;

import java.util.List;

public class Fullfillment {
	private String speech;
	private List<Message> messages;
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	@Override
	public String toString() {
		return "Fullfillment [speech=" + speech + ", messages=" + messages + "]";
	}

	

}
