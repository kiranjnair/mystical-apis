package hack.model.converse;

public class Message {
	private String type;
	private String id;
	private String speech;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	@Override
	public String toString() {
		return "Message [type=" + type + ", id=" + id + ", speech=" + speech + "]";
	}
	

}
