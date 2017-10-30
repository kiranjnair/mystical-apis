package hack.dialog.model;

import hack.model.converse.Result;
import hack.model.converse.Status;

public class ResponseModel {
	public String id;
	public String timestamp;
	public String lang;
	public Result result;
	public Status status;
	public String sessionId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "ResponseModel [id=" + id + ", timestamp=" + timestamp + ", lang=" + lang + ", result=" + result
				+ ", status=" + status + ", sessionId=" + sessionId + "]";
	}
	
}
